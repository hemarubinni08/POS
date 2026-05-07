package com.ust.pos.api.customer;

import com.ust.pos.customer.service.CustomerService;
import com.ust.pos.dto.CustomerDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customer")
public class CustomerControllerApi {

    public static final String REDIRECT_ROLE_LIST = "redirect:/customer/list";
    @Autowired
    private CustomerService customerService;

    @GetMapping("/list")
    public List<CustomerDto> list() {
        return customerService.findAll();
    }

    @PostMapping("/add")
    public CustomerDto addPost(@RequestBody CustomerDto customerDto) {
        return customerService.save(customerDto);
    }

    @GetMapping("/get")
    public CustomerDto updatePage(@RequestParam String identifier) {
        return customerService.findByIdentifier(identifier);
    }

    @PostMapping("/update")
    public CustomerDto updatePost(@RequestBody CustomerDto customerDto) {
        return customerService.update(customerDto);
    }

    @GetMapping("/delete")
    public Boolean delete(@RequestParam String identifier) {

        try {
            customerService.delete(identifier);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    @GetMapping("/active")
    public List<CustomerDto> getActiveCustomers() {
        return customerService.findActive();
    }

    @PostMapping("/toggle-status")
    public CustomerDto toggleStatus(@RequestBody CustomerDto customerDto) {
        return customerService.toggleStatus(customerDto.getIdentifier());
    }
}