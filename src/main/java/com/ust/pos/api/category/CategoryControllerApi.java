package com.ust.pos.api.category;

import com.ust.pos.category.service.CategoryService;
import com.ust.pos.dto.CategoryDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/category")
public class CategoryControllerApi {

    public static final String REDIRECT_ROLE_LIST = "redirect:/category/list";
    @Autowired
    private CategoryService categoryService;

    @GetMapping("/list")
    public List<CategoryDto> list() {
        return categoryService.findAll();
    }

    @PostMapping("/add")
    public CategoryDto addPost(@RequestBody CategoryDto categoryDto) {
        return categoryService.save(categoryDto);
    }

    @GetMapping("/get")
    public CategoryDto updatePage(@RequestParam String identifier) {
        return categoryService.findByIdentifier(identifier);
    }

    @PostMapping("/update")
    public CategoryDto updatePost(@RequestBody CategoryDto categoryDto) {
        return categoryService.update(categoryDto);
    }

    @GetMapping("/delete")
    public Boolean delete(@RequestParam String identifier) {

        try {
            categoryService.delete(identifier);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    @GetMapping("/findChildCategories")
    public List<CategoryDto> findChildCategories() {
        return categoryService.findChildCategories();
    }

    @GetMapping("/super")
    public List<CategoryDto> findSuperCategories() {
        return categoryService.findSuperCategories();
    }

    @GetMapping("/leaf")
    public List<CategoryDto> findLeafCategories() {
        return categoryService.findLeafCategories();
    }
}