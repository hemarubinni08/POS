package com.ust.pos.api.productApi;

import com.ust.pos.category.service.CategoryService;
import com.ust.pos.dto.NodeDto;
import com.ust.pos.dto.ProductDto;
import com.ust.pos.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product")
public class ProductApiController {
    @Autowired
    private ProductService productService;
    @Autowired
    private CategoryService categoryService;

    @GetMapping("/list")
    public List<ProductDto> home(Model model) {
        return productService.findAll();
    }

    @PostMapping("/add")
    public ProductDto addPost(@RequestBody ProductDto productDto) {
        return productService.save(productDto);
    }

    @GetMapping("/get/{identifier}")
    public ProductDto update(@PathVariable String identifier) {
        return productService.findByIdentifier(identifier);
    }

    @PostMapping("/update")
    public ProductDto updatePost( @RequestBody ProductDto productDto) {
        return productService.update(productDto);
    }

    @GetMapping("/delete/{identifier}")
    public boolean delete( @PathVariable String identifier) {
        try{  productService.delete(identifier);}
        catch(Exception e){
            return false;
        }
        return true;
    }
    @PostMapping("/toggle-status")
    public ProductDto toggle(@RequestParam String identifier){

        return productService.toggleStatus(identifier);
    }


    @GetMapping("/findByStatus")
    public List<ProductDto> findByStatus() {

        return productService.findIfTrue();
    }
}