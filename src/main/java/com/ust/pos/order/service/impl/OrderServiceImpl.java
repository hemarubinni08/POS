package com.ust.pos.order.service.impl;

import com.ust.pos.order.service.OrderService;
import com.ust.pos.model.Order;
import com.ust.pos.dto.OrderDto;
import com.ust.pos.model.OrderEntry;
import com.ust.pos.model.OrderEntryRepository;
import com.ust.pos.model.OrderRepository;
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

@Service
@Transactional
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderEntryRepository orderEntryRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<OrderDto> findAll(Pageable pageable) {
        Page<Order> page = orderRepository.findAll(pageable);
        Type listType = new TypeToken<List<OrderDto>>() {
        }.getType();
        return modelMapper.map(page.getContent(), listType);
    }

    @Override
    public OrderDto findByIdentifier(String identifier) {
        Order order = orderRepository.findByIdentifier(identifier);
        if (order == null) {
            OrderDto dto = new OrderDto();
            dto.setSuccess(false);
            dto.setMessage("Order not found");
            return dto;
        }
        return modelMapper.map(order, OrderDto.class);
    }

    @Override
    public OrderDto save(String identifier) {
        Order existing = orderRepository.findByIdentifier(identifier);
        if (existing != null) {
            OrderDto dto = modelMapper.map(existing, OrderDto.class);
            dto.setSuccess(true);
            dto.setMessage("Order already exists");
            return dto;
        }
        Order order = new Order();
        order.setIdentifier(identifier);
        order.setTotalPrice(BigDecimal.ZERO);
        order.setOriginalPrice(BigDecimal.ZERO);
        order.setDiscount(BigDecimal.ZERO);
        orderRepository.save(order);
        OrderDto dto = modelMapper.map(order, OrderDto.class);
        dto.setSuccess(true);
        dto.setMessage("Order created successfully");
        return dto;
    }

    @Override
    public void delete(String identifier) {
        orderEntryRepository.deleteByOrderId(identifier);
        orderRepository.deleteByIdentifier(identifier);
    }

    @Override
    public OrderDto recalculate(String identifier) {
        List<OrderEntry> entries = orderEntryRepository.findByOrderId(identifier);
        BigDecimal total = BigDecimal.ZERO;
        BigDecimal original = BigDecimal.ZERO;
        BigDecimal discount = BigDecimal.ZERO;
        for (OrderEntry e : entries) {
            total = total.add(e.getTotalPrice() != null ? e.getTotalPrice() : BigDecimal.ZERO);
            original = original.add(e.getOriginalPrice() != null ? e.getOriginalPrice() : BigDecimal.ZERO);
            discount = discount.add(e.getDiscount() != null ? e.getDiscount() : BigDecimal.ZERO);
        }
        Order order = orderRepository.findByIdentifier(identifier);
        if (order == null) {
            OrderDto dto = new OrderDto();
            dto.setSuccess(false);
            dto.setMessage("Order not found");
            return dto;
        }
        order.setTotalPrice(total);
        order.setOriginalPrice(original);
        order.setDiscount(discount);
        Order saved = orderRepository.save(order);
        return modelMapper.map(saved, OrderDto.class);
    }

    /**
     * Applies totals that are already known (e.g. copied straight from a Cart
     * during checkout) without re-deriving them by summing entries. Use
     * recalculate() instead when entries are added/edited/removed individually
     * after the order already exists.
     */
    @Override
    public OrderDto applyTotals(String identifier, BigDecimal originalPrice, BigDecimal totalPrice, BigDecimal discount) {
        Order order = orderRepository.findByIdentifier(identifier);
        if (order == null) {
            OrderDto dto = new OrderDto();
            dto.setSuccess(false);
            dto.setMessage("Order not found");
            return dto;
        }
        order.setOriginalPrice(originalPrice);
        order.setTotalPrice(totalPrice);
        order.setDiscount(discount);
        Order saved = orderRepository.save(order);
        OrderDto dto = modelMapper.map(saved, OrderDto.class);
        dto.setSuccess(true);
        dto.setMessage("Order totals updated successfully");
        return dto;
    }
}