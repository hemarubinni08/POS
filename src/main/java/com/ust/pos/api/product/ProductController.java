package com.ust.pos.api.product;

import com.ust.pos.dto.CategoryDto;
import com.ust.pos.dto.PriceDto;
import com.ust.pos.dto.ProductDto;
import com.ust.pos.product.service.ProductService;
import com.ust.pos.price.service.PriceService;
import com.ust.pos.category.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("productApiController")
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private PriceService priceService;

    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public List<ProductDto> getAll() {
        return productService.findAll();
    }

    @GetMapping("/{identifier}")
    public ProductDto getByIdentifier(@PathVariable String identifier) {
        return productService.findByIdentifier(identifier);
    }

    @PostMapping("/save")
    public ProductDto save(@RequestBody ProductDto productDto) {
        return productService.save(productDto);
    }

    @PostMapping("/update/{identifier}")
    public ProductDto update(@PathVariable String identifier,
                             @RequestBody ProductDto productDto) {
        productDto.setIdentifier(identifier);
        return productService.update(productDto);
    }

    @PostMapping("/delete/{identifier}")
    public boolean delete(@PathVariable String identifier) {
        try {
            return productService.delete(identifier);
        } catch (Exception e) {
            return false;
        }
    }

    @PostMapping("/toggle/{identifier}")
    public ProductDto toggleStatus(@PathVariable String identifier) {
        return productService.toggleStatus(identifier);
    }

    @GetMapping("/active")
    public List<ProductDto> activeProducts() {
        return productService.findIfTrue();
    }

    @GetMapping("/prices")
    public List<PriceDto> getPrices() {
        return priceService.getAllPrices();
    }

    @GetMapping("/categories")
    public List<CategoryDto> getCategories() {
        return categoryService.findAll();
    }
}