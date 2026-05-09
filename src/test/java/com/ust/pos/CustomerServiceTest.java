package com.ust.pos;

import com.ust.pos.address.service.AddressService;
import com.ust.pos.customer.service.impl.CustomerServiceImpl;
import com.ust.pos.dto.AddressDto;
import com.ust.pos.dto.CustomerDto;
import com.ust.pos.model.Customer;
import com.ust.pos.model.CustomerRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.server.ResponseStatusException;

import java.lang.reflect.Type;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {

    @InjectMocks
    private CustomerServiceImpl customerService;

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private AddressService addressService;

    @Test
    void findByIdentifierTest() {

        Customer customer = new Customer();
        customer.setIdentifier("CUST1");

        CustomerDto dto = new CustomerDto();
        dto.setIdentifier("CUST1");

        Mockito.when(customerRepository.findByIdentifier("CUST1"))
                .thenReturn(customer);

        Mockito.when(modelMapper.map(customer, CustomerDto.class))
                .thenReturn(dto);

        CustomerDto response = customerService.findByIdentifier("CUST1");

        Assertions.assertEquals("CUST1",
                response.getIdentifier());

        Mockito.verify(customerRepository)
                .findByIdentifier("CUST1");
    }

    @Test
    void findByIdentifierWithAddressDtoTest() {

        Customer customer = new Customer();
        customer.setIdentifier("CUST1");

        CustomerDto dto = new CustomerDto();
        dto.setIdentifier("CUST1");

        AddressDto billing = new AddressDto();
        billing.setAddressType("Billing");

        AddressDto shipping = new AddressDto();
        shipping.setAddressType("Shipping");

        Mockito.when(customerRepository.findByIdentifier("CUST1"))
                .thenReturn(customer);

        Mockito.when(modelMapper.map(customer, CustomerDto.class))
                .thenReturn(dto);

        Mockito.when(addressService.findAllByPhoneNo("CUST1"))
                .thenReturn(List.of(billing, shipping));

        CustomerDto response =
                customerService.findByIdentifierWithAddressDto("CUST1");

        Assertions.assertNotNull(response.getBillingAddress());

        Assertions.assertNotNull(response.getShippingAddress());

        Mockito.verify(addressService)
                .findAllByPhoneNo("CUST1");
    }

    @Test
    void findByIdentifierWithAddressDtoNotFoundTest() {

        Mockito.when(customerRepository.findByIdentifier("CUST1"))
                .thenReturn(null);

        Assertions.assertThrows(ResponseStatusException.class,
                () -> customerService.findByIdentifierWithAddressDto("CUST1"));
    }

    @Test
    void saveTest() {

        CustomerDto dto = new CustomerDto();
        dto.setIdentifier("CUST1");
        dto.setBillingAddress(new AddressDto());
        dto.setShippingAddress(new AddressDto());

        Customer customer = new Customer();

        Mockito.when(customerRepository.findByIdentifier("CUST1"))
                .thenReturn(null);

        Mockito.when(modelMapper.map(dto, Customer.class))
                .thenReturn(customer);

        Mockito.when(modelMapper.map(
                        Mockito.any(AddressDto.class),
                        Mockito.eq(AddressDto.class)))
                .thenReturn(new AddressDto());

        CustomerDto response = customerService.save(dto);

        Assertions.assertEquals("CUST1",
                response.getIdentifier());

        Mockito.verify(customerRepository)
                .save(customer);

        Mockito.verify(addressService,
                        Mockito.times(2))
                .save(Mockito.any(AddressDto.class));
    }

    @Test
    void saveDuplicateTest() {

        CustomerDto dto = new CustomerDto();
        dto.setIdentifier("CUST1");

        Customer existing = new Customer();

        Mockito.when(customerRepository.findByIdentifier("CUST1"))
                .thenReturn(existing);

        CustomerDto response = customerService.save(dto);

        Assertions.assertFalse(response.isSuccess());

        Assertions.assertEquals(
                "Customer with identifier - CUST1 already exists",
                response.getMessage()
        );
    }

    @Test
    void updateTest() {

        CustomerDto dto = new CustomerDto();
        dto.setIdentifier("CUST1");

        AddressDto billing = new AddressDto();
        billing.setAddressType("Billing");
        billing.setIdentifier("CUST1_Billing");

        AddressDto shipping = new AddressDto();
        shipping.setAddressType("Shipping");
        shipping.setIdentifier("CUST1_Shipping");

        dto.setBillingAddress(new AddressDto());
        dto.setShippingAddress(new AddressDto());

        Customer customer = new Customer();

        Mockito.when(customerRepository.findByIdentifier("CUST1"))
                .thenReturn(customer);

        Mockito.when(addressService.findAllByPhoneNo("CUST1"))
                .thenReturn(List.of(billing, shipping));

        Mockito.doNothing()
                .when(modelMapper)
                .map(dto, customer);

        CustomerDto response = customerService.update(dto);

        Assertions.assertEquals("CUST1",
                response.getIdentifier());

        Mockito.verify(customerRepository)
                .save(customer);

        Mockito.verify(addressService,
                        Mockito.times(2))
                .update(Mockito.any(AddressDto.class));
    }

    @Test
    void updateNotFoundTest() {

        CustomerDto dto = new CustomerDto();
        dto.setIdentifier("CUST1");

        Mockito.when(customerRepository.findByIdentifier("CUST1"))
                .thenReturn(null);

        CustomerDto response = customerService.update(dto);

        Assertions.assertFalse(response.isSuccess());

        Assertions.assertEquals(
                "Customer with identifier - CUST1 not found",
                response.getMessage()
        );
    }

    @Test
    void deleteTest() {

        Mockito.doNothing()
                .when(customerRepository)
                .deleteByIdentifier("CUST1");

        Mockito.when(addressService.delete("CUST1"))
                .thenReturn(true);

        boolean response = customerService.delete("CUST1");

        Assertions.assertTrue(response);

        Mockito.verify(customerRepository)
                .deleteByIdentifier("CUST1");

        Mockito.verify(addressService)
                .delete("CUST1");
    }

    @Test
    void findAllTest() {

        Pageable pageable = PageRequest.of(0, 10);

        Customer customer = new Customer();
        customer.setIdentifier("CUST1");

        CustomerDto dto = new CustomerDto();
        dto.setIdentifier("CUST1");

        List<Customer> customers = List.of(customer);
        List<CustomerDto> dtos = List.of(dto);

        Page<Customer> customerPage = new PageImpl<>(customers);

        Mockito.when(customerRepository.findAll(pageable))
                .thenReturn(customerPage);

        Mockito.when(modelMapper.map(
                        Mockito.eq(customers),
                        Mockito.any(Type.class)))
                .thenReturn(dtos);

        List<CustomerDto> response =
                customerService.findAll(pageable);

        Assertions.assertEquals(1, response.size());

        Mockito.verify(customerRepository)
                .findAll(pageable);
    }

    @Test
    void toggleStatusTest() {

        Customer customer = new Customer();
        customer.setIdentifier("CUST1");
        customer.setStatus(true);

        Customer updated = new Customer();
        updated.setIdentifier("CUST1");
        updated.setStatus(false);

        CustomerDto dto = new CustomerDto();
        dto.setIdentifier("CUST1");

        Mockito.when(customerRepository.findByIdentifier("CUST1"))
                .thenReturn(customer);

        Mockito.when(customerRepository.save(customer))
                .thenReturn(updated);

        Mockito.when(modelMapper.map(updated, CustomerDto.class))
                .thenReturn(dto);

        CustomerDto response = customerService.toggleStatus("CUST1");

        Assertions.assertEquals("CUST1",
                response.getIdentifier());

        Mockito.verify(customerRepository)
                .save(customer);
    }
}