package com.ust.pos.product;

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

    @GetMapping("/list")
    public String list(Model model) {
        model.addAttribute("products", productService.findAll());
        return "product/list";
    }

    @GetMapping("/add")
    public String add(Model model) {
        model.addAttribute("productDto", new ProductDto());
        return "product/add";
    }

    @PostMapping("/add")
    public String save(@ModelAttribute ProductDto productDto, Model model) {

        ProductDto response = productService.save(productDto);

        if (!response.isSuccess()) {
            model.addAttribute("message", response.getMessage());
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
        return "product/product";
    }

    @PostMapping("/update")
    public String update(@ModelAttribute ProductDto productDto, Model model) {

        ProductDto response = productService.update(productDto);

        if (!response.isSuccess()) {
            model.addAttribute("message", response.getMessage());
            model.addAttribute("productDto", productDto);
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