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
        model.addAttribute("warehouses", warehouseService.getAllWarehouses());
        return "warehouse/list";
    }


    @GetMapping("/add")
    public String add(Model model) {
        model.addAttribute("warehouseDto", new WarehouseDto());
        return "warehouse/add";
    }

    @PostMapping("/add")
    public String addPost(@ModelAttribute WarehouseDto warehouseDto, Model model) {
        try {
            warehouseService.createWarehouse(warehouseDto);
        } catch (RuntimeException ex) {
            model.addAttribute("message", ex.getMessage());
            model.addAttribute("messageType", "error");
            return "warehouse/add";
        }

        return REDIRECT_WAREHOUSE_LIST;
    }


    @GetMapping("/get")
    public String update(@RequestParam Long id, Model model) {
        WarehouseDto warehouse = warehouseService.getWarehouseById(id);
        model.addAttribute("warehouse", warehouse);
        return "warehouse/warehouse";
    }

    @PostMapping("/update")
    public String updatePost(@ModelAttribute WarehouseDto warehouseDto, Model model) {
        try {
            warehouseService.updateWarehouse(warehouseDto.getId(), warehouseDto);
        } catch (RuntimeException ex) {
            model.addAttribute("message", ex.getMessage());
            model.addAttribute("messageType", "error");
        }

        return REDIRECT_WAREHOUSE_LIST;
    }


    @GetMapping("/delete")
    public String delete(@RequestParam Long id) {
        warehouseService.deactivateWarehouse(id);
        return REDIRECT_WAREHOUSE_LIST;
    }
}