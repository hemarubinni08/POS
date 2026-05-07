package com.ust.pos.customer.service;

import com.ust.pos.dto.CustomerDto;

import java.util.List;

public interface CustomerService {
    CustomerDto save(CustomerDto customerDto);

    CustomerDto update(CustomerDto customerDto);

    boolean delete(String phoneNo);

    List<CustomerDto> findAll();

    CustomerDto findByIdentifier(String identifier);

    CustomerDto findByAddress(String phoneNo);

    List<CustomerDto> findAllActive();

    CustomerDto changeStatus(String phoneNo, boolean status);
}
