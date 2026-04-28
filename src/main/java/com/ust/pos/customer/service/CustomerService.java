package com.ust.pos.customer.service;

import com.ust.pos.dto.CustomerDto;

import java.util.List;

public interface CustomerService {

    CustomerDto save(CustomerDto customerDto);

    CustomerDto update(CustomerDto customerDto);

    List<CustomerDto> findAll();

    CustomerDto findByIdentifier(String identifier);

    boolean delete(String identifier);

    void updateStatus(String identifier);

    List<CustomerDto> findAllActive();
}