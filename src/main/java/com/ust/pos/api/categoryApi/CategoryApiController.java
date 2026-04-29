package com.ust.pos.api.categoryApi;

import com.ust.pos.dto.CategoryDto;
import com.ust.pos.category.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/category")
public class CategoryApiController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/list")
    public List<CategoryDto> list() {

        return categoryService.findAll();
    }

    @PostMapping("/add")
    public CategoryDto add(@RequestBody CategoryDto categoryDto) {
        return categoryService.save(categoryDto);
    }


    @GetMapping("/get")
    public CategoryDto get(@RequestParam String identifier) {
        return categoryService.findByIdentifier(identifier);
    }

    @PostMapping("/update")
    public CategoryDto update(@RequestBody CategoryDto categoryDto) {
        return categoryService.update(categoryDto);
    }


    @GetMapping("/delete")
    public boolean delete(@RequestParam String identifier) {
        try {
            categoryService.delete(identifier);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
