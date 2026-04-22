package com.ust.pos.stock;


import com.ust.pos.dto.RoleDto;
import com.ust.pos.dto.StockDto;
import com.ust.pos.stock.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/stock")
public class StockController {

    @Autowired
    private StockService stockService;

    @GetMapping("/list")
    public String home(Model model) {
        model.addAttribute("stocks", stockService.findAll());
        return "stock/list";
    }

    @GetMapping("/add")
    public String add(Model model, @ModelAttribute StockDto stockDto) {
        return "stock/add";
    }

    @PostMapping("/add")
    public String addPost(Model model, @ModelAttribute StockDto stockDto) {
        StockDto response = stockService.save(stockDto);
        if (!response.isSuccess()) {
            model.addAttribute("message", response.getMessage());
            model.addAttribute("stocks", stockService.findAll());
            return "stock/add";
        }
        return "redirect:/stock/list";
    }

    @GetMapping("/get")
    public String update(Model model, @RequestParam String identifier, @ModelAttribute StockDto stockDto) {
        StockDto response = stockService.findByIdentifier(identifier);
        model.addAttribute("stocks", stockService.findAll());
        model.addAttribute("stock", response);
        return "stock/stock";
    }

    @PostMapping("/update")
    public String updatePost(Model model, @ModelAttribute StockDto userDto) {
        StockDto response = stockService.update(userDto);
        if (!response.isSuccess()) {
            model.addAttribute("message", response.getMessage());
            model.addAttribute("stock", stockService.findAll());
        }
        return "redirect:/stock/list";
    }

    @GetMapping("/delete")
    public String delete(Model model, @RequestParam String identifier) {
        stockService.delete(identifier);
        return "redirect:/stock/list";
    }
}
