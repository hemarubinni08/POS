package com.ust.pos.api.stockApi;

import com.ust.pos.dto.StockDto;
import com.ust.pos.stock.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/stock")
public class StockApiController {

    @Autowired
    private StockService stockService;

    @GetMapping("/list")
    public List<StockDto> list() {
        return stockService.findAll();
    }

    @PostMapping("/add")
    public StockDto add(@RequestBody StockDto stockDto) {

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
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}