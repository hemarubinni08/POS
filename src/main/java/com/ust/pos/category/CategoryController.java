package com.ust.pos.category;

import com.ust.pos.dto.CategoryDto;
import com.ust.pos.category.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/category")
public class CategoryController {

    private static final String REDIRECT = "redirect:/category/list";

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/list")
    public String list(Model model) {
        model.addAttribute("categories", categoryService.findAll());
        return "category/list";
    }

    @GetMapping("/add")
    public String add(Model model) {
        model.addAttribute("categoryDto", new CategoryDto());
        model.addAttribute("categories", categoryService.findSuperCategories());
        return "category/add";
    }

    @PostMapping("/add")
    public String save(@ModelAttribute CategoryDto categoryDto, Model model) {

        CategoryDto response = categoryService.save(categoryDto);

        if (!response.isSuccess()) {
            model.addAttribute("message", response.getMessage());
            model.addAttribute("categoryDto", categoryDto);
            model.addAttribute("categories", categoryService.findSuperCategories());
            return "category/add";
        }

        return REDIRECT;
    }

    @GetMapping("/get")
    public String edit(@RequestParam String identifier, Model model) {

        CategoryDto response = categoryService.findByIdentifier(identifier);

        if (!response.isSuccess()) {
            model.addAttribute("message", response.getMessage());
            model.addAttribute("categories", categoryService.findSuperCategories());
            return "category/list";
        }

        model.addAttribute("categoryDto", response);
        model.addAttribute("categories", categoryService.findSuperCategories());
        return "category/category";
    }

    @PostMapping("/update")
    public String update(@ModelAttribute CategoryDto categoryDto, Model model) {

        CategoryDto response = categoryService.update(categoryDto);

        if (!response.isSuccess()) {
            model.addAttribute("message", response.getMessage());
            model.addAttribute("categoryDto", categoryDto);
            model.addAttribute("categories", categoryService.findSuperCategories());
            return "category/category";
        }

        return REDIRECT;
    }

    @GetMapping("/delete")
    public String delete(@RequestParam String identifier) {
        categoryService.delete(identifier);
        return REDIRECT;
    }
}