package com.ust.pos.api.productApi;

import com.ust.pos.dto.ProductDto;
import com.ust.pos.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product")
public class ProductApiController {

    @Autowired
    private ProductService productService;

    @GetMapping("/list")
    public List<ProductDto> list() {

        return productService.findAll();
    }

    @PostMapping("/add")
    public ProductDto add(@RequestBody ProductDto productDto) {

        return productService.save(productDto);
    }


    @GetMapping("/get")
    public ProductDto get(@RequestParam String identifier) {

        return productService.findByIdentifier(identifier);
    }

    @PostMapping("/update")
    public ProductDto update(@RequestBody ProductDto productDto) {

        return productService.update(productDto);
    }


    @GetMapping("/delete")
    public boolean delete(@RequestParam String identifier) {
        try {
            productService.delete(identifier);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}