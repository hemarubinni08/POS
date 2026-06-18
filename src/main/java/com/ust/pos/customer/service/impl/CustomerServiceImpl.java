package com.ust.pos.customer.service.impl;

import com.ust.pos.address.service.AddressService;
import com.ust.pos.customer.service.CustomerService;
import com.ust.pos.dto.AddressDto;
import com.ust.pos.dto.CustomerDto;
import com.ust.pos.dto.WsDto;
import com.ust.pos.modell.Customer;
import com.ust.pos.modell.CustomerRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private AddressService addressService;

    @Override
    public CustomerDto findById(String identifier) {
        Customer customer = customerRepository.findByIdentifier(identifier); // no Optional
        if (customer == null) return null;
        return modelMapper.map(customer, CustomerDto.class);
    }

    @Override
    public CustomerDto findByIdentifierWithAddressDto(String phoneNo) {
        Customer customer = customerRepository.findByPhoneNo(phoneNo);
        CustomerDto customerDto = modelMapper.map(customer, CustomerDto.class);
        List<AddressDto> addressDtoList = addressService.findAllByPhoneNo(phoneNo);

        if (addressDtoList != null) {
            if (addressDtoList != null && !addressDtoList.isEmpty()) {
                customerDto.setBillingAddress(addressDtoList.get(0));
            }

            if (addressDtoList != null && addressDtoList.size() > 1) {
                customerDto.setShippingAddress(addressDtoList.get(1));
            }
            if (addressDtoList.size() > 1) {
                customerDto.setShippingAddress(addressDtoList.get(1));
            }
        }
        return customerDto;
    }

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
        AddressDto billingAddress = modelMapper.map(customerDto.getBillingAddress(), AddressDto.class);
        billingAddress.setIdentifier(customerDto.getIdentifier() + "_" + "Billing");
        billingAddress.setAddressType("Billing");
        billingAddress.setPhoneNo(customerDto.getPhoneNo());
        addressService.save(billingAddress);
        AddressDto shippingAddress = modelMapper.map(customerDto.getShippingAddress(), AddressDto.class);
        shippingAddress.setIdentifier(customerDto.getIdentifier() + "_" + "Shipping");
        shippingAddress.setAddressType("Shipping");
        shippingAddress.setPhoneNo(customerDto.getPhoneNo());
        addressService.save(shippingAddress);
        return customerDto;
    }

    @Override
    public CustomerDto update(CustomerDto customerDto) {

        // ✅ ALWAYS use identifier (stable key)
        Customer existingCustomer = customerRepository.findByIdentifier(customerDto.getIdentifier());

        if (existingCustomer == null) {
            customerDto.setMessage(
                    "Customer with identifier - " + customerDto.getIdentifier() + " not found");
            customerDto.setSuccess(false);
            return customerDto;
        }

        // ✅ Update customer fields
        existingCustomer.setCustomerName(customerDto.getCustomerName());
        existingCustomer.setPhoneNo(customerDto.getPhoneNo());
        existingCustomer.setPartyType(customerDto.getPartyType());
        existingCustomer.setCreditType(customerDto.getCreditType());
        existingCustomer.setCredit(customerDto.getCredit());
        existingCustomer.setCreditLimit(customerDto.getCreditLimit());

        customerRepository.save(existingCustomer);

        // ✅ Update addresses safely
        List<AddressDto> addresses = addressService.findAllByPhoneNo(customerDto.getPhoneNo());

        if (addresses != null && !addresses.isEmpty()) {

            // ✅ Billing Address
            AddressDto billingAddress = addresses.get(0);
            billingAddress.setAddressLine(customerDto.getBillingAddress().getAddressLine());
            billingAddress.setCity(customerDto.getBillingAddress().getCity());
            billingAddress.setState(customerDto.getBillingAddress().getState());
            billingAddress.setZipCode(customerDto.getBillingAddress().getZipCode());
            billingAddress.setCountry(customerDto.getBillingAddress().getCountry());
            billingAddress.setPhoneNo(customerDto.getPhoneNo());

            addressService.update(billingAddress);
        }

        if (addresses != null && addresses.size() > 1) {

            // ✅ Shipping Address
            AddressDto shippingAddress = addresses.get(1);
            shippingAddress.setAddressLine(customerDto.getShippingAddress().getAddressLine());
            shippingAddress.setCity(customerDto.getShippingAddress().getCity());
            shippingAddress.setState(customerDto.getShippingAddress().getState());
            shippingAddress.setZipCode(customerDto.getShippingAddress().getZipCode());
            shippingAddress.setCountry(customerDto.getShippingAddress().getCountry());
            shippingAddress.setPhoneNo(customerDto.getPhoneNo());

            addressService.update(shippingAddress);
        }

        customerDto.setSuccess(true);
        customerDto.setMessage("Customer updated successfully");

        return customerDto;
    }

    @Override
    public boolean delete(String phoneNo) {
        customerRepository.deleteByPhoneNo(phoneNo);
        addressService.delete(phoneNo);
        return true;
    }

    @Override
    public WsDto<CustomerDto> findAll(Pageable pageable) {
        Type listType = new TypeToken<List<CustomerDto>>() {
        }.getType();
        Page<Customer> customerPage = customerRepository.findAll(pageable);
        WsDto<CustomerDto> customerWsDto = new WsDto<>();
        customerWsDto.setDtoList(modelMapper.map(customerPage.getContent(), listType));
        customerWsDto.setTotalRecords(customerPage.getTotalElements());
        customerWsDto.setTotalPage(customerPage.getTotalPages());
        customerWsDto.setSizePerPage(pageable.getPageSize());
        customerWsDto.setPage(pageable.getPageNumber());
        return customerWsDto;
    }

    @Override
    public CustomerDto toggleStatus(String identifier) {
        Customer customer = customerRepository.findByIdentifier(identifier);
        customer.setStatus(!customer.getStatus());
        customerRepository.save(customer);
        return modelMapper.map(customer, CustomerDto.class);
    }

    @Override
    public List<CustomerDto> findIfTrue() {
        Type listType = new TypeToken<List<CustomerDto>>() {
        }.getType();
        return modelMapper.map(
                customerRepository.findByStatusIsTrue(),
                listType
        );
    }
}