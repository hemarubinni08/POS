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

    @GetMapping
    public List<CategoryDto> getAll() {
        return categoryService.findAll();
    }

    @GetMapping("/{identifier}")
    public CategoryDto getByIdentifier(@PathVariable String identifier) {
        return categoryService.findByIdentifier(identifier);
    }

    @PostMapping("/save")
    public CategoryDto save(@RequestBody CategoryDto dto) {
        return categoryService.save(dto);
    }

    @PostMapping("/update/{identifier}")
    public CategoryDto update(@PathVariable String identifier,
                              @RequestBody CategoryDto dto) {
        dto.setIdentifier(identifier);
        return categoryService.update(dto);
    }

    @PostMapping("/delete/{identifier}")
    public boolean delete(@PathVariable String identifier) {
        try {
            return categoryService.delete(identifier);
        } catch (Exception e) {
            return false;
        }
    }

    @PostMapping("/toggle/{identifier}")
    public CategoryDto toggleStatus(@PathVariable String identifier) {
        return categoryService.toggleStatus(identifier);
    }

    @GetMapping("/active")
    public List<CategoryDto> activeCategories() {
        return categoryService.findIfTrue();
    }

    @GetMapping("/sub-categories")
    public List<CategoryDto> subCategories() {
        return categoryService.findSuperCategories();
    }
}