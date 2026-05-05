package com.ust.pos.brand;


import com.ust.pos.brand.service.BrandService;
import com.ust.pos.dto.BrandDto;
import com.ust.pos.product.service.ProductService;
import com.ust.pos.warehouse.service.WarehouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/brand")
public class BrandController {
    @Autowired
    BrandService brandService;
    @Autowired
    ProductService productService;
    @Autowired
    WarehouseService warehouseService;

    @GetMapping("/list")
    public String list(Model model) {
        model.addAttribute("brands", brandService.findAll());

        return "brand/list";
    }

    @GetMapping("/add")
    public String add(Model model, @ModelAttribute BrandDto brandDto) {
        model.addAttribute("products", productService.findAll());
        model.addAttribute("warehouses", warehouseService.findAll());
        return "brand/add";
    }

    @PostMapping("/add")
    public String addData(Model model, @ModelAttribute BrandDto brandDto) {
        BrandDto response = brandService.save(brandDto);
        if (!response.isSuccess()) {
            model.addAttribute("message", response.getMessage());
            return "brand/add";
        }
        return "redirect:/brand/list";
    }

    @GetMapping("/get")
    public String update(Model model, @RequestParam String identifier) {
        BrandDto response = brandService.findByIdentifier(identifier);
        model.addAttribute("brandDto", response);
        return "brand/brand";
    }

    @PostMapping("/update")
    public String updatePost(Model model, @ModelAttribute BrandDto brandDto) {
        BrandDto response = brandService.update(brandDto);
        if (!response.isSuccess()) {
            model.addAttribute("message", response.getMessage());
            return "/brand/brand";
        }
        return "redirect:/brand/list";
    }

    @GetMapping("/delete")
    public String delete(Model model, @RequestParam String identifier) {
        brandService.delete(identifier);
        return "redirect:/brand/list";
    }
}
