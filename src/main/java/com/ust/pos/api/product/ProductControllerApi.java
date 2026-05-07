package com.ust.pos.api.product;

import com.ust.pos.dto.ProductDto;
import com.ust.pos.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product")
public class ProductControllerApi {

    public static final String REDIRECT_ROLE_LIST = "redirect:/product/list";
    @Autowired
    private ProductService productService;

    @GetMapping("/list")
    public List<ProductDto> list() {
        return productService.findAll();
    }

    @PostMapping("/add")
    public ProductDto addPost(@RequestBody ProductDto productDto) {
        return productService.save(productDto);
    }

    @GetMapping("/get")
    public ProductDto updatePage(@RequestParam String identifier) {
        return productService.findByIdentifier(identifier);
    }

    @PostMapping("/update")
    public ProductDto updatePost(@RequestBody ProductDto productDto) {
        return productService.update(productDto);
    }

    @GetMapping("/delete")
    public Boolean delete(@RequestParam String identifier) {

        try {
            productService.delete(identifier);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    @PostMapping("/toggle")
    public ProductDto toggleStatus(@RequestBody ProductDto productDto) {
        return productService.toggleStatus(productDto.getIdentifier());
    }

    @GetMapping("/active")
    public List<ProductDto> getActiveProducts() {
        return productService.findActiveProducts();
    }
}