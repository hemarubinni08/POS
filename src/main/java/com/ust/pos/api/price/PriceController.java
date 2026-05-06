package com.ust.pos.api.price;

import com.ust.pos.dto.PriceDto;
import com.ust.pos.dto.ProductDto;
import com.ust.pos.price.service.PriceService;
import com.ust.pos.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("priceApiController")
@RequestMapping("/api/prices")
public class PriceController {

    @Autowired
    private PriceService priceService;

    @Autowired
    private ProductService productService;

    // GET ALL PRICES
    @GetMapping
    public List<PriceDto> getAll() {
        return priceService.getAllPrices();
    }

    // GET PRICE BY ID
    @GetMapping("/{id}")
    public PriceDto getById(@PathVariable Long id) {
        return priceService.getPriceById(id);
    }

    // CREATE PRICE
    @PostMapping
    public PriceDto create(@RequestBody PriceDto priceDto) {
        return priceService.createPrice(priceDto);
    }

    // UPDATE PRICE
    @PutMapping("/{id}")
    public PriceDto update(@PathVariable Long id,
                           @RequestBody PriceDto priceDto) {
        priceDto.setId(id);
        return priceService.updatePrice(priceDto);
    }

    // DELETE PRICE
    @DeleteMapping("/{id}")
    public boolean delete(@PathVariable Long id) {
        try {
            priceService.deletePrice(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // GET ALL PRODUCTS (for dropdown / reference)
    @GetMapping("/products")
    public List<ProductDto> getAllProducts() {
        return productService.findAll();
    }
}