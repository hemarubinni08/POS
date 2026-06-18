package com.ust.pos.orderentry.service;

import com.ust.pos.dto.OrderEntryDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface OrderEntryService {
    List<OrderEntryDto> findAll(Pageable pageable);
    List<OrderEntryDto> findByOrderId(String orderId);
    OrderEntryDto findByIdentifier(String identifier);
    OrderEntryDto save(OrderEntryDto orderEntryDto);
    List<OrderEntryDto> saveAll(List<OrderEntryDto> orderEntryDtos);
    void delete(String identifier);
}