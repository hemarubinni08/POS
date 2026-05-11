package com.ust.pos.api.customer;

import com.ust.pos.api.BaseController;
import com.ust.pos.customer.service.CustomerService;
import com.ust.pos.dto.CustomerDto;
import com.ust.pos.dto.PaginationDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController("customerApiController")
@RequestMapping("/api/customers")
public class CustomerController extends BaseController {

    @Autowired
    private CustomerService customerService;

    @PostMapping("/list")
    public List<CustomerDto> list(@RequestBody PaginationDto paginationDto) {
        Pageable pageable = getPageable(paginationDto.getPage(), paginationDto.getSizePerPage(), paginationDto.getSortDirection(), paginationDto.getSortField());
        return customerService.findAll(pageable);
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
    public CustomerDto update(@PathVariable String identifier, @RequestBody CustomerDto customerDto) {
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