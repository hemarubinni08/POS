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

    // GET ALL MODELS
    @GetMapping
    public List<ModelsDto> getAll() {
        return modelsService.findAll();
    }

    // GET BY IDENTIFIER
    @GetMapping("/{identifier}")
    public ModelsDto getByIdentifier(@PathVariable String identifier) {
        return modelsService.findByIdentifier(identifier);
    }

    // CREATE MODEL
    @PostMapping
    public ModelsDto create(@RequestBody ModelsDto modelsDto) {
        return modelsService.save(modelsDto);
    }

    // UPDATE MODEL
    @PutMapping("/{identifier}")
    public ModelsDto update(@PathVariable String identifier,
                            @RequestBody ModelsDto modelsDto) {
        modelsDto.setIdentifier(identifier);
        return modelsService.update(modelsDto);
    }

    // DELETE MODEL
    @DeleteMapping("/{identifier}")
    public boolean delete(@PathVariable String identifier) {
        try {
            return modelsService.delete(identifier);
        } catch (Exception e) {
            return false;
        }
    }

    // TOGGLE STATUS
    @PatchMapping("/{identifier}/toggle")
    public ModelsDto toggleStatus(@PathVariable String identifier) {
        return modelsService.toggleStatus(identifier);
    }
}