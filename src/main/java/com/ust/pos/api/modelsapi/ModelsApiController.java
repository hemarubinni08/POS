package com.ust.pos.api.modelsapi;

import com.ust.pos.dto.ModelsDto;
import com.ust.pos.models.service.ModelsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/models")
public class ModelsApiController {
    @Autowired
    private ModelsService modelsService;

    @GetMapping("/list")
    public List<ModelsDto> home() {
        return modelsService.findAll();
    }

    @PostMapping("/add")
    public ModelsDto addPost(@RequestBody ModelsDto modelsDto) {
        return modelsService.save(modelsDto);
    }

    @GetMapping("/get")
    public ModelsDto update(@RequestParam String identifier) {
        return modelsService.findByIdentifier(identifier);
    }

    @PostMapping("/update")
    public ModelsDto updatePost( @RequestBody ModelsDto modelsDto) {
        return modelsService.update(modelsDto);
    }

    @GetMapping("/delete")
    public boolean delete(@RequestParam String identifier) {
        try { modelsService.delete(identifier);}
        catch(Exception e){
            return false;
        }
        return true;
    }

    @PostMapping("/toggle-status")
    public ModelsDto toggle(@RequestParam String identifier){

        return modelsService.toggleStatus(identifier);
    }


    @GetMapping("/findByStatus")
    public List<ModelsDto> findByStatus() {

        return modelsService.findIfTrue();
    }
}
