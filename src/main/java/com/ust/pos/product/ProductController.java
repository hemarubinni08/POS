package com.ust.pos.product;

import com.ust.pos.dto.ProductDto;
import com.ust.pos.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/product")
@Controller
public class ProductController {
    public static final String REDIRECT_ROLE_LIST = "redirect:/product/list";
    public static final String ROLE_ADD = "product/add";
    @Autowired
    private ProductService productService;

    @GetMapping("/list")
    public String home(Model model, Pageable pageable) {
        model.addAttribute("products", productService.findAll(pageable));
        return "product/list";
    }

    @GetMapping("/add")
    public String add(Model model, @ModelAttribute ProductDto userDto) {
        return ROLE_ADD;
    }

    @PostMapping("/add")
    public String addPost(Model model, @ModelAttribute ProductDto userDto) {
        ProductDto response = productService.save(userDto);
        if (!response.isSuccess()) {
            model.addAttribute("message", response.getMessage());
            return ROLE_ADD;
        }
        return REDIRECT_ROLE_LIST;
    }

    @GetMapping("/get")
    public String update(Model model, @RequestParam String identifier) {
        ProductDto response = productService.findByIdentifier(identifier);
        model.addAttribute("productDto", response);
        return "product/product";
    }

    @PostMapping("/update")
    public String updatePost(Model model, @ModelAttribute ProductDto userDto) {
        ProductDto response = productService.update(userDto);
        if (!response.isSuccess()) {
            model.addAttribute("message", response.getMessage());
            return ROLE_ADD;
        }
        return REDIRECT_ROLE_LIST;
    }

    @GetMapping("/delete")
    public String delete(Model model, @RequestParam String identifier) {
        productService.delete(identifier);
        return REDIRECT_ROLE_LIST;
    }
}
