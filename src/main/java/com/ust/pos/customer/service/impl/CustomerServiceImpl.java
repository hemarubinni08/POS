package com.ust.pos.customer.service.impl;

import com.ust.pos.address.service.AddressService;
import com.ust.pos.customer.service.CustomerService;
import com.ust.pos.dto.AddressDto;
import com.ust.pos.dto.CustomerDto;
import com.ust.pos.model.Customer;
import com.ust.pos.model.CustomerRepository;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private AddressService addressService;

    //  FIND 
    @Override
    public CustomerDto findByIdentifier(String identifier) {

        Customer customer = customerRepository.findByIdentifier(identifier);

        if (customer == null) {
            return null;
        }

        CustomerDto dto = modelMapper.map(customer, CustomerDto.class);

        dto.setBillingAddress(
                addressService.findByPhoneNoAndAddressType(customer.getPhoneNo(), "billing")
        );
        dto.setShippingAddress(
                addressService.findByPhoneNoAndAddressType(customer.getPhoneNo(), "shipping")
        );

        return dto;
    }

    //  SAVE 
    @Override
    public CustomerDto save(CustomerDto customerDto) {

        if (customerDto.getPhoneNo() == null || customerDto.getPhoneNo().isEmpty()) {
            customerDto.setSuccess(false);
            customerDto.setMessage("Phone number is required");
            return customerDto;
        }

        if (!customerDto.getPhoneNo().matches("\\d{10}")) {
            customerDto.setSuccess(false);
            customerDto.setMessage("Phone number must be 10 digits");
            return customerDto;
        }

        Customer existing = customerRepository.findByPhoneNo(customerDto.getPhoneNo());

        if (existing != null) {
            customerDto.setSuccess(false);
            customerDto.setMessage("Customer already exists");
            return customerDto;
        }

        // SAVE ADDRESSES
        saveAddresses(customerDto);

        Customer customer = modelMapper.map(customerDto, Customer.class);

        // IMPORTANT: identifier = phoneNo (ONLY ONCE HERE)
        customer.setIdentifier(customerDto.getPhoneNo());

        if (customer.getStatus() == null) {
            customer.setStatus(true);
        }

        customerRepository.save(customer);

        customerDto.setSuccess(true);
        return customerDto;
    }

    //  UPDATE 
    @Override
    public CustomerDto update(CustomerDto customerDto) {

        Customer existing = customerRepository.findByIdentifier(customerDto.getIdentifier());

        if (existing == null) {
            customerDto.setSuccess(false);
            customerDto.setMessage("Customer not found");
            return customerDto;
        }

        // PHONE VALIDATION
        if (!existing.getPhoneNo().equals(customerDto.getPhoneNo())) {

            Customer phoneExists = customerRepository.findByPhoneNo(customerDto.getPhoneNo());

            if (phoneExists != null) {
                customerDto.setSuccess(false);
                customerDto.setMessage("Phone already used");
                return customerDto;
            }

            // update phone ONLY if changed
            existing.setPhoneNo(customerDto.getPhoneNo());
        }

        //  MANUAL FIELD UPDATE
        existing.setName(customerDto.getName());
        existing.setEmail(customerDto.getEmail());
        existing.setBalance(customerDto.getBalance());
        existing.setBalanceType(customerDto.getBalanceType());
        existing.setPartyType(customerDto.getPartyType());
        existing.setCreditLimit(customerDto.getCreditLimit());

        //  STATUS
        if (customerDto.getStatus() != null) {
            existing.setStatus(customerDto.getStatus());
        }

        // SAVE ADDRESSES
        saveAddresses(customerDto);

        customerRepository.save(existing);

        customerDto.setSuccess(true);
        return customerDto;
    }

    //  DELETE (SOFT) 
    @Override
    public void delete(String identifier) {
        customerRepository.deleteByIdentifier(identifier);
        addressService.delete(identifier);
    }

    //  FIND ALL 
    @Override
    public List<CustomerDto> findAll() {

        Type listType = new TypeToken<List<CustomerDto>>() {
        }.getType();

        return modelMapper.map(customerRepository.findAll(), listType);
    }

    //  ACTIVE 
    @Override
    public List<CustomerDto> findActive() {

        List<Customer> list = new ArrayList<>();
        for (Customer c : customerRepository.findAll()) {
            if (Boolean.TRUE.equals(c.getStatus())) {
                list.add(c);
            }
        }

        Type listType = new TypeToken<List<CustomerDto>>() {
        }.getType();

        return modelMapper.map(list, listType);
    }

    @Override
    public CustomerDto toggleStatus(String identifier) {

        CustomerDto response = new CustomerDto();

        Customer customer = customerRepository.findByIdentifier(identifier);

        if (customer == null) {
            response.setSuccess(false);
            response.setMessage("Customer not found");
            return response;
        }

        // TOGGLE LOGIC
        customer.setStatus(!Boolean.TRUE.equals(customer.getStatus()));

        Customer saved = customerRepository.save(customer);

        response.setIdentifier(saved.getIdentifier());
        response.setName(saved.getName());
        response.setPhoneNo(saved.getPhoneNo());
        response.setStatus(saved.getStatus());

        response.setSuccess(true);
        response.setMessage("Customer status updated successfully");

        return response;
    }

    //  COMMON METHOD 
    private void saveAddresses(CustomerDto customerDto) {

        if (customerDto.getBillingAddress() != null) {
            AddressDto billing = customerDto.getBillingAddress();
            billing.setPhoneNo(customerDto.getPhoneNo());
            billing.setAddressType("billing");
            addressService.save(billing);
        }

        if (customerDto.getShippingAddress() != null) {
            AddressDto shipping = customerDto.getShippingAddress();
            shipping.setPhoneNo(customerDto.getPhoneNo());
            shipping.setAddressType("shipping");
            addressService.save(shipping);
        }
    }
}