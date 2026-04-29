package com.ust.pos.warehouse;

import com.ust.pos.dto.WarehouseDto;
import com.ust.pos.warehouse.service.WarehouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/warehouse")
public class WarehouseController {

    private static final String REDIRECT_LIST = "redirect:/warehouse/list";

    @Autowired
    private WarehouseService warehouseService;

    @GetMapping("/list")
    public String list(Model model) {
        model.addAttribute("warehouses", warehouseService.findAll());
        return "warehouse/list";
    }

    @GetMapping("/add")
    public String add(Model model) {
        model.addAttribute("warehouseDto", new WarehouseDto());
        return "warehouse/add";
    }

    @PostMapping("/add")
    public String addPost(@ModelAttribute WarehouseDto dto, Model model) {

        WarehouseDto result = warehouseService.save(dto);
        if (!result.isSuccess()) {
            model.addAttribute("message", result.getMessage());
            model.addAttribute("messageType", "error");
            return "warehouse/add";
        }
        return REDIRECT_LIST;
    }

    @GetMapping("/get")
    public String get(@RequestParam String identifier, Model model) {
        model.addAttribute("warehouseDto", warehouseService.findByIdentifier(identifier));
        return "warehouse/warehouse";
    }

    @PostMapping("/update")
    public String update(@ModelAttribute WarehouseDto dto, Model model) {

        WarehouseDto result = warehouseService.update(dto);
        if (!result.isSuccess()) {
            model.addAttribute("message", result.getMessage());
            model.addAttribute("messageType", "error");
            return "warehouse/warehouse";
        }

        return REDIRECT_LIST;
    }

    @GetMapping("/delete")
    public String delete(@RequestParam String identifier) {
        warehouseService.delete(identifier);
        return REDIRECT_LIST;
    }

    @GetMapping("/toggle-status")
    public String toggleStatus(@RequestParam String identifier) {
        warehouseService.toggleStatus(identifier);
        return REDIRECT_LIST;
    }
}