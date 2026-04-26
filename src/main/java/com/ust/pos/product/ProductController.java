package com.ust.pos.product;

import com.ust.pos.category.service.CategoryService;
import com.ust.pos.dto.ProductDto;
import com.ust.pos.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/product")
public class ProductController {

    private static final String REDIRECT = "redirect:/product/list";

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/list")
    public String list(Model model) {
        model.addAttribute("products", productService.findAll());
        return "product/list";
    }

    @GetMapping("/add")
    public String add(Model model) {
        model.addAttribute("productDto", new ProductDto());
        // Correct attribute name
        model.addAttribute("categories", categoryService.findLeafCategories());

        return "product/add";
    }

    @PostMapping("/add")
    public String save(@ModelAttribute ProductDto productDto, Model model) {

        ProductDto response = productService.save(productDto);

        if (!response.isSuccess()) {
            model.addAttribute("message", response.getMessage());
            // Important: reload categories on error
            model.addAttribute("categories", categoryService.findLeafCategories());
            model.addAttribute("productDto", productDto);
            return "product/add";
        }

        return REDIRECT;
    }

    @GetMapping("/get")
    public String edit(@RequestParam String identifier, Model model) {

        ProductDto response = productService.findByIdentifier(identifier);

        if (!response.isSuccess()) {
            model.addAttribute("message", response.getMessage());
            model.addAttribute("products", productService.findAll());
            return "product/list";
        }
        model.addAttribute("productDto", response);
        // Needed for dropdown in update page
        model.addAttribute("categories", categoryService.findLeafCategories());

        return "product/product";
    }

    @PostMapping("/update")
    public String update(@ModelAttribute ProductDto productDto, Model model) {

        ProductDto response = productService.update(productDto);

        if (!response.isSuccess()) {
            model.addAttribute("message", response.getMessage());
            model.addAttribute("productDto", productDto);
            // reload categories
            model.addAttribute("categories", categoryService.findLeafCategories());
            return "product/product";
        }

        return REDIRECT;
    }

    @GetMapping("/delete")
    public String delete(@RequestParam String identifier) {
        productService.delete(identifier);
        return REDIRECT;
    }
}