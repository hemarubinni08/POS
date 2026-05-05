package com.ust.pos.category;

import com.ust.pos.category.service.CategoryService;
import com.ust.pos.dto.CategoryDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/category")
public class CategoryController {
    @Autowired
    CategoryService categoryService;

    @GetMapping("/list")
    public String home(Model model) {
        model.addAttribute("categories", categoryService.findAll());

        return "category/list";
    }

    @GetMapping("/add")
    public String add(Model model, @ModelAttribute CategoryDto categoryDto) {
        model.addAttribute("categories", categoryService.findAll());
        return "category/add";
    }

    @PostMapping("/add")
    public String addPost(Model model, @ModelAttribute CategoryDto categoryDto) {
        CategoryDto categoryDto1 = categoryService.save(categoryDto);
        if (!categoryDto1.isSuccess()) {
            model.addAttribute("message", categoryDto1.getMessage());
        }
        return "redirect:/category/list";
    }

    @GetMapping("/get")
    public String update(Model model, @RequestParam String identifier) {
        CategoryDto categoryDto = categoryService.findByIdentifier(identifier);
        model.addAttribute("categories", categoryDto);
        List<CategoryDto> cd = categoryService.findAll();
        model.addAttribute("category", cd.stream().filter(s -> !s.getIdentifier().equals(identifier)).toList());
        return "category/category";
    }

    @PostMapping("/update")
    public String get(Model model, @ModelAttribute CategoryDto categoryDto) {
        CategoryDto categoryDto1 = categoryService.update(categoryDto);
        if (!categoryDto1.isSuccess()) {
            model.addAttribute("message", categoryDto1.getMessage());
            return "category/category";
        }
        return "redirect:/category/list";
    }

    @GetMapping("/delete")
    public String delete(Model model, @RequestParam String identifier) {
        categoryService.delete(identifier);
        return "redirect:/category/list";
    }
}
