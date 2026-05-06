package com.ust.pos.api.brand;

import com.ust.pos.brand.service.BrandService;
import com.ust.pos.dto.BrandDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("brandApiController")
@RequestMapping("/api/brands")
public class BrandController {

    @Autowired
    private BrandService brandService;

    // GET all brands
    @GetMapping
    public List<BrandDto> getAll() {
        return brandService.findAll();
    }

    // GET by identifier
    @GetMapping("/{identifier}")
    public BrandDto getByIdentifier(@PathVariable String identifier) {
        return brandService.findByIdentifier(identifier);
    }

    // CREATE
    @PostMapping
    public BrandDto create(@RequestBody BrandDto brandDto) {
        return brandService.save(brandDto);
    }

    // UPDATE
    @PutMapping("/{identifier}")
    public BrandDto update(@PathVariable String identifier,
                           @RequestBody BrandDto brandDto) {
        brandDto.setIdentifier(identifier);
        return brandService.update(brandDto);
    }

    // DELETE
    @DeleteMapping("/{identifier}")
    public boolean delete(@PathVariable String identifier) {
        try {
            brandService.delete(identifier);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // TOGGLE STATUS
    @PatchMapping("/{identifier}/toggle")
    public BrandDto toggleStatus(@PathVariable String identifier) {
        return brandService.toggleStatus(identifier);
    }

    // GET ACTIVE BRANDS
    @GetMapping("/active")
    public List<BrandDto> getActiveBrands() {
        return brandService.findIfTrue();
    }
}