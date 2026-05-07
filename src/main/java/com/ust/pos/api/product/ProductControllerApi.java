package com.ust.pos.api.product;

import com.ust.pos.dto.ProductDto;
import com.ust.pos.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product")
public class ProductControllerApi {

    @Autowired
    private ProductService productService;

    @GetMapping("/list")
    public List<ProductDto> home() {
        return productService.findAll();
    }

    @PostMapping("/add")
    public ProductDto addPost(@RequestBody ProductDto productDto) {
        return productService.save(productDto);
    }

    @GetMapping("/get")
    public ProductDto update(@RequestParam String identifier) {
        return productService.findByIdentifier(identifier);
    }

    @PostMapping("/update")
    public ProductDto updatePost(@RequestBody ProductDto productDto) {
        return productService.update(productDto);
    }

    @GetMapping("/delete")
    public boolean delete(@RequestParam String identifier) {
        try {
            productService.delete(identifier);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    @GetMapping("/active")
    public List<ProductDto> findAllActive() {
        return productService.findAllActive();
    }

    @PostMapping("/changestatus")
    public ProductDto changeStatus(@RequestBody ProductDto productDto) {
        return productService.updateStatus(productDto.getIdentifier(), productDto.isStatus());
    }
}
