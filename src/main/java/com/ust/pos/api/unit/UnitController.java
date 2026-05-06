package com.ust.pos.api.unit;

import com.ust.pos.dto.UnitDto;
import com.ust.pos.unit.service.UnitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("unitApiController")
@RequestMapping("/api/units")
public class UnitController {

    @Autowired
    private UnitService unitService;

    // GET ALL UNITS
    @GetMapping
    public List<UnitDto> getAll() {
        return unitService.findAll();
    }

    // GET UNIT BY IDENTIFIER
    @GetMapping("/{identifier}")
    public UnitDto getByIdentifier(@PathVariable String identifier) {
        return unitService.findByIdentifier(identifier);
    }

    // CREATE UNIT
    @PostMapping
    public UnitDto create(@RequestBody UnitDto unitDto) {
        return unitService.save(unitDto);
    }

    // UPDATE UNIT
    @PutMapping("/{identifier}")
    public UnitDto update(@PathVariable String identifier,
                          @RequestBody UnitDto unitDto) {
        unitDto.setIdentifier(identifier);
        return unitService.update(unitDto);
    }

    // DELETE UNIT
    @DeleteMapping("/{identifier}")
    public boolean delete(@PathVariable String identifier) {
        try {
            unitService.delete(identifier);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // TOGGLE STATUS
    @PatchMapping("/{identifier}/toggle")
    public UnitDto toggleStatus(@PathVariable String identifier) {
        return unitService.toggleStatus(identifier);
    }
}