package com.ust.pos.product;

import com.ust.pos.dto.ProductDto;
import com.ust.pos.price.service.PriceService;
import com.ust.pos.product.service.ProductService;
import com.ust.pos.category.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/product")
public class ProductController {

    public static final String REDIRECT_PRODUCT_LIST = "redirect:/product/list";

    @Autowired
    private ProductService productService;

    @Autowired
    private PriceService priceService;

    @Autowired
    private CategoryService categoryService;


    @GetMapping("/list")
    public String list(Model model) {
        model.addAttribute("products", productService.getAllProducts());
        model.addAttribute("prices", priceService.getAllPrices());
        return "product/list";
    }


    @GetMapping("/add")
    public String add(Model model) {
        model.addAttribute("productDto", new ProductDto());

        model.addAttribute("categories", categoryService.getAllCategories());

        return "product/add";
    }


    @PostMapping("/add")
    public String addPost(@ModelAttribute ProductDto productDto, Model model) {

        ProductDto response = productService.createProduct(productDto);

        if (!response.isSuccess()) {
            model.addAttribute("message", response.getMessage());
            model.addAttribute("messageType", "error");

            model.addAttribute("categories", categoryService.getAllCategories());

            return "product/add";
        }

        return REDIRECT_PRODUCT_LIST;
    }


    @GetMapping("/get")
    public String update(@RequestParam Long id, Model model) {

        ProductDto response = productService.getProduct(id);

        if (!response.isSuccess()) {
            model.addAttribute("message", response.getMessage());
            model.addAttribute("messageType", "error");
            return REDIRECT_PRODUCT_LIST;
        }

        model.addAttribute("product", response);

        model.addAttribute("categories", categoryService.getAllCategories());

        return "product/product";
    }


    @PostMapping("/update")
    public String updatePost(@ModelAttribute ProductDto productDto, Model model) {

        ProductDto response = productService.updateProduct(productDto);

        if (!response.isSuccess()) {
            model.addAttribute("message", response.getMessage());
            model.addAttribute("messageType", "error");

            model.addAttribute("product", productDto);
            model.addAttribute("categories", categoryService.getAllCategories());

            return "product/product";
        }

        return REDIRECT_PRODUCT_LIST;
    }

    @GetMapping("/delete")
    public String delete(@RequestParam Long id) {

        productService.deleteProduct(id);

        return REDIRECT_PRODUCT_LIST;
    }
}
