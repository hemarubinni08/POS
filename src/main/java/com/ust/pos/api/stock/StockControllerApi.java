package com.ust.pos.api.stock;

import com.ust.pos.dto.StockDto;
import com.ust.pos.stock.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/stock")
public class StockControllerApi {

    @Autowired
    private StockService stockService;

    // LIST
    @GetMapping("/list")
    public List<StockDto> list() {
        return stockService.findAll();
    }

    // ADD
    @PostMapping("/add")
    public StockDto add(@RequestBody StockDto stockDto) {
        return stockService.save(stockDto);
    }

    // GET ONE
    @GetMapping("/get")
    public StockDto get(@RequestParam String identifier) {
        return stockService.findByIdentifier(identifier);
    }

    // UPDATE
    @PostMapping("/update")
    public StockDto update(@RequestBody StockDto stockDto) {
        return stockService.update(stockDto);
    }

    // DELETE
    @GetMapping("/delete")
    public Boolean delete(@RequestParam String identifier) {
        try {
            stockService.delete(identifier);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // TOGGLE STATUS
    @GetMapping("/toggle")
    public StockDto toggle(@RequestParam String identifier) {
        return stockService.toggleStatus(identifier);
    }
}