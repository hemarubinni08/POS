package com.ust.pos.api.warehouse;

import com.ust.pos.dto.WarehouseDto;
import com.ust.pos.warehouse.service.WarehouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("warehouseApiController")
@RequestMapping("/api/warehouse")
public class WarehouseController {

    @Autowired
    private WarehouseService warehouseService;

    /* ================= LIST ================= */

    @GetMapping("/list")
    public List<WarehouseDto> list() {
        return warehouseService.findAll();
    }

    /* ================= GET ================= */

    @GetMapping("/get")
    public WarehouseDto get(@RequestParam String identifier) {
        return warehouseService.findByIdentifier(identifier);
    }

    /* ================= ADD ================= */

    @PostMapping("/add")
    public WarehouseDto add(@RequestBody WarehouseDto warehouseDto) {
        return warehouseService.save(warehouseDto);
    }

    /* ================= UPDATE ================= */

    @PostMapping("/update")
    public WarehouseDto update(@RequestBody WarehouseDto warehouseDto) {
        return warehouseService.update(warehouseDto);
    }

    /* ================= DELETE ================= */

    @GetMapping("/delete")
    public boolean delete(@RequestParam String identifier) {
        try {
            warehouseService.delete(identifier);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /* ================= TOGGLE STATUS ================= */

    @GetMapping("/toggle-status")
    public boolean toggleStatus(@RequestParam String identifier) {
        try {
            warehouseService.toggleStatus(identifier);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}