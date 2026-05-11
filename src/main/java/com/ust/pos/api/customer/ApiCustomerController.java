package com.ust.pos.api.customer;

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
public class ApiCustomerController extends BaseController {

    @Autowired
    private CustomerService customerService;

    // ✅ ADD
    @PostMapping("/add")
    public CustomerDto addPost(@RequestBody CustomerDto customerDto) {
        return customerService.save(customerDto);
    }

    // ✅ LIST (Better: use @PostMapping instead of @GetMapping for body)
    @PostMapping("/list")
    public List<CustomerDto> list(@RequestBody PaginationDto paginationDto) {
        Pageable pageable = getPageable(
                paginationDto.getPage(),
                paginationDto.getSizePerPage(),
                paginationDto.getSortDirection(),
                paginationDto.getSortField()
        );
        return customerService.findAll(pageable);
    }

    // ✅ GET (Fixed method call ✅)
    @GetMapping("/get")
    public CustomerDto get(@RequestParam String identifier) {
        return customerService.findByIdentifierWithAddressDto(identifier);
    }

    // ✅ UPDATE
    @PostMapping("/update")
    public CustomerDto updatePost(@RequestBody CustomerDto customerDto) {
        return customerService.update(customerDto);
    }

    // ✅ DELETE
    @GetMapping("/delete")
    public boolean delete(@RequestParam String identifier) {
        try {
            customerService.delete(identifier);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // ✅ TOGGLE STATUS (Added for completeness - same as MVC)
    @PostMapping("/toggle-status")
    public void toggle(@RequestParam String identifier) {
        customerService.toggleStatus(identifier);
    }
}