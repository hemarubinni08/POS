package com.ust.pos.price;

import com.ust.pos.dto.PriceDto;
import com.ust.pos.price.service.PriceService;
import com.ust.pos.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/price")
public class PriceController {

    private static final String REDIRECT = "redirect:/price/list";

    @Autowired
    private PriceService priceService;

    @Autowired
    private ProductService productService;

    @GetMapping("/list")
    public String list(Model model) {
        model.addAttribute("prices", priceService.findAll());
        return "price/list";
    }

    @GetMapping("/add")
    public String add(Model model) {
        model.addAttribute("priceDto", new PriceDto());
        model.addAttribute("products",productService.findAll());
        return "price/add";
    }

    @PostMapping("/add")
    public String save(@ModelAttribute PriceDto priceDto, Model model) {

        priceDto.setIdentifier(priceDto.getProductId());
        PriceDto response = priceService.save(priceDto);

        if (!response.isSuccess()) {
            model.addAttribute("message", response.getMessage());
            model.addAttribute("priceDto", priceDto);
            model.addAttribute("products",productService.findAll());
            return "price/add";
        }

        return REDIRECT;
    }

    @GetMapping("/get")
    public String edit(@RequestParam String identifier, Model model) {

        PriceDto response = priceService.findByIdentifier(identifier);

        if (!response.isSuccess()) {
            model.addAttribute("message", response.getMessage());
            model.addAttribute("prices", priceService.findAll());
            return "price/list";
        }

        model.addAttribute("priceDto", response);
        return "price/price";
    }

    @PostMapping("/update")
    public String update(@ModelAttribute PriceDto priceDto, Model model) {

        PriceDto response = priceService.update(priceDto);

        if (!response.isSuccess()) {
            model.addAttribute("message", response.getMessage());
            model.addAttribute("priceDto", priceDto);
            return "price/price";
        }

        return REDIRECT;
    }

    @GetMapping("/delete")
    public String delete(@RequestParam String identifier) {
        priceService.delete(identifier);
        return REDIRECT;
    }
}