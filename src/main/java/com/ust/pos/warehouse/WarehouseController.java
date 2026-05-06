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

    public static final String REDIRECT_WAREHOUSE_LIST = "redirect:/warehouse/list";

    @Autowired
    private WarehouseService warehouseService;

    @GetMapping("/list")
    public String list(Model model) {
        model.addAttribute("warehouses", warehouseService.findAll());
        return "warehouse/list";
    }

    @GetMapping("/add")
    public String add(Model model, @ModelAttribute WarehouseDto warehouseDto) {
        model.addAttribute("warehouseDto", warehouseDto);
        return "warehouse/add";
    }

    @PostMapping("/add")
    public String addPost(Model model, @ModelAttribute WarehouseDto warehouseDto) {

        WarehouseDto response = warehouseService.save(warehouseDto);

        if (!response.isSuccess()) {
            model.addAttribute("warehouseDto", warehouseDto);
            model.addAttribute("message", response.getMessage());
            model.addAttribute("messageType", "error");
            return "warehouse/add";
        }

        return REDIRECT_WAREHOUSE_LIST;
    }

    @GetMapping("/get")
    public String update(Model model, @RequestParam String identifier) {

        WarehouseDto response = warehouseService.findByIdentifier(identifier);

        if (response == null) {
            model.addAttribute("message", "Warehouse not found");
            model.addAttribute("messageType", "error");
            return REDIRECT_WAREHOUSE_LIST;
        }

        model.addAttribute("warehouseDto", response);
        return "warehouse/edit";
    }

    @PostMapping("/update")
    public String updatePost(Model model, @ModelAttribute WarehouseDto warehouseDto) {

        WarehouseDto response = warehouseService.update(warehouseDto);

        if (!response.isSuccess()) {
            model.addAttribute("warehouseDto", warehouseDto);
            model.addAttribute("message", response.getMessage());
            model.addAttribute("messageType", "error");
            return "warehouse/edit";
        }

        return REDIRECT_WAREHOUSE_LIST;
    }

    @GetMapping("/delete")
    public String delete(@RequestParam String identifier) {
        warehouseService.delete(identifier);
        return REDIRECT_WAREHOUSE_LIST;
    }

    @PostMapping("/toggle-status")
    @ResponseBody
    public void toggleStatus(@RequestParam String identifier) {
        warehouseService.toggleStatus(identifier);
    }
}