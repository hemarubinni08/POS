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

    @GetMapping("/list")
    public List<ProductDto> list() {
        return productService.getAllProducts();
    }

    @GetMapping("/get")
    public ProductDto get(@RequestParam Long id) {
        return productService.getProduct(id);
    }

    @PostMapping("/add")
    public ProductDto add(@RequestBody ProductDto productDto) {
        return productService.createProduct(productDto);
    }

    @PostMapping("/update")
    public ProductDto update(@RequestBody ProductDto productDto) {
        return productService.updateProduct(productDto);
    }

    @GetMapping("/delete")
    public boolean delete(@RequestParam Long id) {
        try {
            productService.deleteProduct(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @GetMapping("/prices")
    public List<PriceDto> prices() {
        return priceService.getAllPrices();
    }

    @GetMapping("/categories")
    public List<CategoryDto> categories() {
        return categoryService.getAllCategories();
    }
}