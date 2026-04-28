package com.ust.pos.customer;

import com.ust.pos.customer.service.CustomerService;
import com.ust.pos.dto.CustomerDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/customer")
public class CustomerController {
    public static final String REDIRECT_CUSTOMER_LIST = "redirect:/customer/list";
    @Autowired
    private CustomerService customerService;

    @GetMapping("/list")
    public String home(Model model) {
        model.addAttribute("categories", customerService.findAll());
        return "customer/list";
    }

    @GetMapping("/add")
    public String add(Model model, @ModelAttribute CustomerDto userDto) {
        model.addAttribute("categories", customerService.findAll());
        return "customer/add";
    }

    @PostMapping("/add")
    public String addPost(Model model, @ModelAttribute CustomerDto userDto, RedirectAttributes redirectAttributes) {
        CustomerDto response = customerService.save(userDto);
        if (!response.isSuccess()) {
            model.addAttribute("message", response.getMessage());
            redirectAttributes.addFlashAttribute("error", response.getMessage());
            return "redirect:/customer/add";
        }

        redirectAttributes.addFlashAttribute("success","Customer added successfully");
        return REDIRECT_CUSTOMER_LIST;
    }

    @GetMapping("/get")
    public String update(Model model, @RequestParam String identifier) {
        CustomerDto response = customerService.findByIdentifier(identifier);
        model.addAttribute("customer", response);
        model.addAttribute("categories", customerService.findAll());
        return "customer/customer";
    }

    @PostMapping("/update")
    public String updatePost(Model model, @ModelAttribute CustomerDto userDto) {
        CustomerDto response = customerService.update(userDto);
        if (!response.isSuccess()) {
            model.addAttribute("message", response.getMessage());
        }
        return REDIRECT_CUSTOMER_LIST;
    }

    @GetMapping("/delete")
    public String delete(Model model, @RequestParam String identifier) {
        customerService.delete(identifier);
        return REDIRECT_CUSTOMER_LIST;
    }
}