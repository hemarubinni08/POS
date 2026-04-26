package com.ust.pos.brand;

import com.ust.pos.dto.BrandDto;
import com.ust.pos.brand.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class BrandController {

    public static final String REDIRECT_ROLE_LIST = "redirect:/brand/list";
    @Autowired
    private BrandService brandService;

    @GetMapping("/list")
    public String list(Model model) {
        model.addAttribute("brands", brandService.findAll());
        return "brand/list";
    }

    @GetMapping("/add")
    public String addPage(Model model) {
        model.addAttribute("brandDto", new BrandDto());
        return "brand/add";
    }

    @PostMapping("/add")
    public String addPost(Model model,
                          @ModelAttribute("brandDto") BrandDto brandDto) {

        BrandDto response = brandService.save(brandDto);

        if (!response.isSuccess()) {
            model.addAttribute("message", response.getMessage());
            return "brand/add";
        }

        return REDIRECT_ROLE_LIST;
    }


    @GetMapping("/get")
    public String updatePage(Model model,
                             @RequestParam String identifier) {

        BrandDto brandDto = brandService.findByIdentifier(identifier);
        model.addAttribute("brandDto", brandDto);
        return "brand/brand";
    }

    @PostMapping("/update")
    public String updatePost(Model model,
                             @ModelAttribute("brandDto") BrandDto brandDto) {

        BrandDto response = brandService.update(brandDto);

        if (!response.isSuccess()) {
            model.addAttribute("message", response.getMessage());
            return "brand/brand";
        }

        return REDIRECT_ROLE_LIST;
    }

    @GetMapping("/delete")
    public String delete(@RequestParam String identifier) {
        brandService.delete(identifier);
        return REDIRECT_ROLE_LIST;
    }
}
