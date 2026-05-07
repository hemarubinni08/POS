package com.ust.pos.api.unit;

import com.ust.pos.dto.UnitDto;
import com.ust.pos.unit.service.UnitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/unit")
public class UnitControllerApi {

    public static final String REDIRECT_ROLE_LIST = "redirect:/unit/list";

    @Autowired
    private UnitService unitService;

    @GetMapping("/list")
    public List<UnitDto> home() {
        return unitService.findAll();
    }

    @PostMapping("/add")
    public UnitDto addPost(@RequestBody UnitDto unitDto) {
        return unitService.save(unitDto);
    }

    @GetMapping("/get")
    public UnitDto update(@RequestParam String identifier) {
        return unitService.findByIdentifier(identifier);
    }

    @PostMapping("/update")
    public UnitDto updatePost(@RequestBody UnitDto unitDto) {
        return unitService.update(unitDto);
    }

    @GetMapping("/delete")
    public boolean delete(@RequestParam String identifier) {
        try {
            unitService.delete(identifier);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    @GetMapping("/active")
    public List<UnitDto> findAllActive() {
        return unitService.findAllActive();
    }

    @PostMapping("/changestatus")
    public UnitDto changeStatus(@RequestBody UnitDto unitDto) {
        return unitService.updateStatus(unitDto.getIdentifier(), unitDto.isStatus());
    }
}
