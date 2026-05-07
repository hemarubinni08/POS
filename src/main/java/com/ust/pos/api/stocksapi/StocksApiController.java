package com.ust.pos.api.stocksapi;

import com.ust.pos.dto.StocksDto;
import com.ust.pos.product.service.ProductService;
import com.ust.pos.stocks.service.StocksService;
import com.ust.pos.warehouse.service.WareHouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/stocks")
public class StocksApiController {
    @Autowired
    private StocksService stocksService;

    @Autowired
    private ProductService productService;

    @Autowired
    private WareHouseService wareHouseService;

    @GetMapping("/list")
    public List<StocksDto> home(Model model) {
        return stocksService.findAll();
    }

    @PostMapping("/add")
    public StocksDto addPost(@RequestBody StocksDto stocksDto) {
        return stocksService.save(stocksDto);
    }


    @GetMapping("/get")
    public StocksDto update(@RequestParam String identifier) {
        return stocksService.findByIdentifier(identifier);
    }

    @PostMapping("/update")
    public StocksDto updatePost( @RequestBody StocksDto stocksDto) {
        return stocksService.update(stocksDto);
    }

    @GetMapping("/delete")
    public boolean delete(@RequestParam String identifier) {
        try{stocksService.delete(identifier);}
        catch (Exception e){
            return false;
        }
        return true;
    }
    @PostMapping("/toggle-status")
    public StocksDto toggle(@RequestParam String identifier){

        return stocksService.toggleStatus(identifier);
    }


    @GetMapping("/findByStatus")
    public List<StocksDto> findByStatus() {

        return stocksService.findIfTrue();
    }
}