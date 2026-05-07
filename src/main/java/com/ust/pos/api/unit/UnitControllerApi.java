package com.ust.pos.api.unit;

import com.ust.pos.dto.UnitDto;
import com.ust.pos.unit.service.UnitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/unit")
public class UnitControllerApi {

    @Autowired
    private UnitService unitService;

    // LIST
    @GetMapping("/list")
    public List<UnitDto> list() {
        return unitService.findAll();
    }

    // ADD
    @PostMapping("/add")
    public UnitDto add(@RequestBody UnitDto unitDto) {
        return unitService.save(unitDto);
    }

    // GET
    @GetMapping("/get")
    public UnitDto get(@RequestParam String identifier) {
        return unitService.findByIdentifier(identifier);
    }

    // UPDATE
    @PostMapping("/update")
    public UnitDto update(@RequestBody UnitDto unitDto) {
        return unitService.update(unitDto);
    }

    // DELETE
    @GetMapping("/delete")
    public Boolean delete(@RequestParam String identifier) {
        try {
            unitService.delete(identifier);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // TOGGLE STATUS
    @GetMapping("/toggle")
    public UnitDto toggle(@RequestParam String identifier) {
        return unitService.toggleStatus(identifier);
    }

    // ACTIVE LIST
    @GetMapping("/active")
    public List<UnitDto> active() {
        return unitService.findActiveUnits();
    }
}