package com.ust.pos.api.models;

import com.ust.pos.dto.ModelsDto;
import com.ust.pos.models.service.ModelsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("modelsApiController")
@RequestMapping("/api/models")
public class ModelsController {

    @Autowired
    private ModelsService modelsService;

    @GetMapping("/list")
    public List<ModelsDto> list() {
        return modelsService.findAll();
    }

    @GetMapping("/get")
    public ModelsDto get(@RequestParam String identifier) {
        return modelsService.findByIdentifier(identifier);
    }

    @PostMapping("/add")
    public ModelsDto add(@RequestBody ModelsDto modelsDto) {
        return modelsService.save(modelsDto);
    }

    @PostMapping("/update")
    public ModelsDto update(@RequestBody ModelsDto modelsDto) {
        return modelsService.update(modelsDto);
    }

    @GetMapping("/delete")
    public boolean delete(@RequestParam String identifier) {
        try {
            modelsService.delete(identifier);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}