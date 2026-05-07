package com.ust.pos.api.brand;

import com.ust.pos.brand.service.BrandService;
import com.ust.pos.dto.BrandDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/brand")
public class BrandControllerApi {

    @Autowired
    private BrandService brandService;

    // GET ALL
    @GetMapping("/list")
    public List<BrandDto> list() {
        return brandService.findAll();
    }

    // ADD
    @PostMapping("/add")
    public BrandDto add(@RequestBody BrandDto brandDto) {
        return brandService.save(brandDto);
    }

    // GET BY ID
    @GetMapping("/get")
    public BrandDto get(@RequestParam String identifier) {
        return brandService.findByIdentifier(identifier);
    }

    // UPDATE
    @PostMapping("/update")
    public BrandDto update(@RequestBody BrandDto brandDto) {
        return brandService.update(brandDto);
    }

    // DELETE
    @DeleteMapping("/delete")
    public BrandDto delete(@RequestParam String identifier) {
        BrandDto response = new BrandDto();

        try {
            brandService.delete(identifier);
            response.setSuccess(true);
            response.setMessage("Brand deleted successfully");
        } catch (Exception e) {
            response.setSuccess(false);
            response.setMessage("Delete failed");
        }
        return response;
    }

    // TOGGLE STATUS (API STYLE)
    @PostMapping("/toggle-status")
    public BrandDto toggleStatus(@RequestBody BrandDto brandDto) {
        return brandService.toggleStatus(brandDto.getIdentifier());
    }

    // ACTIVE LIST
    @GetMapping("/active")
    public List<BrandDto> active() {
        return brandService.findActiveBrands();
    }
}