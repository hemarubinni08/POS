package com.ust.pos.order.service;

import com.ust.pos.dto.OrderDto;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.List;

public interface OrderService {
    List<OrderDto> findAll(Pageable pageable);
    OrderDto findByIdentifier(String identifier);
    OrderDto save(String identifier);
    void delete(String identifier);
    OrderDto recalculate(String identifier);
    OrderDto applyTotals(String identifier, BigDecimal originalPrice, BigDecimal totalPrice, BigDecimal discount);
}