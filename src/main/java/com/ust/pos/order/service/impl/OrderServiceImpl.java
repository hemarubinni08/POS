package com.ust.pos.order.service.impl;

import com.ust.pos.dto.OrderDto;
import com.ust.pos.dto.OrderEntryDto;
import com.ust.pos.dto.WsDto;
import com.ust.pos.modell.*;
import com.ust.pos.order.service.OrderService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderEntryRepository orderEntryRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartEntryRepository cartEntryRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    @Transactional
    public OrderDto checkout(OrderDto orderDto) {
        // 1. Validation: Ensure the frontend actually sent product line items
        if (orderDto.getEntryList() == null || orderDto.getEntryList().isEmpty()) {
            orderDto.setSuccess(false);
            orderDto.setMessage("Cannot process checkout: Transaction item list is empty");
            return orderDto;
        }

        // 2. Generate and assign top-level Order database properties directly from the payload
        String orderIdentifier = "ORD-" + System.currentTimeMillis();
        Order order = new Order();
        order.setIdentifier(orderIdentifier);
        order.setCustomerIdentifier(orderDto.getCustomerIdentifier());
        order.setOriginalPrice(orderDto.getOriginalPrice());
        order.setDiscount(orderDto.getDiscount());
        order.setTotalPrice(orderDto.getTotalPrice());
        order.setPaymentMethod(orderDto.getPaymentMethod());

        // 3. Handle Tender Math
        if ("CASH".equalsIgnoreCase(orderDto.getPaymentMethod())) {
            order.setReceivedAmount(orderDto.getReceivedAmount());
            BigDecimal changeAmount = orderDto.getReceivedAmount().subtract(orderDto.getTotalPrice());
            order.setChangeAmount(changeAmount.compareTo(BigDecimal.ZERO) > 0 ? changeAmount : BigDecimal.ZERO);
        } else {
            order.setReceivedAmount(orderDto.getTotalPrice());
            order.setChangeAmount(BigDecimal.ZERO);
        }

        // Save the order master record
        orderRepository.save(order);

        // 4. Loop through the entries sent directly in the request payload and persist them
        for (OrderEntryDto entryDto : orderDto.getEntryList()) {
            OrderEntry orderEntry = new OrderEntry();
            orderEntry.setIdentifier(orderIdentifier + "-" + entryDto.getProductIdentifier());
            orderEntry.setOrderIdentifier(orderIdentifier);
            orderEntry.setProductIdentifier(entryDto.getProductIdentifier());
            orderEntry.setQuantity(entryDto.getQuantity());
            orderEntry.setUnitPrice(entryDto.getUnitPrice());
            orderEntry.setOriginalPrice(entryDto.getOriginalPrice() != null ? entryDto.getOriginalPrice() : entryDto.getUnitPrice());
            orderEntry.setDiscount(entryDto.getDiscount() != null ? entryDto.getDiscount() : BigDecimal.ZERO);
            orderEntry.setTotalPrice(entryDto.getTotalPrice());

            orderEntryRepository.save(orderEntry);
        }

        // 5. Build clean ModelMapper response dto output directly
        OrderDto responseDto = modelMapper.map(order, OrderDto.class);
        Type entryListType = new TypeToken<List<OrderEntryDto>>() {}.getType();
        List<OrderEntry> orderEntryList = orderEntryRepository.findByOrderIdentifier(orderIdentifier);

        responseDto.setEntryList(modelMapper.map(orderEntryList, entryListType));
        responseDto.setSuccess(true);
        responseDto.setMessage("Order processed and saved directly to ledger successfully!");

        return responseDto;
    }

    @Override
    public OrderDto get(String identifier) {
        Order order = orderRepository.findByIdentifier(identifier);
        if (order == null) {
            OrderDto orderDto = new OrderDto();
            orderDto.setSuccess(false);
            orderDto.setMessage("Order registry trace not found");
            return orderDto;
        }

        OrderDto orderDto = modelMapper.map(order, OrderDto.class);
        List<OrderEntry> orderEntryList = orderEntryRepository.findByOrderIdentifier(identifier);
        Type entryListType = new TypeToken<List<OrderEntryDto>>() {}.getType();
        orderDto.setEntryList(modelMapper.map(orderEntryList, entryListType));

        return orderDto;
    }

    @Override
    public WsDto<OrderDto> findAll(Pageable pageable) {
        Type listType = new TypeToken<List<OrderDto>>() {}.getType();
        Page<Order> orderPage = orderRepository.findAll(pageable);

        WsDto<OrderDto> orderWsDto = new WsDto<>();
        orderWsDto.setDtoList(modelMapper.map(orderPage.getContent(), listType));
        orderWsDto.setTotalRecords(orderPage.getTotalElements());
        orderWsDto.setTotalPage(orderPage.getTotalPages());
        orderWsDto.setSizePerPage(pageable.getPageSize());
        orderWsDto.setPage(pageable.getPageNumber());
        return orderWsDto;
    }

    @Override
    @Transactional
    public boolean delete(String identifier) {
        orderEntryRepository.deleteByOrderIdentifier(identifier);
        orderRepository.deleteByIdentifier(identifier);
        return true;
    }
}