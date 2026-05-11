package com.ust.pos.api.customerapi;

import com.ust.pos.api.BaseController;
import com.ust.pos.customer.service.CustomerService;
import com.ust.pos.dto.CustomerDto;
import com.ust.pos.dto.PaginationDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customer")
public class CustomerApiController extends BaseController {

    @Autowired
    private CustomerService customerService;

    @PostMapping("/list")
    public List<CustomerDto> list(@RequestBody PaginationDto paginationDto) {
        Pageable pageable = getPageable(paginationDto.getPage(), paginationDto.getSizePerPage(),
                paginationDto.getSortDirection(), paginationDto.getSortField());
        return customerService.findAll(pageable);
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
    public CustomerDto toggle(@RequestParam String identifier) {
        return customerService.toggleStatus(identifier);
    }

}