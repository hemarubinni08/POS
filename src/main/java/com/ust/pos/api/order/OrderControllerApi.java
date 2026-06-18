package com.ust.pos.api.order;

import com.ust.pos.order.service.OrderService;
import com.ust.pos.dto.OrderDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderControllerApi {

    @Autowired
    private OrderService orderService;

    @GetMapping
    public ResponseEntity<List<OrderDto>> findAll(Pageable pageable) {
        return ResponseEntity.ok(orderService.findAll(pageable));
    }

    @GetMapping("/{identifier}")
    public ResponseEntity<OrderDto> findByIdentifier(@PathVariable String identifier) {
        OrderDto dto = orderService.findByIdentifier(identifier);
        if (!dto.isSuccess()) {
            return ResponseEntity.badRequest().body(dto);
        }
        return ResponseEntity.ok(dto);
    }

    @DeleteMapping("/{identifier}")
    public ResponseEntity<Void> delete(@PathVariable String identifier) {
        orderService.delete(identifier);
        return ResponseEntity.noContent().build();
    }
}