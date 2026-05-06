package com.ust.pos.api.priceApi;

import com.ust.pos.dto.NodeDto;
import com.ust.pos.dto.PriceDto;
import com.ust.pos.price.service.PriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/price")
public class PriceApiController {
    @Autowired
    private PriceService priceService;

    @GetMapping("/list")
    public List<PriceDto> home(Model model) {
        return priceService.findAll();
    }
    @PostMapping("/add")
    public PriceDto addPost(@RequestBody PriceDto priceDto) {
        return priceService.save(priceDto);
    }

    @GetMapping("/get")
    public PriceDto update(@RequestParam String identifier) {
        return priceService.findByIdentifier(identifier);
    }

    @PostMapping("/update")
    public PriceDto updatePost(@RequestBody PriceDto priceDto) {
        return priceService.update(priceDto);
    }

    @GetMapping("/delete")
    public boolean delete(Model model, @RequestParam String identifier) {
        try{ priceService.delete(identifier);}
        catch (Exception e){
            return false;
        }
        return true;
    }
    @PostMapping("/toggle-status")
    public PriceDto toggle(@RequestParam String identifier){

        return priceService.toggleStatus(identifier);
    }


    @GetMapping("/findByStatus")
    public List<PriceDto> findByStatus() {

        return priceService.findIfTrue();
    }
}
