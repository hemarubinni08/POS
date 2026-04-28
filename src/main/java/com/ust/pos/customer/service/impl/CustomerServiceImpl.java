package com.ust.pos.customer.service.impl;

import com.ust.pos.address.service.AddressService;
import com.ust.pos.customer.service.CustomerService;
import com.ust.pos.dto.AddressDto;
import com.ust.pos.dto.CategoryDto;
import com.ust.pos.dto.CustomerDto;
import com.ust.pos.model.Customer;
import com.ust.pos.model.CustomerRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {
    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private AddressService addressService;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CustomerDto save(CustomerDto customerDto) {
        String identifier = customerDto.getIdentifier();
        Customer existingCustomer = customerRepository.findByIdentifier(identifier);
        if (existingCustomer != null) {
            customerDto.setMessage("Customer with identifier - " + identifier + " already exists");
            customerDto.setSuccess(false);
            return customerDto;
        }
        Customer customer = modelMapper.map(customerDto, Customer.class);
        customerRepository.save(customer);
        if (customerDto.getBillingAddress() != null) {
            AddressDto billingAddress = customerDto.getBillingAddress();
            String billIdentifier = customerDto.getUsername() + "_billing_" +customerDto.getIdentifier();
            billingAddress.setIdentifier(billIdentifier);
            billingAddress.setPhoneNo(customerDto.getIdentifier());
            billingAddress.setCustomerName(customer.getCustomerName());
            billingAddress.setAddressType("billing");
            addressService.save(billingAddress);
        }
        if (customerDto.getShippingAddress() != null) {
            AddressDto shippingAddress = customerDto.getShippingAddress();
            String shipIdentifier = customerDto.getUsername() + "_shipping_" + customerDto.getIdentifier();
            shippingAddress.setIdentifier(shipIdentifier);
            shippingAddress.setPhoneNo(customerDto.getIdentifier());
            shippingAddress.setCustomerName(customer.getCustomerName());
            shippingAddress.setAddressType("shipping");
            addressService.save(shippingAddress);
        }
        customerDto.setSuccess(true);
        return customerDto;
    }

    @Override
    public CustomerDto update(CustomerDto customerDto) {
        return null;
    }

    @Override
    public List<CustomerDto> findAll() {
        Type listType = new TypeToken<List<CategoryDto>>() {
        }.getType();
        return modelMapper.map(customerRepository.findAll(), listType);
    }

    @Override
    public CustomerDto findByIdentifier(String identifier) {
        Customer customer=customerRepository.findByIdentifier(identifier);
        return modelMapper.map(customer,CustomerDto.class);
    }

    @Override
    public boolean delete(String identifier) {
        customerRepository.deleteByIdentifier(identifier);
        addressService.delete(identifier);
        return true;
    }

    @Override
    public void updateStatus(String identifier) {
        Customer customer = customerRepository.findByIdentifier(identifier);
        customer.setStatus(!customer.isStatus());
        customerRepository.save(customer);
    }

    @Override
    public List<CustomerDto> findAllActive() {
        Type listType = new TypeToken<List<CategoryDto>>() {
        }.getType();
        return modelMapper.map(customerRepository.findAllByStatus(true), listType);
    }
}