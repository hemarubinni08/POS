package com.ust.pos.stock;

import com.ust.pos.dto.StockDto;
import com.ust.pos.product.service.ProductService;
import com.ust.pos.rack.service.RackService;
import com.ust.pos.shelf.service.ShelfService;
import com.ust.pos.stock.service.StockService;
import com.ust.pos.warehouse.service.WarehouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/stock")
public class StockController {

    private static final String REDIRECT = "redirect:/stock/list";
    public static final String STOCK_DTO = "stockDto";
    public static final String MESSAGE = "message";

    @Autowired private StockService stockService;
    @Autowired private WarehouseService warehouseService;
    @Autowired private ProductService productService;


    @GetMapping("/list")
    public String list(Model model) {
        model.addAttribute("stocks", stockService.findAll());
        return "stock/list";
    }

    @GetMapping("/add")
    public String add(Model model) {
        model.addAttribute(STOCK_DTO, new StockDto());
        load(model);
        return "stock/add";
    }

    @PostMapping("/add")
    public String save(@ModelAttribute StockDto dto, Model model) {

        StockDto res = stockService.save(dto);

        if (!res.isSuccess()) {
            model.addAttribute(MESSAGE, res.getMessage());
            model.addAttribute(STOCK_DTO, dto);
            load(model);
            return "stock/add";
        }

        return REDIRECT;
    }

    @GetMapping("/get")
    public String edit(@RequestParam String identifier, Model model) {

        StockDto res = stockService.findByIdentifier(identifier);

        if (!res.isSuccess()) {
            model.addAttribute(MESSAGE, res.getMessage());
            return REDIRECT;
        }

        model.addAttribute(STOCK_DTO, res);
        load(model);
        return "stock/stock";
    }

    @PostMapping("/update")
    public String update(@ModelAttribute StockDto dto, Model model) {

        StockDto res = stockService.update(dto);

        if (!res.isSuccess()) {
            model.addAttribute(MESSAGE, res.getMessage());
            model.addAttribute(STOCK_DTO, dto);
            load(model);
            return "stock/stock";
        }

        return REDIRECT;
    }

    @GetMapping("/delete")
    public String delete(@RequestParam String identifier) {
        stockService.delete(identifier);
        return REDIRECT;
    }

    @GetMapping("/toggle")
    public String toggle(@RequestParam String identifier) {
        stockService.toggleStatus(identifier);
        return REDIRECT;
    }

    private void load(Model model) {
        model.addAttribute("warehouse", warehouseService.findAll());
        model.addAttribute("product", productService.findActiveProducts());
    }
}