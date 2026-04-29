package com.ust.pos.api.brandApi;

import com.ust.pos.brand.service.BrandService;
import com.ust.pos.dto.BrandDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/brand")
public class BrandApiController {

    @Autowired
    private BrandService brandService;

    @GetMapping("/list")
    public List<BrandDto> list() {
        return brandService.findAll();
    }

    @PostMapping("/add")
    public BrandDto add(@RequestBody BrandDto brandDto) {
        return brandService.save(brandDto);
    }

    @GetMapping("/get")
    public BrandDto get(@RequestParam String identifier) {
        return brandService.findByIdentifier(identifier);
    }

    @PostMapping("/update")
    public BrandDto update(@RequestBody BrandDto brandDto) {
        return brandService.update(brandDto);
    }

    @GetMapping("/delete")
    public boolean delete(@RequestParam String identifier) {
        try {
            brandService.delete(identifier);
            return true;
        } catch (Exception e) {
            return false;
        }
    }


}