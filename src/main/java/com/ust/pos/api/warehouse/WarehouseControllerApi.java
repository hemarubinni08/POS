package com.ust.pos.api.warehouse;

import com.ust.pos.dto.WarehouseDto;
import com.ust.pos.warehouse.service.WarehouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/warehouse")
public class WarehouseControllerApi {

    public static final String REDIRECT_ROLE_LIST = "redirect:/warehouse/list";

    @Autowired
    private WarehouseService warehouseService;

    @GetMapping("/list")
    public List<WarehouseDto> home() {
        return warehouseService.findAll();
    }

    @PostMapping("/add")
    public WarehouseDto addPost(@RequestBody WarehouseDto warehouseDto) {
        return warehouseService.save(warehouseDto);
    }

    @GetMapping("/get")
    public WarehouseDto update(@RequestParam String identifier) {
        return warehouseService.findByIdentifier(identifier);
    }

    @PostMapping("/update")
    public WarehouseDto updatePost(@RequestBody WarehouseDto warehouseDto) {
        return warehouseService.update(warehouseDto);
    }

    @GetMapping("/delete")
    public boolean delete(@RequestParam String identifier) {
        try {
            warehouseService.delete(identifier);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    @GetMapping("/active")
    public List<WarehouseDto> findAllActive() {
        return warehouseService.findAllActive();
    }

    @PostMapping("/changestatus")
    public WarehouseDto changeStatus(@RequestBody WarehouseDto warehouseDto) {
        return warehouseService.updateStatus(warehouseDto.getIdentifier(), warehouseDto.isStatus());
    }
}
