package com.ust.pos.api.price;

import com.ust.pos.dto.PriceDto;
import com.ust.pos.dto.ProductDto;
import com.ust.pos.price.service.PriceService;
import com.ust.pos.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("priceApiController")
@RequestMapping("/api/price")
public class PriceController {

    @Autowired
    private PriceService priceService;

    @Autowired
    private ProductService productService;

    @GetMapping("/list")
    public List<PriceDto> list() {
        return priceService.getAllPrices();
    }

    @GetMapping("/get")
    public PriceDto get(@RequestParam Long id) {
        return priceService.getPriceById(id);
    }

    @PostMapping("/add")
    public PriceDto add(@RequestBody PriceDto priceDto) {
        return priceService.createPrice(priceDto);
    }

    @PostMapping("/update")
    public PriceDto update(@RequestBody PriceDto priceDto) {
        return priceService.updatePrice(priceDto);
    }

    @GetMapping("/delete")
    public boolean delete(@RequestParam Long id) {
        try {
            priceService.deletePrice(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @GetMapping("/products")
    public List<ProductDto> products() {
        return productService.findAll();
    }
}