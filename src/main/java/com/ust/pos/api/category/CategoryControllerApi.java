package com.ust.pos.api.category;

import com.ust.pos.category.service.CategoryService;
import com.ust.pos.dto.CategoryDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/category")
public class CategoryControllerApi {

    @Autowired
    private CategoryService categoryService;

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

    @PostMapping("/update")
    public CategoryDto updatePost(@RequestBody CategoryDto categoryDto) {
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

    @PostMapping("/changestatus")
    public CategoryDto changeStatus(@RequestBody CategoryDto categoryDto) {
        return categoryService.updateStatus(categoryDto.getIdentifier(), categoryDto.isStatus());
    }

    @GetMapping("/active")
    public List<CategoryDto> findAllActive() {
        return categoryService.findAllActive();
    }

    @GetMapping("/activechilds")
    public List<CategoryDto> findAllActiveChild() {
        return categoryService.findAllActiveChilds();
    }
}
