package com.ust.pos.api.category;

import com.ust.pos.category.service.CategoryService;
import com.ust.pos.dto.CategoryDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("categoryApiController")
@RequestMapping("/api/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    // Get all categories
    @GetMapping("/list")
    public List<CategoryDto> list() {
        return categoryService.findAll();
    }

    // Get category by identifier
    @GetMapping("/get")
    public CategoryDto get(@RequestParam String identifier) {
        return categoryService.findByIdentifier(identifier);
    }

    // Add new category
    @PostMapping("/add")
    public CategoryDto add(@RequestBody CategoryDto dto) {
        return categoryService.save(dto);
    }

    // Update category
    @PostMapping("/update")
    public CategoryDto update(@RequestBody CategoryDto dto) {
        return categoryService.update(dto);
    }

    // Delete category by identifier
    @DeleteMapping("/delete")
    public boolean delete(@RequestParam String identifier) {
        return categoryService.delete(identifier);
    }

    // Toggle active / inactive status
    @PostMapping("/toggle-status")
    public CategoryDto toggleStatus(@RequestParam String identifier) {
        return categoryService.toggleStatus(identifier);
    }

    // Get only active categories
    @GetMapping("/active")
    public List<CategoryDto> activeCategories() {
        return categoryService.findIfTrue();
    }

    // Get categories having super category
    @GetMapping("/sub-categories")
    public List<CategoryDto> subCategories() {
        return categoryService.findBySuperCategoryNotNull();
    }
}