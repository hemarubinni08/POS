package com.ust.pos.api.product;

import com.ust.pos.category.service.CategoryService;
import com.ust.pos.dto.CategoryDto;
import com.ust.pos.dto.ProductDto;
import com.ust.pos.product.service.ProductService;
import com.ust.pos.stock.service.StockService;
import com.ust.pos.warehouse.service.WarehouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/product")
public class ProductApiController {
    @Autowired
    private ProductService productService;

    @Autowired
    private StockService stockService;

    @Autowired
    private WarehouseService warehouseService;

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/list")
    public List<ProductDto> home() {
        return productService.findAll();
    }

    @GetMapping("/add")
    public List<CategoryDto> add(@RequestBody ProductDto productDto) {
        return categoryService.findAllWithoutNull();
    }

    @PostMapping("/add")
    public ProductDto addPost(@RequestBody ProductDto productDto) {
        return productService.save(productDto);
    }


    @GetMapping("/get")
    public Map<String, Object> update(@RequestParam String identifier) {
        ProductDto productDto = productService.findByIdentifier(identifier);
        Map<String, Object> response = new HashMap<>();
        response.put("product", productDto);
        response.put("warehouse", warehouseService.findAll());
        response.put("categories", categoryService.findAllWithoutNull());
        return response;
    }


    @PostMapping("/update")
    public ProductDto doupdate(@RequestBody ProductDto productDto) {
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
}