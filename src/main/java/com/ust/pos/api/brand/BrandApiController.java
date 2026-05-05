package com.ust.pos.api.brand;


import com.ust.pos.brand.service.BrandService;
import com.ust.pos.dto.BrandDto;
import com.ust.pos.product.service.ProductService;
import com.ust.pos.warehouse.service.WarehouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/brand")
public class BrandApiController {
    @Autowired
    BrandService brandService;
    @Autowired
    ProductService productService;
    @Autowired
    WarehouseService warehouseService;

    @GetMapping("/list")
    public List<BrandDto> list() {
        return brandService.findAll();
    }


    @PostMapping("/add")
    public BrandDto addData(@RequestBody BrandDto brandDto) {
        return brandService.save(brandDto);
    }

    @GetMapping("/get")
    public BrandDto update(@RequestParam String identifier) {
        return brandService.findByIdentifier(identifier);
    }

    @PostMapping("/update")
    public BrandDto updatePost(@RequestBody BrandDto brandDto) {
        return brandService.update(brandDto);
    }

    @GetMapping("/delete")
    public boolean delete(@RequestParam String identifier) {
        try {
            brandService.delete(identifier);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    @PostMapping("/toggleStatus")
    public boolean toggleStatus(@RequestParam String identifier) {
        try {
            brandService.toggleStatus(identifier);
        } catch (Exception e) {
            return false;
        }
        return true;
    }
}
