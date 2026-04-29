package com.ust.pos.api.customerApi;

import com.ust.pos.customer.service.CustomerService;
import com.ust.pos.dto.CustomerDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customer")
public class CustomerApiController {

    @Autowired
    private CustomerService customerService;

    @GetMapping("/list")
    public List<CustomerDto> list() {

        return customerService.findAll();
    }

    @PostMapping("/add")
    public CustomerDto add(@RequestBody CustomerDto customerDto) {

        return customerService.save(customerDto);
    }

    @GetMapping("/get")
    public CustomerDto get(@RequestParam String identifier) {
        return customerService.findByIdentifierWithAddressDto(identifier);
    }

    @PostMapping("/update")
    public CustomerDto update(@RequestBody CustomerDto customerDto) {

        return customerService.update(customerDto);
    }

    @GetMapping("/delete")
    public boolean delete(@RequestParam String identifier) {
        try {
            customerService.delete(identifier);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @PostMapping("/toggle-status")
    public void toggle(@RequestParam String identifier) {

        customerService.toggleStatus(identifier);
    }
}
