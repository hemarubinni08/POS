package com.ust.pos.api.priceApi;

import com.ust.pos.dto.PriceDto;
import com.ust.pos.price.service.PriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/price")
public class PriceApiController {

    @Autowired
    private PriceService priceService;

    @GetMapping("/list")
    public List<PriceDto> list() {
        return priceService.findAll();
    }

    @PostMapping("/add")
    public PriceDto add(@RequestBody PriceDto priceDto) {
        return priceService.save(priceDto);
    }

    @GetMapping("/get")
    public PriceDto get(@RequestParam String identifier) {
        return priceService.findByIdentifier(identifier);
    }

    @PostMapping("/update")
    public PriceDto update(@RequestBody PriceDto priceDto) {
        return priceService.update(priceDto);
    }

    @GetMapping("/delete")
    public boolean delete(@RequestParam String identifier) {
        try {
            priceService.delete(identifier);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}