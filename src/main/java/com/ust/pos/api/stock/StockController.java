package com.ust.pos.api.stock;

import com.ust.pos.dto.ProductDto;
import com.ust.pos.dto.StockDto;
import com.ust.pos.dto.WarehouseDto;
import com.ust.pos.product.service.ProductService;
import com.ust.pos.stock.service.StockService;
import com.ust.pos.warehouse.service.WarehouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("stockApiController")
@RequestMapping("/api/stocks")
public class StockController {

    @Autowired
    private StockService stockService;

    @Autowired
    private ProductService productService;

    @Autowired
    private WarehouseService warehouseService;

    /* ================= LIST ================= */

    @GetMapping("/list")
    public List<StockDto> list() {
        return stockService.getAllStocks();
    }

    @GetMapping("/get")
    public StockDto get(@RequestParam Long productId, @RequestParam Long warehouseId) {
        return stockService.getStock(productId, warehouseId);
    }

    @PostMapping("/add")
    public StockDto add(@RequestBody StockDto stockDto) {
        return stockService.createStock(stockDto);
    }

    @PostMapping("/update-quantity")
    public StockDto updateQuantity(@RequestParam Long stockId, @RequestParam Integer quantity) {
        return stockService.updateStockQuantity(stockId, quantity);
    }

    @GetMapping("/delete")
    public boolean delete(@RequestParam Long id) {
        try {
            stockService.deleteStock(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @GetMapping("/products")
    public List<ProductDto> products() {
        return productService.findAll();
    }

    @GetMapping("/warehouses")
    public List<WarehouseDto> warehouses() {
        return warehouseService.findAll();
    }
}