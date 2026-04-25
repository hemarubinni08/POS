package com.ust.pos.warehouse;

import com.ust.pos.dto.WarehouseDto;
import com.ust.pos.product.service.ProductService;
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
    @Autowired
    private ProductService productService;

    @GetMapping("/list")
    public String home(Model model) {
        model.addAttribute("warehouses", warehouseService.findAll());
        return "warehouse/list";
    }

    @GetMapping("/add")
    public String add(Model model, @ModelAttribute WarehouseDto warehouseDto) {
        model.addAttribute("products", productService.findAll());
        return "warehouse/add";
    }
    @PostMapping("/add")
    public String addPost(Model model, @ModelAttribute WarehouseDto warehouseDto) {

        WarehouseDto response = warehouseService.save(warehouseDto);

        if (!response.isSuccess()) {
            model.addAttribute("message", response.getMessage());


            model.addAttribute("products", productService.findAll());

            return "warehouse/add";
        }

        return REDIRECT_WAREHOUSE_LIST;
    }

    @GetMapping("/get")
    public String update(Model model, @RequestParam String identifier) {

        WarehouseDto response = warehouseService.findByIdentifier(identifier);
        model.addAttribute("warehouse", response);

        return "warehouse/warehouse";
    }

    @PostMapping("/update")
    public String updatePost(Model model, @ModelAttribute WarehouseDto warehouseDto) {

        WarehouseDto response = warehouseService.update(warehouseDto);

        if (!response.isSuccess()) {
            model.addAttribute("message", response.getMessage());
            model.addAttribute(
                    "warehouse",
                    warehouseService.findByIdentifier(warehouseDto.getIdentifier())
            );
            return "warehouse/warehouse";
        }

        return REDIRECT_WAREHOUSE_LIST;
    }

    @GetMapping("/delete")
    public String delete(@RequestParam String identifier) {

        warehouseService.delete(identifier);
        return REDIRECT_WAREHOUSE_LIST;
    }
}
