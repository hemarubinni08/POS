package com.ust.pos.warehouse;

import com.ust.pos.dto.WarehouseDto;
import com.ust.pos.warehouse.service.WarehouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/warehouse")
public class WarehouseController {
    public static final String REDIRECT_WAREHOUSE_LIST = "redirect:/warehouse/list";
    @Autowired
    WarehouseService warehouseService;

    @GetMapping("/list")
    public String home(Model model, @ModelAttribute WarehouseDto warehouseDto) {
        model.addAttribute("warehouses", warehouseService.findAll());
        return "warehouse/list";
    }

    @GetMapping("/add")
    public String addWarehouse(@ModelAttribute WarehouseDto warehouseDto, Model model) {
        List<String> commonUnits = Arrays.asList("pcs", "kg", "gram", "box", "ltr", "unit", "pack");
        model.addAttribute("unitList", commonUnits);
        return "warehouse/add";
    }

    @PostMapping("/add")
    public String doAddWarehouse(RedirectAttributes ra, Model model, @ModelAttribute WarehouseDto warehouseDto) {
        model.addAttribute("warehouses", warehouseDto);
        warehouseService.save(warehouseDto);
        ra.addFlashAttribute("message", warehouseDto.getMessage());
        ra.addFlashAttribute("success", warehouseDto.isSuccess());
        return REDIRECT_WAREHOUSE_LIST;
    }

    @GetMapping("/delete")
    public String deleteWarehouse(@RequestParam String identifier) {
        warehouseService.delete(identifier);
        return REDIRECT_WAREHOUSE_LIST;
    }

    @GetMapping("/get")
    public String update(@RequestParam String identifier, Model model, @ModelAttribute WarehouseDto warehouseDto) {
        WarehouseDto warehouseDto1 = warehouseService.findByIdentifier(identifier);
        model.addAttribute("warehouseDto", warehouseDto1);
        return "warehouse/warehouse";
    }

    @PostMapping("/update")
    public String updatePost(@ModelAttribute WarehouseDto warehouseDto) {
        warehouseService.update(warehouseDto);
        return REDIRECT_WAREHOUSE_LIST;
    }

    @PostMapping("/toggle")
    @ResponseBody
    public String toggle(@RequestParam String identifier, boolean status) {
        warehouseService.updateStatus(identifier, status);
        return "success";
    }
}
