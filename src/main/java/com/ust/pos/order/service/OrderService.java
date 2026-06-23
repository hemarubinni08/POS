package com.ust.pos.order.service;

import com.ust.pos.dto.OrderDto;
import com.ust.pos.dto.WsDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface OrderService {
    OrderDto processCheckout(OrderDto orderDto);
    OrderDto getOrderDetails(String identifier);
    List<OrderDto> getAllOrdersList();
}