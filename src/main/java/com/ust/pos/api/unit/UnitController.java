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

    @GetMapping
    public List<UnitDto> getAll() {
        return unitService.findAll();
    }

    @GetMapping("/{identifier}")
    public UnitDto getByIdentifier(@PathVariable String identifier) {
        return unitService.findByIdentifier(identifier);
    }

    @PostMapping("/save")
    public UnitDto save(@RequestBody UnitDto unitDto) {
        return unitService.save(unitDto);
    }

    @PostMapping("/update/{identifier}")
    public UnitDto update(@PathVariable String identifier,
                          @RequestBody UnitDto unitDto) {
        unitDto.setIdentifier(identifier);
        return unitService.update(unitDto);
    }

    @PostMapping("/delete/{identifier}")
    public boolean delete(@PathVariable String identifier) {
        try {
            unitService.delete(identifier);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @PostMapping("/toggle/{identifier}")
    public UnitDto toggleStatus(@PathVariable String identifier) {
        return unitService.toggleStatus(identifier);
    }
}