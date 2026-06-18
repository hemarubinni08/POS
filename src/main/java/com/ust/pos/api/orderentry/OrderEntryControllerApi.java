package com.ust.pos.api.orderentry;

import com.ust.pos.orderentry.service.OrderEntryService;
import com.ust.pos.dto.OrderEntryDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/order-entries")
public class OrderEntryControllerApi {

    @Autowired
    private OrderEntryService orderEntryService;

    @GetMapping
    public ResponseEntity<List<OrderEntryDto>> findAll(Pageable pageable) {
        return ResponseEntity.ok(orderEntryService.findAll(pageable));
    }

    @GetMapping("/order/{orderId}")
    public ResponseEntity<List<OrderEntryDto>> findByOrderId(@PathVariable String orderId) {
        return ResponseEntity.ok(orderEntryService.findByOrderId(orderId));
    }

    @GetMapping("/{identifier}")
    public ResponseEntity<OrderEntryDto> findByIdentifier(@PathVariable String identifier) {
        OrderEntryDto dto = orderEntryService.findByIdentifier(identifier);
        if (!dto.isSuccess()) {
            return ResponseEntity.badRequest().body(dto);
        }
        return ResponseEntity.ok(dto);
    }

    @PostMapping
    public ResponseEntity<OrderEntryDto> save(@RequestBody OrderEntryDto dto) {
        OrderEntryDto response = orderEntryService.save(dto);
        if (!response.isSuccess()) {
            return ResponseEntity.badRequest().body(response);
        }
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{identifier}")
    public ResponseEntity<Void> delete(@PathVariable String identifier) {
        orderEntryService.delete(identifier);
        return ResponseEntity.noContent().build();
    }
}