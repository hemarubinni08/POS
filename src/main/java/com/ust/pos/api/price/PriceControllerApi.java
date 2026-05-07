package com.ust.pos.api.price;

import com.ust.pos.dto.PriceDto;
import com.ust.pos.price.service.PriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/price")
public class PriceControllerApi {

    public static final String REDIRECT_ROLE_LIST = "redirect:/price/list";
    @Autowired
    private PriceService priceService;

    @GetMapping("/list")
    public List<PriceDto> list() {
        return priceService.findAll();
    }

    @PostMapping("/add")
    public PriceDto addPost(@RequestBody PriceDto priceDto) {
        return priceService.save(priceDto);
    }

    @GetMapping("/get")
    public PriceDto updatePage(@RequestParam String identifier) {
        return priceService.findByIdentifier(identifier);
    }

    @PostMapping("/update")
    public PriceDto updatePost(@RequestBody PriceDto priceDto) {
        return priceService.update(priceDto);
    }

    @GetMapping("/delete")
    public Boolean delete(@RequestParam String identifier) {

        try {
            priceService.delete(identifier);
        } catch (Exception e) {
            return false;
        }
        return true;
    }
}