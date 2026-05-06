package com.ust.pos.stocks;

import com.ust.pos.dto.StocksDto;
import com.ust.pos.product.service.ProductService;
import com.ust.pos.warehouse.service.WareHouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.ust.pos.stocks.service.StocksService;


@Controller
@RequestMapping("/stocks")
public class    StocksController {
    public static final String REDIRECT_STOCKS_LIST = "redirect:/stocks/list";
    public static final String STOCKS = "stocks";
    public static final String PRODUCTS = "products";
    public static final String WAREHOUSE = "warehouse";
    @Autowired
    private StocksService stocksService;

    @Autowired
    private ProductService productService;

    @Autowired
    private WareHouseService warehouseService;

    @GetMapping("/list")
    public String home(Model model) {
        model.addAttribute(STOCKS, stocksService.findAll());
        return "stocks/list";
    }

    @GetMapping("/add")
    public String add(Model model, @ModelAttribute StocksDto stocksDto) {
        model.addAttribute(PRODUCTS,productService.findIfTrue());
        model.addAttribute(WAREHOUSE,warehouseService.findAll());
        return "stocks/add";
    }

    @PostMapping("/add")
    public String addPost(Model model, @ModelAttribute StocksDto stocksDto) {
        StocksDto response = stocksService.save(stocksDto);
        if (!response.isSuccess()) {
            model.addAttribute("message", response.getMessage());
            model.addAttribute(PRODUCTS,productService.findAll());
            model.addAttribute(WAREHOUSE,warehouseService.findAll());
            return "stocks/add";
        }
        return REDIRECT_STOCKS_LIST;
    }

    @GetMapping("/get")
    public String update(Model model, @RequestParam String identifier) {
        StocksDto response = stocksService.findByIdentifier(identifier);
        model.addAttribute(PRODUCTS,productService.findAll());
        model.addAttribute(WAREHOUSE,warehouseService.findAll());
        model.addAttribute(STOCKS, response);
        return "stocks/stocks";
    }

    @PostMapping("/update")
    public String updatePost(Model model, @ModelAttribute StocksDto stocksDto) {
        StocksDto response = stocksService.update(stocksDto);
        if (!response.isSuccess()) {
            model.addAttribute(STOCKS, response);
            model.addAttribute("message", response.getMessage());
            model.addAttribute(PRODUCTS,productService.findAll());
            model.addAttribute(WAREHOUSE,warehouseService.findAll());
            return "stocks/stocks";
        }
        return REDIRECT_STOCKS_LIST;
    }

    @GetMapping("/delete")
    public String delete(Model model, @RequestParam String identifier) {
        stocksService.delete(identifier);
        return REDIRECT_STOCKS_LIST;
    }
    @PostMapping("/toggle-status")
    @ResponseBody
    public void toggle(Model model,@RequestParam String identifier){
        stocksService.toggleStatus(identifier);}
}