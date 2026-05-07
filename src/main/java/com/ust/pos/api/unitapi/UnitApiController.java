package com.ust.pos.api.unitapi;


import com.ust.pos.dto.UnitDto;
import com.ust.pos.unit.service.UnitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/unit")
public class UnitApiController {

    @Autowired
    private UnitService unitService;

    @GetMapping("/list")
    public List<UnitDto> home(Model model) {
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
        try{ unitService.delete(identifier);}
        catch (Exception e){
            return false;
        }
        return true;
    }
    @PostMapping("/toggle-status")
    public UnitDto toggle(@RequestParam String identifier){
        return unitService.toggleStatus(identifier);
    }

    @GetMapping("/findByStatus")
    public List<UnitDto> findByStatus() {

        return unitService.findIfTrue();
    }
}