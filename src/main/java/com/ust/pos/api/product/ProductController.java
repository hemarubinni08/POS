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
@RequestMapping("/api/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private PriceService priceService;

    @Autowired
    private CategoryService categoryService;

    // Get all products
    @GetMapping("/list")
    public List<ProductDto> list() {
        return productService.findAll();
    }

    // Get product by identifier
    @GetMapping("/get")
    public ProductDto get(@RequestParam String identifier) {
        return productService.findByIdentifier(identifier);
    }

    // Add a new product
    @PostMapping("/add")
    public ProductDto add(@RequestBody ProductDto productDto) {
        return productService.save(productDto);
    }

    // Update product
    @PostMapping("/update")
    public ProductDto update(@RequestBody ProductDto productDto) {
        return productService.update(productDto);
    }

    // Delete product by identifier
    @DeleteMapping("/delete")
    public boolean delete(@RequestParam String identifier) {
        return productService.delete(identifier);
    }

    // Toggle active/inactive status
    @PostMapping("/toggle-status")
    public ProductDto toggleStatus(@RequestParam String identifier) {
        return productService.toggleStatus(identifier);
    }

    // Get only active products
    @GetMapping("/active")
    public List<ProductDto> activeProducts() {
        return productService.findIfTrue();
    }

    // List all prices
    @GetMapping("/prices")
    public List<PriceDto> prices() {
        return priceService.getAllPrices();
    }

    // List all categories
    @GetMapping("/categories")
    public List<CategoryDto> categories() {
        return categoryService.findAll();
    }
}