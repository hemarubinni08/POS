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
    public static final String ADD_STOCK = "stock/add";
    @Autowired
    StockService stockService;

    @Autowired
    WarehouseService warehouseService;

    @Autowired
    ProductService productService;

    @PostMapping("/add")
    public StockDto addPost(@RequestBody StockDto stockDto) {
        return stockService.save(stockDto);
    }

    @GetMapping("/get")
    public StockDto get(@RequestParam String identifier) {
        return stockService.findByIdentifier(identifier);
    }

    @PostMapping("/update")
    public StockDto update(@RequestBody StockDto stockDto) {
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

    @GetMapping("/list")
    public List<StockDto> list() {
        return stockService.findAll();
    }

    @PostMapping("/status")
    public boolean updateStatus(
            @RequestParam String identifier, Boolean status) {
        try {
            stockService.updateStatusOnly(identifier, status);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

}
