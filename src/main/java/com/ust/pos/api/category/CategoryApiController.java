package com.ust.pos.api.category;

import com.ust.pos.category.service.CategoryService;
import com.ust.pos.dto.CategoryDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/category")
public class CategoryApiController {
    @Autowired
    CategoryService categoryService;

    @GetMapping("/list")
    public List<CategoryDto> home() {
        return categoryService.findAll();
    }

    @PostMapping("/add")
    public CategoryDto addPost(@RequestBody CategoryDto categoryDto) {
        return categoryService.save(categoryDto);
    }

    @GetMapping("/get")
    public CategoryDto update(@RequestParam String identifier) {
        return categoryService.findByIdentifier(identifier);
    }

    @PostMapping("/get")
    public CategoryDto get(@RequestBody CategoryDto categoryDto) {
        return categoryService.update(categoryDto);

    }

    @GetMapping("/delete")
    public boolean delete(Model model, @RequestParam String identifier) {
        try {
            categoryService.delete(identifier);
        } catch (Exception e) {
            return false;
        }
        return true;
    }
}
