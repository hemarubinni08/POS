package com.ust.pos.price;

import com.ust.pos.dto.PriceDto;
import com.ust.pos.product.service.ProductService;
import com.ust.pos.price.service.PriceService;
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
    public String list(Model model) {
        model.addAttribute("prices", priceService.getAllPrices());
        return "price/list";
    }


    @GetMapping("/add")
    public String add(Model model) {
        model.addAttribute("priceDto", new PriceDto());
        model.addAttribute("products", productService.getAllProducts());
        return "price/add";
    }

    @PostMapping("/add")
    public String addPost(
            @ModelAttribute PriceDto priceDto,
            Model model
    ) {
        PriceDto response = priceService.createPrice(priceDto);

        if (!response.isSuccess()) {
            model.addAttribute("products", productService.getAllProducts());
            model.addAttribute("message", response.getMessage());
            model.addAttribute("messageType", "error");
            return "price/add";
        }

        return REDIRECT_PRICE_LIST;
    }


    @GetMapping("/get")
    public String update(
            @RequestParam Long id,
            Model model
    ) {
        PriceDto priceDto = new PriceDto();
        priceDto.setId(id);

        model.addAttribute("products", productService.getAllProducts());
        model.addAttribute("price", priceDto);

        return "price/price";
    }

    @PostMapping("/update")
    public String updatePost(
            @ModelAttribute PriceDto priceDto,
            Model model
    ) {
        PriceDto response = priceService.updatePrice(priceDto);

        if (!response.isSuccess()) {
            model.addAttribute("message", response.getMessage());
            model.addAttribute("messageType", "error");
        }

        return REDIRECT_PRICE_LIST;
    }

    @GetMapping("/delete")
    public String delete(@RequestParam Long id) {
        priceService.deletePrice(id);
        return REDIRECT_PRICE_LIST;
    }
}
