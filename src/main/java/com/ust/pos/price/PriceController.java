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

    public static final String REDIRECT_PRICE_LIST = "redirect:/price/list";

    @Autowired
    private PriceService priceService;
    @Autowired
    private ProductService productService;

    @GetMapping("/list")
    public String home(Model model) {
        model.addAttribute("prices", priceService.findAll());
        return "price/list";
    }

    @GetMapping("/add")
    public String add(Model model, @ModelAttribute PriceDto priceDto) {

        // ✅ THIS LINE IS THE KEY
        model.addAttribute("products", productService.findAll());

        return "price/add";
    }

    @PostMapping("/add")
    public String addPost(Model model, @ModelAttribute PriceDto priceDto) {

        PriceDto response = priceService.save(priceDto);

        if (!response.isSuccess()) {
            model.addAttribute("message", response.getMessage());


            model.addAttribute("products", productService.findAll());

            return "price/add";
        }

        return "redirect:/price/list";
    }


    @GetMapping("/get")
    public String update(Model model, @RequestParam String identifier) {

        PriceDto response = priceService.findByIdentifier(identifier);
        model.addAttribute("price", response);

        return "price/price";
    }

    @PostMapping("/update")
    public String updatePost(Model model, @ModelAttribute PriceDto priceDto) {

        PriceDto response = priceService.update(priceDto);

        if (!response.isSuccess()) {
            model.addAttribute("message", response.getMessage());
            model.addAttribute(
                    "price",
                    priceService.findByIdentifier(priceDto.getIdentifier())
            );
            return "price/price";
        }

        return REDIRECT_PRICE_LIST;
    }

    @GetMapping("/delete")
    public String delete(@RequestParam String identifier) {

        priceService.delete(identifier);
        return REDIRECT_PRICE_LIST;
    }
}