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

    @GetMapping("/list")
    public List<CategoryDto> list() {
        return categoryService.getAllCategories();
    }

    @GetMapping("/get")
    public CategoryDto get(@RequestParam Long id) {
        return categoryService.getCategory(id);
    }

    @PostMapping("/add")
    public CategoryDto add(@RequestBody CategoryDto dto) {
        return categoryService.createCategory(dto);
    }

    @PostMapping("/update")
    public CategoryDto update(@RequestBody CategoryDto dto) {
        return categoryService.updateCategory(dto);
    }

    @GetMapping("/delete")
    public boolean delete(@RequestParam Long id) {
        try {
            categoryService.deleteCategory(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}