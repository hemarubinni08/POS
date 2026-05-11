package com.ust.pos.api.stock;

import com.ust.pos.api.BaseController;
import com.ust.pos.dto.PaginationDto;
import com.ust.pos.dto.ProductDto;
import com.ust.pos.dto.StockDto;
import com.ust.pos.dto.WarehouseDto;
import com.ust.pos.product.service.ProductService;
import com.ust.pos.stock.service.StockService;
import com.ust.pos.warehouse.service.WarehouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("stockApiController")
@RequestMapping("/api/stocks")
public class StockController extends BaseController {

    @Autowired
    private StockService stockService;

    @Autowired
    private ProductService productService;

    @Autowired
    private WarehouseService warehouseService;

    @PostMapping("/list")
    public ResponseEntity<List<StockDto>> list(@RequestBody PaginationDto paginationDto) {

        Pageable pageable = getPageable(paginationDto.getPage(), paginationDto.getSizePerPage(), paginationDto.getSortDirection(), paginationDto.getSortField());

        List<StockDto> stocks = stockService.findAll(pageable);

        return ResponseEntity.ok(stocks);
    }

    @GetMapping("/search")
    public ResponseEntity<?> get(@RequestParam Long productId, @RequestParam Long warehouseId) {

        StockDto response = stockService.getStock(productId, warehouseId);

        if (response == null || !response.isSuccess()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response != null ? response.getMessage() : "Stock not found");
        }

        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<?> save(@RequestBody StockDto stockDto) {

        StockDto response = stockService.createStock(stockDto);

        if (!response.isSuccess()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        return ResponseEntity.ok(response);
    }

    @PostMapping("/{stockId}/quantity")
    public ResponseEntity<?> updateQuantity(@PathVariable Long stockId, @RequestParam Integer quantity) {

        StockDto response = stockService.updateStockQuantity(stockId, quantity);

        if (!response.isSuccess()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        return ResponseEntity.ok(response);
    }

    @PostMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {

        try {

            boolean deleted = stockService.deleteStock(id);

            if (!deleted) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Stock not found");
            }

            return ResponseEntity.ok().build();

        } catch (Exception e) {

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/products")
    public ResponseEntity<List<ProductDto>> getProducts() {

        List<ProductDto> products = productService.findIfTrue();

        return ResponseEntity.ok(products);
    }

    @GetMapping("/warehouses")
    public ResponseEntity<List<WarehouseDto>> getWarehouses() {

        List<WarehouseDto> warehouses = warehouseService.findIfTrue();

        return ResponseEntity.ok(warehouses);
    }
}