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
        return modelMapper.map(customerRepository.findById(identifier), CustomerDto.class
        );
    }

    @Override
    public CustomerDto findByIdentifierWithAddressDto(String phoneNo) {
        Customer customer = customerRepository.findByPhoneNo(phoneNo);

        if (customer == null) {
            return null;
        }

        CustomerDto customerDto = modelMapper.map(customer, CustomerDto.class);

        // 🚀 CRITICAL FIX: Explicitly copy the email from the identifier column
        // into the email field of the DTO so the frontend can read it!
        customerDto.setIdentifier(customer.getIdentifier());

        List<AddressDto> addressDtoList = addressService.findAllByPhoneNo(phoneNo);

        if (addressDtoList != null && !addressDtoList.isEmpty()) {
            customerDto.setBillingAddress(addressDtoList.get(0));

            if (addressDtoList.size() > 1) {
                customerDto.setShippingAddress(addressDtoList.get(1));
            }
        }

        return customerDto;
    }

    @Override
    public CustomerDto save(CustomerDto customerDto) {

        // CHANGE THIS LINE: Read email from the incoming DTO to use as the identifier value
        String identifier = customerDto.getIdentifier();

        // CRITICAL: Keep checking existence by phone number so lookup mechanics do not break
        Customer existingCustomer = customerRepository.findByPhoneNo(customerDto.getPhoneNo());

        if (existingCustomer != null) {
            customerDto.setMessage("Customer already exists");
            customerDto.setSuccess(false);
            return customerDto;
        }

        // ✅ Save customer mapping record
        Customer customer = modelMapper.map(customerDto, Customer.class);

        // This line writes the email string cleanly to the posdb.customer.identifier database column
        customer.setIdentifier(identifier);
        customerRepository.save(customer);

        // ✅ Save Billing Address using email context strings to avoid foreign integrity breaks if needed
        if (customerDto.getBillingAddress() != null) {
            AddressDto billing = customerDto.getBillingAddress();

            billing.setIdentifier(identifier + "_Billing");
            billing.setAddressType("Billing");
            billing.setPhoneNo(customerDto.getPhoneNo()); // Keep raw phone link intact

            addressService.save(billing);
        }

        // ✅ Save Shipping Address
        if (customerDto.getShippingAddress() != null) {
            AddressDto shipping = customerDto.getShippingAddress();

            shipping.setIdentifier(identifier + "_Shipping");
            shipping.setAddressType("Shipping");
            shipping.setPhoneNo(customerDto.getPhoneNo()); // Keep raw phone link intact

            addressService.save(shipping);
        }

        return customerDto;
    }
    @Override
    public CustomerDto update(CustomerDto customerDto) {

        Customer existingCustomer = customerRepository.findByPhoneNo(customerDto.getPhoneNo());

        if (existingCustomer == null) {
            customerDto.setMessage(
                    "Customer with phoneNo - " + customerDto.getPhoneNo() + " not found");
            customerDto.setSuccess(false);
            return customerDto;
        }

        // ✅ update customer
        modelMapper.map(customerDto, existingCustomer);
        customerRepository.save(existingCustomer);

        String phoneNo = existingCustomer.getPhoneNo();

        // ✅ UPDATE BILLING ADDRESS
        if (customerDto.getBillingAddress() != null) {
            AddressDto billing = customerDto.getBillingAddress();

            billing.setPhoneNo(phoneNo);
            billing.setAddressType("Billing");
            billing.setIdentifier(phoneNo + "_Billing");

            addressService.update(billing);
        }

        // ✅ UPDATE SHIPPING ADDRESS
        if (customerDto.getShippingAddress() != null) {
            AddressDto shipping = customerDto.getShippingAddress();

            shipping.setPhoneNo(phoneNo);
            shipping.setAddressType("Shipping");
            shipping.setIdentifier(phoneNo + "_Shipping");

            addressService.update(shipping);
        }

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
        Customer customer = customerRepository.findById(identifier);
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