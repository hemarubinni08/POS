package com.ust.pos.api.unit;

import com.ust.pos.dto.UnitDto;
import com.ust.pos.unit.service.UnitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("unitApiController")
@RequestMapping("/api/unit")
public class UnitController {

    @Autowired
    private UnitService unitService;

    @GetMapping("/list")
    public List<UnitDto> list() {
        return unitService.findAll();
    }

    @GetMapping("/get")
    public UnitDto get(@RequestParam String identifier) {
        return unitService.findByIdentifier(identifier);
    }

    @PostMapping("/add")
    public UnitDto add(@RequestBody UnitDto unitDto) {
        return unitService.save(unitDto);
    }

    @PostMapping("/update")
    public UnitDto update(@RequestBody UnitDto unitDto) {
        return unitService.update(unitDto);
    }

    @GetMapping("/delete")
    public boolean delete(@RequestParam String identifier) {
        try {
            unitService.delete(identifier);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}