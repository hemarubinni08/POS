package com.ust.pos.invoice.service.impl;

import com.ust.pos.dto.InvoiceDto;
import com.ust.pos.dto.OrderDto;
import com.ust.pos.invoice.service.InvoiceService;
import com.ust.pos.model.Invoice;
import com.ust.pos.model.InvoiceRepository;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class InvoiceServiceImpl implements InvoiceService {

    @Autowired
    private InvoiceRepository invoiceRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public InvoiceDto generate(String orderId, String cartId, String paymentReference, OrderDto orderTotals) {
        Invoice invoice = new Invoice();
        invoice.setIdentifier("INV-" + UUID.randomUUID());
        invoice.setOrderId(orderId);
        invoice.setCartId(cartId);
        invoice.setPaymentReference(paymentReference);
        invoice.setOriginalPrice(orderTotals.getOriginalPrice());
        invoice.setTotalPrice(orderTotals.getTotalPrice());
        invoice.setDiscount(orderTotals.getDiscount());
        invoice.setGeneratedAt(LocalDateTime.now());

        Invoice saved = invoiceRepository.save(invoice);
        InvoiceDto dto = modelMapper.map(saved, InvoiceDto.class);
        dto.setSuccess(true);
        dto.setMessage("Invoice generated successfully");
        return dto;
    }

    @Override
    public InvoiceDto findByIdentifier(String identifier) {
        Invoice invoice = invoiceRepository.findByIdentifier(identifier);
        if (invoice == null) {
            InvoiceDto dto = new InvoiceDto();
            dto.setSuccess(false);
            dto.setMessage("Invoice not found");
            return dto;
        }
        InvoiceDto dto = modelMapper.map(invoice, InvoiceDto.class);
        dto.setSuccess(true);
        return dto;
    }

    @Override
    public InvoiceDto findByOrderId(String orderId) {
        Invoice invoice = invoiceRepository.findByOrderId(orderId);
        if (invoice == null) {
            InvoiceDto dto = new InvoiceDto();
            dto.setSuccess(false);
            dto.setMessage("Invoice not found for order: " + orderId);
            return dto;
        }
        InvoiceDto dto = modelMapper.map(invoice, InvoiceDto.class);
        dto.setSuccess(true);
        return dto;
    }

    @Override
    public List<InvoiceDto> findAll(Pageable pageable) {
        Page<Invoice> page = invoiceRepository.findAll(pageable);
        Type listType = new TypeToken<List<InvoiceDto>>() {
        }.getType();
        return modelMapper.map(page.getContent(), listType);
    }
}