package com.ust.pos.api.customer;

import com.ust.pos.customer.service.CustomerService;
import com.ust.pos.dto.CustomerDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("customerApiController")
@RequestMapping("/api/customers")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @GetMapping
    public List<CustomerDto> getAll() {
        return customerService.findAll();
    }

    @GetMapping("/{identifier}")
    public CustomerDto getByIdentifier(@PathVariable String identifier) {
        return customerService.findByIdentifierWithAddressDto(identifier);
    }

    @PostMapping("/save")
    public CustomerDto save(@RequestBody CustomerDto customerDto) {
        return customerService.save(customerDto);
    }

    @PostMapping("/update/{identifier}")
    public CustomerDto update(@PathVariable String identifier,
                              @RequestBody CustomerDto customerDto) {
        customerDto.setIdentifier(identifier);
        return customerService.update(customerDto);
    }

    @PostMapping("/delete/{identifier}")
    public boolean delete(@PathVariable String identifier) {
        try {
            return customerService.delete(identifier);
        } catch (Exception e) {
            return false;
        }
    }

    @PostMapping("/toggle/{identifier}")
    public CustomerDto toggleStatus(@PathVariable String identifier) {
        return customerService.toggleStatus(identifier);
    }
}