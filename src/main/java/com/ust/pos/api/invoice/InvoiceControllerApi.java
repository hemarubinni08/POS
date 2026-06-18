package com.ust.pos.api.invoice;

import com.ust.pos.invoice.service.InvoiceService;
import com.ust.pos.dto.InvoiceDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/invoices")
public class InvoiceControllerApi {

    @Autowired
    private InvoiceService invoiceService;

    @GetMapping
    public ResponseEntity<List<InvoiceDto>> findAll(Pageable pageable) {
        return ResponseEntity.ok(invoiceService.findAll(pageable));
    }

    @GetMapping("/{identifier}")
    public ResponseEntity<InvoiceDto> findByIdentifier(@PathVariable String identifier) {
        InvoiceDto dto = invoiceService.findByIdentifier(identifier);
        if (!dto.isSuccess()) {
            return ResponseEntity.badRequest().body(dto);
        }
        return ResponseEntity.ok(dto);
    }

    @GetMapping("/order/{orderId}")
    public ResponseEntity<InvoiceDto> findByOrderId(@PathVariable String orderId) {
        InvoiceDto dto = invoiceService.findByOrderId(orderId);
        if (!dto.isSuccess()) {
            return ResponseEntity.badRequest().body(dto);
        }
        return ResponseEntity.ok(dto);
    }
}