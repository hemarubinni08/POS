package com.ust.pos.api.category;

import com.ust.pos.category.service.CategoryService;
import com.ust.pos.dto.CategoryDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/api/category")
public class CategoryApiController {
    @Autowired
    CategoryService categoryService;

    @GetMapping("/list")
    public List<CategoryDto> home() {
        return categoryService.findAll();
    }

    @GetMapping("/add")
    public List<CategoryDto> add(@RequestBody CategoryDto categoryDto) {
        return categoryService.findAll();
    }

    @GetMapping("/get")
    public CategoryDto update(@RequestParam String identifier) {
        return categoryService.findByIdentifier(identifier);
    }

    @PostMapping("/update")
    public CategoryDto get(@RequestBody CategoryDto categoryDto) {
        return categoryService.update(categoryDto);
    }

    @GetMapping("/delete")
    public boolean delete(@RequestParam String identifier) {
        try {
            categoryService.delete(identifier);
        } catch (Exception e) {
            return false;
        }
        return true;
    }
}
