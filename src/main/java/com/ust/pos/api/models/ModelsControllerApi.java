package com.ust.pos.api.models;

import com.ust.pos.dto.ModelsDto;
import com.ust.pos.models.service.ModelsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/models")
public class ModelsControllerApi {

    public static final String REDIRECT_ROLE_LIST = "redirect:/models/list";
    @Autowired
    private ModelsService modelsService;

    @GetMapping("/list")
    public List<ModelsDto> list() {
        return modelsService.findAll();
    }

    @PostMapping("/add")
    public ModelsDto addPost(@RequestBody ModelsDto modelsDto) {
        return modelsService.save(modelsDto);
    }

    @GetMapping("/get")
    public ModelsDto updatePage(@RequestParam String identifier) {
        return modelsService.findByIdentifier(identifier);
    }

    @PostMapping("/update")
    public ModelsDto updatePost(@RequestBody ModelsDto modelsDto) {
        return modelsService.update(modelsDto);
    }

    @GetMapping("/delete")
    public Boolean delete(@RequestParam String identifier) {

        try {
            modelsService.delete(identifier);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    @PostMapping("/toggle-status")
    public ModelsDto toggleStatus(@RequestBody ModelsDto modelsDto) {
        return modelsService.toggleStatus(modelsDto.getIdentifier());
    }

    @GetMapping("/active")
    public List<ModelsDto> getActiveModels() {
        return modelsService.findActiveModels();
    }
}