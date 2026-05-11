package com.ust.pos;

import com.ust.pos.address.service.AddressService;
import com.ust.pos.customer.service.impl.CustomerServiceImpl;
import com.ust.pos.dto.AddressDto;
import com.ust.pos.dto.CustomerDto;
import com.ust.pos.model.Address;
import com.ust.pos.model.Customer;
import com.ust.pos.model.AddressRepository;
import com.ust.pos.model.CustomerRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private AddressRepository addressRepository;

    @Mock
    private AddressService addressService;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private CustomerServiceImpl customerService;

    @Test
    void saveCustomerSuccess() {
        CustomerDto dto = new CustomerDto();
        dto.setPhoneNum("1234567890");
        dto.setUsername("test@gmail.com");
        dto.setBillingAddress(new AddressDto());
        dto.setShippingAddress(new AddressDto());

        Mockito.when(customerRepository.findByPhoneNum("1234567890"))
                .thenReturn(null);

        Mockito.when(customerRepository.save(Mockito.any(Customer.class)))
                .thenReturn(new Customer());

        Mockito.when(addressRepository.save(Mockito.any(Address.class)))
                .thenReturn(new Address());

        CustomerDto response = customerService.save(dto);

        Assertions.assertNotNull(response);
    }

    @Test
    void saveCustomerAlreadyExists() {
        CustomerDto dto = new CustomerDto();
        dto.setPhoneNum("1234567890");

        Mockito.when(customerRepository.findByPhoneNum("1234567890"))
                .thenReturn(new Customer());

        CustomerDto response = customerService.save(dto);

        Assertions.assertFalse(response.isSuccess());
        Assertions.assertTrue(response.getMessage().contains("already exist"));
    }

    @Test
    void findByIdentifierSuccess() {
        Customer customer = new Customer();
        customer.setIdentifier("123");

        CustomerDto customerDto = new CustomerDto();
        customerDto.setIdentifier("123");

        AddressDto billing = new AddressDto();
        AddressDto shipping = new AddressDto();

        Mockito.when(customerRepository.findByIdentifier("123"))
                .thenReturn(customer);

        Mockito.when(modelMapper.map(customer, CustomerDto.class))
                .thenReturn(customerDto);

        Mockito.when(addressService.findByIdentifierAndAddressType("123", "Billing Address"))
                .thenReturn(billing);

        Mockito.when(addressService.findByIdentifierAndAddressType("123", "Shipping Address"))
                .thenReturn(shipping);

        CustomerDto response = customerService.findByIdentifier("123");

        Assertions.assertEquals("123", response.getIdentifier());
        Assertions.assertNotNull(response.getBillingAddress());
        Assertions.assertNotNull(response.getShippingAddress());
    }

    @Test
    void findAllCustomers() {
        Customer customer = new Customer();
        CustomerDto customerDto = new CustomerDto();

        Page<Customer> page = new PageImpl<>(List.of(customer));

        Mockito.when(customerRepository.findAll(Mockito.any(Pageable.class)))
                .thenReturn(page);

        Type listType = new TypeToken<List<CustomerDto>>() {
        }.getType();
        Mockito.when(modelMapper.map(page.getContent(), listType))
                .thenReturn(List.of(customerDto));

        Pageable pageable = PageRequest.of(0, 50, Sort.unsorted());
        List<CustomerDto> response = customerService.findAll(pageable);

        Assertions.assertEquals(1, response.size());
    }

    @Test
    void changeCustomerStatusSuccess() {
        Customer customer = new Customer();
        customer.setIdentifier("123");

        Mockito.when(customerRepository.findByIdentifier("123"))
                .thenReturn(customer);

        Mockito.when(customerRepository.save(customer))
                .thenReturn(customer);

        Mockito.when(modelMapper.map(customer, CustomerDto.class))
                .thenReturn(new CustomerDto());

        CustomerDto response = customerService.changeCustomerStatus("123", true);

        Assertions.assertNotNull(response);
    }

    @Test
    void updateCustomerSuccess() {
        CustomerDto dto = new CustomerDto();
        dto.setIdentifier("123");
        dto.setBillingAddress(new AddressDto());
        dto.setShippingAddress(new AddressDto());

        Customer customer = new Customer();

        Mockito.when(customerRepository.findByIdentifier("123"))
                .thenReturn(customer);

        Mockito.when(addressService.findByIdentifierAndAddressType("123", "Billing Address"))
                .thenReturn(new AddressDto());

        Mockito.when(addressService.findByIdentifierAndAddressType("123", "Shipping Address"))
                .thenReturn(new AddressDto());

        CustomerDto response = customerService.update(dto);

        Assertions.assertTrue(response.isSuccess());
    }

    @Test
    void findByIdSuccess() {
        Customer customer = new Customer();
        CustomerDto dto = new CustomerDto();

        Mockito.when(customerRepository.findById(1L))
                .thenReturn(Optional.of(customer));

        Mockito.when(modelMapper.map(customer, CustomerDto.class))
                .thenReturn(dto);

        CustomerDto response = customerService.findById(1L);

        Assertions.assertNotNull(response);
    }

    @Test
    void deleteByIdentifier() {
        Mockito.doNothing().when(customerRepository)
                .deleteByIdentifier("123");

        customerService.deleteByIdentifier("123");

        Mockito.verify(customerRepository, Mockito.times(1))
                .deleteByIdentifier("123");
    }
}