package com.ust.pos.api.category;

import com.ust.pos.category.service.CategoryService;
import com.ust.pos.dto.CategoryDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("categoryApiController")
@RequestMapping("/api/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    // GET ALL
    @GetMapping
    public List<CategoryDto> getAll() {
        return categoryService.findAll();
    }

    // GET BY IDENTIFIER
    @GetMapping("/{identifier}")
    public CategoryDto getByIdentifier(@PathVariable String identifier) {
        return categoryService.findByIdentifier(identifier);
    }

    // CREATE
    @PostMapping
    public CategoryDto create(@RequestBody CategoryDto dto) {
        return categoryService.save(dto);
    }

    // UPDATE
    @PutMapping("/{identifier}")
    public CategoryDto update(@PathVariable String identifier,
                              @RequestBody CategoryDto dto) {
        dto.setIdentifier(identifier);
        return categoryService.update(dto);
    }

    // DELETE
    @DeleteMapping("/{identifier}")
    public boolean delete(@PathVariable String identifier) {
        try {
            return categoryService.delete(identifier);
        } catch (Exception e) {
            return false;
        }
    }

    // TOGGLE STATUS
    @PatchMapping("/{identifier}/toggle")
    public CategoryDto toggleStatus(@PathVariable String identifier) {
        return categoryService.toggleStatus(identifier);
    }

    // GET ACTIVE CATEGORIES
    @GetMapping("/active")
    public List<CategoryDto> activeCategories() {
        return categoryService.findIfTrue();
    }

    // GET SUB-CATEGORIES (with parent)
    @GetMapping("/sub-categories")
    public List<CategoryDto> subCategories() {
        return categoryService.findSuperCategories();
    }
}