package com.ust.pos.invoice.service;

import com.ust.pos.dto.InvoiceDto;
import com.ust.pos.dto.OrderDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface InvoiceService {
    InvoiceDto generate(String orderId, String cartId, String paymentReference, OrderDto orderTotals);
    InvoiceDto findByIdentifier(String identifier);
    InvoiceDto findByOrderId(String orderId);
    List<InvoiceDto> findAll(Pageable pageable);
}