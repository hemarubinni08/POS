package com.ust.pos.product;

import com.ust.pos.api.BaseController;
import com.ust.pos.category.service.CategoryService;
import com.ust.pos.dto.PaginationDto;
import com.ust.pos.dto.ProductDto;
import com.ust.pos.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/product")
public class ProductController extends BaseController {

    public static final String REDIRECT_PRODUCT_LIST = "redirect:/product/list";
    public static final String CATEGORIES = "categories";

    @Autowired
    private ProductService productService;
    @Autowired
    private CategoryService categoryService;

    @GetMapping("/list")
    public String home(Model model) {
        PaginationDto paginationDto = new PaginationDto();
        model.addAttribute("products", productService.findAll(getPageable(paginationDto.getPage(), paginationDto.getSizePerPage(),
                paginationDto.getSortDirection(), paginationDto.getSortField())));
        return "product/list";
    }

    @GetMapping("/add")
    public String add(Model model, @ModelAttribute ProductDto productDto) {
        model.addAttribute(CATEGORIES, categoryService.findBySuperCategoryNotNull());
        return "product/add";
    }

    @PostMapping("/add")
    public String addPost(Model model, @ModelAttribute ProductDto productDto) {

        ProductDto response = productService.save(productDto);

        if (!response.isSuccess()) {
            model.addAttribute("message", response.getMessage());
            return "product/add";
        }

        return REDIRECT_PRODUCT_LIST;
    }

    @GetMapping("/get")
    public String update(Model model, @RequestParam String identifier) {

        ProductDto response = productService.findByIdentifier(identifier);
        model.addAttribute("product", response);
        model.addAttribute(CATEGORIES, categoryService.findBySuperCategoryNotNull());
        return "product/product";
    }

    @PostMapping("/update")
    public String updatePost(Model model, @ModelAttribute ProductDto productDto) {

        ProductDto response = productService.update(productDto);

        if (!response.isSuccess()) {
            model.addAttribute("message", response.getMessage());
            model.addAttribute(CATEGORIES, categoryService.findBySuperCategoryNotNull());
            model.addAttribute("product",
                    productService.findByIdentifier(productDto.getIdentifier()));
            return "product/product";
        }

        return REDIRECT_PRODUCT_LIST;
    }

    @GetMapping("/delete")
    public String delete(@RequestParam String identifier) {

        productService.delete(identifier);
        return REDIRECT_PRODUCT_LIST;
    }
}
