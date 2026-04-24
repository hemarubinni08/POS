package com.ust.pos.category;

import com.ust.pos.category.service.CategoryService;
import com.ust.pos.dto.CategoryDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/category")
public class CategoryController {

    public static final String REDIRECT_CATEGORY_LIST = "redirect:/category/list";

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/list")
    public String list(Model model) {
        model.addAttribute("categories", categoryService.getAllCategories());
        return "category/list";
    }

    @GetMapping("/add")
    public String add(Model model) {
        model.addAttribute("categoryDto", new CategoryDto());
        return "category/add";
    }

    @PostMapping("/add")
    public String addPost(@ModelAttribute CategoryDto dto, Model model) {

        CategoryDto response = categoryService.createCategory(dto);

        if (!response.isSuccess()) {
            model.addAttribute("message", response.getMessage());
            model.addAttribute("messageType", "error");
            return "category/add";
        }

        return REDIRECT_CATEGORY_LIST;
    }

    @GetMapping("/get")
    public String update(@RequestParam Long id, Model model) {

        CategoryDto dto = categoryService.getCategory(id);

        if (!dto.isSuccess()) {
            model.addAttribute("message", dto.getMessage());
            return REDIRECT_CATEGORY_LIST;
        }

        model.addAttribute("category", dto);
        return "category/category";
    }

    @PostMapping("/update")
    public String updatePost(@ModelAttribute CategoryDto dto, Model model) {

        CategoryDto response = categoryService.updateCategory(dto);

        if (!response.isSuccess()) {
            model.addAttribute("category", dto);
            model.addAttribute("message", response.getMessage());
            return "category/category";
        }

        return REDIRECT_CATEGORY_LIST;
    }
    
    @GetMapping("/delete")
    public String delete(@RequestParam Long id) {

        categoryService.deleteCategory(id);
        return REDIRECT_CATEGORY_LIST;
    }
}
