package com.ust.pos.stock;

import com.ust.pos.dto.StockDto;
import com.ust.pos.product.service.ProductService;
import com.ust.pos.stock.service.StockService;
import com.ust.pos.warehouse.service.WarehouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/stock")
public class StockController {

    public static final String REDIRECT_STOCK_LIST = "redirect:/stock/list";

    @Autowired
    private StockService stockService;

    @Autowired
    private ProductService productService;

    @Autowired
    private WarehouseService warehouseService;

    @GetMapping("/list")
    public String list(Model model) {
        model.addAttribute("stocks", stockService.getAllStocks());
        return "stock/list";
    }

    @GetMapping("/add")
    public String add(Model model) {

        model.addAttribute("stockDto", new StockDto());
        model.addAttribute("warehouses", warehouseService.findIfTrue());
        model.addAttribute("products", productService.findIfTrue());

        return "stock/add";
    }

    @PostMapping("/add")
    public String addPost(@ModelAttribute StockDto stockDto, Model model) {

        StockDto response = stockService.createStock(stockDto);

        if (!response.isSuccess()) {
            model.addAttribute("stockDto", stockDto);
            model.addAttribute("warehouses", warehouseService.findIfTrue());
            model.addAttribute("products", productService.findIfTrue());
            model.addAttribute("message", response.getMessage());
            model.addAttribute("messageType", "error");
            return "stock/add";
        }

        return REDIRECT_STOCK_LIST;
    }

    @GetMapping("/get")
    public String update(@RequestParam Long productId,
                         @RequestParam Long warehouseId,
                         Model model) {

        StockDto response = stockService.getStock(productId, warehouseId);

        if (!response.isSuccess()) {
            model.addAttribute("message", response.getMessage());
            model.addAttribute("messageType", "error");
            return REDIRECT_STOCK_LIST;
        }

        model.addAttribute("stock", response);
        model.addAttribute("warehouses", warehouseService.findIfTrue());
        model.addAttribute("products", productService.findIfTrue());

        return "stock/stock";
    }

    @PostMapping("/update")
    public String updatePost(@RequestParam Long stockId,
                             @RequestParam Integer quantity,
                             Model model) {

        StockDto response = stockService.updateStockQuantity(stockId, quantity);

        if (!response.isSuccess()) {
            model.addAttribute("message", response.getMessage());
            model.addAttribute("messageType", "error");
        }

        return REDIRECT_STOCK_LIST;
    }

    @GetMapping("/delete")
    public String delete(@RequestParam Long id, Model model) {

        boolean deleted = stockService.deleteStock(id);

        if (!deleted) {
            model.addAttribute("message", "Stock not found");
            model.addAttribute("messageType", "error");
        }

        return REDIRECT_STOCK_LIST;
    }
}