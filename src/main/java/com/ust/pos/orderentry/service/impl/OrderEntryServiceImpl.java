package com.ust.pos.orderentry.service.impl;

import com.ust.pos.orderentry.service.OrderEntryService;
import com.ust.pos.order.service.OrderService;
import com.ust.pos.dto.OrderEntryDto;
import com.ust.pos.model.OrderEntry;
import com.ust.pos.model.OrderEntryRepository;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
public class OrderEntryServiceImpl implements OrderEntryService {

    @Autowired
    private OrderEntryRepository orderEntryRepository;

    @Autowired
    private OrderService orderService;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<OrderEntryDto> findAll(Pageable pageable) {
        Page<OrderEntry> page = orderEntryRepository.findAll(pageable);
        Type listType = new TypeToken<List<OrderEntryDto>>() {
        }.getType();
        return modelMapper.map(page.getContent(), listType);
    }

    @Override
    public List<OrderEntryDto> findByOrderId(String orderId) {
        List<OrderEntry> entries = orderEntryRepository.findByOrderId(orderId);
        Type listType = new TypeToken<List<OrderEntryDto>>() {
        }.getType();
        return modelMapper.map(entries, listType);
    }

    @Override
    public OrderEntryDto findByIdentifier(String identifier) {
        OrderEntry entry = orderEntryRepository.findByIdentifier(identifier);
        if (entry == null) {
            OrderEntryDto dto = new OrderEntryDto();
            dto.setSuccess(false);
            dto.setMessage("Order entry not found");
            return dto;
        }
        return modelMapper.map(entry, OrderEntryDto.class);
    }

    @Override
    public OrderEntryDto save(OrderEntryDto orderEntryDto) {
        OrderEntry entry = null;
        boolean isNew = true;

        if (orderEntryDto.getIdentifier() != null) {
            entry = orderEntryRepository.findByIdentifier(orderEntryDto.getIdentifier());
            isNew = entry == null;
        }
        if (entry == null) {
            entry = new OrderEntry();
            entry.setIdentifier(UUID.randomUUID().toString());
        }

        entry.setOrderId(orderEntryDto.getOrderId());
        entry.setProductId(orderEntryDto.getProductId());
        entry.setProductName(orderEntryDto.getProductName());
        entry.setQuantity(orderEntryDto.getQuantity());
        entry.setMrp(orderEntryDto.getMrp());
        entry.setSellingPrice(orderEntryDto.getSellingPrice());

        BigDecimal qty = entry.getQuantity() != null ? entry.getQuantity() : BigDecimal.ZERO;
        BigDecimal mrp = entry.getMrp() != null ? entry.getMrp() : BigDecimal.ZERO;
        BigDecimal sellingPrice = entry.getSellingPrice() != null ? entry.getSellingPrice() : BigDecimal.ZERO;

        BigDecimal originalPrice = mrp.multiply(qty);
        BigDecimal totalPrice = sellingPrice.multiply(qty);
        BigDecimal discount = originalPrice.subtract(totalPrice);

        entry.setOriginalPrice(originalPrice);
        entry.setTotalPrice(totalPrice);
        entry.setDiscount(discount);

        OrderEntry saved = orderEntryRepository.save(entry);
        orderService.recalculate(saved.getOrderId());

        OrderEntryDto dto = modelMapper.map(saved, OrderEntryDto.class);
        dto.setSuccess(true);
        dto.setMessage(isNew ? "Order entry created successfully" : "Order entry updated successfully");
        return dto;
    }

    /**
     * Bulk-copies entries (e.g. from a Cart during checkout) where pricing has
     * already been computed by the caller. Persists everything in one batch and
     * deliberately skips per-entry order recalculation — the caller is expected
     * to set the order's totals directly afterward (see OrderService#applyTotals).
     */
    @Override
    public List<OrderEntryDto> saveAll(List<OrderEntryDto> orderEntryDtos) {
        List<OrderEntry> entries = orderEntryDtos.stream().map(orderEntryDto -> {
            OrderEntry entry = new OrderEntry();
            entry.setIdentifier(UUID.randomUUID().toString());
            entry.setOrderId(orderEntryDto.getOrderId());
            entry.setProductId(orderEntryDto.getProductId());
            entry.setProductName(orderEntryDto.getProductName());
            entry.setQuantity(orderEntryDto.getQuantity());
            entry.setMrp(orderEntryDto.getMrp());
            entry.setSellingPrice(orderEntryDto.getSellingPrice());
            entry.setOriginalPrice(orderEntryDto.getOriginalPrice());
            entry.setTotalPrice(orderEntryDto.getTotalPrice());
            entry.setDiscount(orderEntryDto.getDiscount());
            return entry;
        }).collect(Collectors.toList());

        List<OrderEntry> saved = orderEntryRepository.saveAll(entries);

        Type listType = new TypeToken<List<OrderEntryDto>>() {
        }.getType();
        return modelMapper.map(saved, listType);
    }

    @Override
    public void delete(String identifier) {
        OrderEntry entry = orderEntryRepository.findByIdentifier(identifier);
        if (entry == null) {
            return;
        }
        String orderId = entry.getOrderId();
        orderEntryRepository.delete(entry);
        orderService.recalculate(orderId);
    }
}