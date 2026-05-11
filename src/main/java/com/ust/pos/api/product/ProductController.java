package com.ust.pos.api.product;

import com.ust.pos.api.BaseController;
import com.ust.pos.category.service.CategoryService;
import com.ust.pos.dto.CategoryDto;
import com.ust.pos.dto.PaginationDto;
import com.ust.pos.dto.PriceDto;
import com.ust.pos.dto.ProductDto;
import com.ust.pos.price.service.PriceService;
import com.ust.pos.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("productApiController")
@RequestMapping("/api/products")
public class ProductController extends BaseController {

    @Autowired
    private ProductService productService;

    @Autowired
    private PriceService priceService;

    @Autowired
    private CategoryService categoryService;

    @PostMapping("/list")
    public ResponseEntity<List<ProductDto>> list(@RequestBody PaginationDto paginationDto) {

        Pageable pageable = getPageable(paginationDto.getPage(), paginationDto.getSizePerPage(), paginationDto.getSortDirection(), paginationDto.getSortField());

        List<ProductDto> products = productService.findAll(pageable);

        return ResponseEntity.ok(products);
    }

    @GetMapping("/{identifier}")
    public ResponseEntity<?> getByIdentifier(@PathVariable String identifier) {

        ProductDto response = productService.findByIdentifier(identifier);

        if (response == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found");
        }

        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<?> save(@RequestBody ProductDto productDto) {

        ProductDto response = productService.save(productDto);

        if (!response.isSuccess()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        return ResponseEntity.ok(response);
    }

    @PostMapping("/{identifier}")
    public ResponseEntity<?> update(@PathVariable String identifier, @RequestBody ProductDto productDto) {

        productDto.setIdentifier(identifier);

        ProductDto response = productService.update(productDto);

        if (!response.isSuccess()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        return ResponseEntity.ok(response);
    }

    @PostMapping("/{identifier}")
    public ResponseEntity<?> delete(@PathVariable String identifier) {

        try {

            boolean deleted = productService.delete(identifier);

            if (!deleted) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Unable to delete product");
            }

            return ResponseEntity.ok().build();

        } catch (Exception e) {

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping("/{identifier}/toggle-status")
    public ResponseEntity<ProductDto> toggleStatus(@PathVariable String identifier) {

        ProductDto response = productService.toggleStatus(identifier);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/active")
    public ResponseEntity<List<ProductDto>> activeProducts() {

        List<ProductDto> activeProducts = productService.findIfTrue();

        return ResponseEntity.ok(activeProducts);
    }

    @GetMapping("/prices")
    public ResponseEntity<List<PriceDto>> getPrices() {

        List<PriceDto> prices = priceService.findAll(null);

        return ResponseEntity.ok(prices);
    }

    @GetMapping("/categories")
    public ResponseEntity<List<CategoryDto>> getCategories() {

        List<CategoryDto> categories = categoryService.findAll(null);

        return ResponseEntity.ok(categories);
    }
}