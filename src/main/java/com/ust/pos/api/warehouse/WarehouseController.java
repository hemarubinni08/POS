package com.ust.pos.api.warehouse;

import com.ust.pos.dto.WarehouseDto;
import com.ust.pos.warehouse.service.WarehouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("warehouseApiController")
@RequestMapping("/api/warehouses")
public class WarehouseController {

    @Autowired
    private WarehouseService warehouseService;

    @GetMapping
    public ResponseEntity<List<WarehouseDto>> getAll() {
        return ResponseEntity.ok(warehouseService.findAll());
    }

    @GetMapping("/{identifier}")
    public ResponseEntity<WarehouseDto> getByIdentifier(@PathVariable String identifier) {
        return ResponseEntity.ok(warehouseService.findByIdentifier(identifier));
    }

    @PostMapping
    public ResponseEntity<WarehouseDto> create(@RequestBody WarehouseDto warehouseDto) {
        return ResponseEntity.ok(warehouseService.save(warehouseDto));
    }

    @PutMapping("/{identifier}")
    public ResponseEntity<WarehouseDto> update(@PathVariable String identifier,
                                               @RequestBody WarehouseDto warehouseDto) {
        warehouseDto.setIdentifier(identifier);
        return ResponseEntity.ok(warehouseService.update(warehouseDto));
    }

    @DeleteMapping("/{identifier}")
    public ResponseEntity<Void> delete(@PathVariable String identifier) {
        warehouseService.delete(identifier);
        return ResponseEntity.noContent().build(); // 204
    }

    @PatchMapping("/{identifier}/toggle")
    public ResponseEntity<Void> toggleStatus(@PathVariable String identifier) {
        warehouseService.toggleStatus(identifier);
        return ResponseEntity.ok().build();
    }
}