package com.ust.pos.stock;

import com.ust.pos.dto.StockDto;
import com.ust.pos.model.ProductRepository;
import com.ust.pos.model.WarehouseRepository;
import com.ust.pos.stock.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/stock")
public class StockController {

    private static final String REDIRECT = "redirect:/stock/list";

    @Autowired
    private StockService stockService;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private WarehouseRepository warehouseRepository;

    // LIST
    @GetMapping("/list")
    public String list(Model model) {
        model.addAttribute("stocks", stockService.findAll());
        return "stock/list";
    }

    // ADD PAGE
    @GetMapping("/add")
    public String add(Model model) {

        model.addAttribute("stockDto", new StockDto());

        model.addAttribute("products", productRepository.findAll());
        model.addAttribute("warehouses", warehouseRepository.findAll());

        return "stock/add";
    }

    // SAVE
    @PostMapping("/add")
    public String save(@ModelAttribute StockDto stockDto, Model model) {
        stockDto.setIdentifier(stockDto.getProductIdentifier()+"_"+stockDto.getWarehouseIdentifier());
        StockDto response = stockService.save(stockDto);

        if (!response.isSuccess()) {
            model.addAttribute("message", response.getMessage());
            model.addAttribute("stockDto", stockDto);

            model.addAttribute("products", productRepository.findAll());
            model.addAttribute("warehouses", warehouseRepository.findAll());

            return "stock/list";
        }

        return REDIRECT;
    }

    // EDIT PAGE
    @GetMapping("/get")
    public String edit(@RequestParam String identifier, Model model) {

        StockDto dto = stockService.findByIdentifier(identifier);

        if (!dto.isSuccess()) {
            model.addAttribute("message", dto.getMessage());
            return REDIRECT;
        }

        model.addAttribute("stockDto", dto);

        model.addAttribute("products", productRepository.findAll());
        model.addAttribute("warehouses", warehouseRepository.findAll());

        return "stock/stock";
    }

    // UPDATE
    @PostMapping("/update")
    public String update(@ModelAttribute StockDto stockDto, Model model) {

        StockDto response = stockService.update(stockDto);

        if (!response.isSuccess()) {
            model.addAttribute("message", response.getMessage());
            model.addAttribute("stockDto", stockDto);

            model.addAttribute("products", productRepository.findAll());
            model.addAttribute("warehouses", warehouseRepository.findAll());

            return "stock/stock";
        }

        return REDIRECT;
    }

    // DELETE
    @GetMapping("/delete")
    public String delete(@RequestParam String identifier) {
        stockService.delete(identifier);
        return REDIRECT;
    }
}