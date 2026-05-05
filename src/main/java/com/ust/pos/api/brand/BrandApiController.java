package com.ust.pos.api.brand;

import com.ust.pos.brand.service.BrandService;
import com.ust.pos.dto.BrandDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/brand")
public class BrandApiController {

    public static final String REDIRECT_BRAND_LIST = "redirect:/brand/list";

    @Autowired
    private BrandService brandService;

    @GetMapping("/list")
    public List<BrandDto> home() {
        return brandService.findAll();
    }

    @PostMapping("/add")
    public BrandDto addPost(@RequestBody BrandDto brandDto) {
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

    @PostMapping("/status")
    public boolean updateStatus(
            @RequestParam String identifier, Boolean status) {
        try {
            brandService.updateStatusOnly(identifier, status);
        } catch (Exception e) {
            return false;
        }
        return true;
    }


    @GetMapping("/delete")
    public boolean delete(Model model, @RequestParam String identifier) {
        try {
            brandService.delete(identifier);
        } catch (Exception e) {
            return false;
        }
        return true;
    }
}