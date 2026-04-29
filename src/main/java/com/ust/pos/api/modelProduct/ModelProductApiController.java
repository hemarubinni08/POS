package com.ust.pos.api.modelProduct;

import com.ust.pos.dto.ModelProductDto;
import com.ust.pos.modelProduct.service.ModelProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/modelProduct")
public class ModelProductApiController {

    @Autowired
    private ModelProductService modelProductService;

    @GetMapping("/list")
    public List<ModelProductDto> home() {
        return modelProductService.findAll();
    }

    @PostMapping("/add")
    public ModelProductDto addPost(@RequestBody ModelProductDto modelProductDto) {
        return modelProductService.save(modelProductDto);
    }

    @GetMapping("/get")
    public ModelProductDto update( @RequestParam String identifier) {
        return modelProductService.findByIdentifier(identifier);
    }

    @PostMapping("/update")
    public ModelProductDto updatePost(@RequestBody ModelProductDto modelProductDto) {
        return modelProductService.update(modelProductDto);
    }

    @GetMapping("/delete")
    public boolean delete(@RequestParam String identifier) {
        try{
            modelProductService.delete(identifier);
        }catch(Exception e){
            return false;
        }
        return true;
    }

    @PostMapping("/toggleStatus")
    public boolean toggleStatus(@RequestBody String identifier){
        try {
            modelProductService.toggleStatus(identifier);
        } catch (Exception e) {
            return false;
        }
        return true;
    }
}
