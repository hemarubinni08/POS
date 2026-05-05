package com.ust.pos.api.stock;


import com.ust.pos.dto.StockDto;
import com.ust.pos.product.service.ProductService;
import com.ust.pos.stock.service.StockService;
import com.ust.pos.warehouse.service.WarehouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/stock")
public class StockApiController {
    @Autowired
    StockService stockService;
    @Autowired
    ProductService productService;
    @Autowired
    WarehouseService warehouseService;

    @GetMapping("/list")
    public List<StockDto> list() {
        return stockService.findAll();
    }

    @PostMapping("/add")
    public StockDto addData(@RequestBody StockDto stockDto) {
        return stockService.save(stockDto);
    }

    @GetMapping("/get")
    public StockDto update(@RequestParam String identifier) {
        return stockService.findByIdentifier(identifier);
    }

    @PostMapping("/update")
    public StockDto updatePost(@RequestBody StockDto stockDto) {
        return stockService.update(stockDto);

    }

    @GetMapping("/delete")
    public boolean delete(@RequestParam String identifier) {
        try {
            stockService.delete(identifier);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    @PostMapping("/toggleStatus")
    public boolean toggleStatus(@RequestParam String identifier) {
        try {
            stockService.toggleStatus(identifier);
        } catch (Exception e) {
            return false;
        }
        return true;
    }
}
