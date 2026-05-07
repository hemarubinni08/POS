package com.ust.pos.customer.service;

import com.ust.pos.dto.CustomerDto;

import java.util.List;

public interface CustomerService {

    CustomerDto findByIdentifier(String identifier);
    CustomerDto save(CustomerDto customerDto);
    CustomerDto update(CustomerDto customerDto);
    void delete(String identifier);
    List<CustomerDto> findAll();
    List<CustomerDto> findActive();
    CustomerDto toggleStatus(String identifier);
}