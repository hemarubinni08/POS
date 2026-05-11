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
import org.springframework.data.domain.*;
import java.util.ArrayList;
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
    void saveTest_Success() {
        CustomerDto customerDto = new CustomerDto();
        customerDto.setIdentifier("C1");
        customerDto.setBillingAddress(new AddressDto());
        customerDto.setShippingAddress(new AddressDto());
        Mockito.when(customerRepository.findByIdentifier("C1"))
                .thenReturn(null);
        Customer customer = new Customer();
        Mockito.when(modelMapper.map(customerDto, Customer.class))
                .thenReturn(customer);
        Mockito.when(customerRepository.save(customer))
                .thenReturn(customer);
        Mockito.when(modelMapper.map(Mockito.any(), Mockito.eq(AddressDto.class)))
                .thenReturn(new AddressDto());
        CustomerDto response = customerService.save(customerDto);
        Mockito.verify(addressService, Mockito.times(2))
                .save(Mockito.any(AddressDto.class));
        Assertions.assertEquals("C1", response.getIdentifier());
    }

    @Test
    void saveTest_Failure() {
        CustomerDto dto = new CustomerDto();
        dto.setIdentifier("C1");
        Mockito.when(customerRepository.findByIdentifier("C1"))
                .thenReturn(new Customer());
        CustomerDto response = customerService.save(dto);
        Assertions.assertFalse(response.isSuccess());
        Assertions.assertNotNull(response.getMessage());
    }

    @Test
    void findByIdentifier_Success() {
        Customer customer = new Customer();
        customer.setIdentifier("C1");
        CustomerDto dto = new CustomerDto();
        dto.setIdentifier("C1");
        Mockito.when(customerRepository.findByIdentifier("C1"))
                .thenReturn(customer);
        Mockito.when(modelMapper.map(customer, CustomerDto.class))
                .thenReturn(dto);
        CustomerDto response = customerService.findByIdentifier("C1");
        Assertions.assertEquals("C1", response.getIdentifier());
    }

    @Test
    void findByIdentifier_NullCustomer() {
        Mockito.when(customerRepository.findByIdentifier("C404"))
                .thenReturn(null);
        Mockito.when(modelMapper.map(null, CustomerDto.class))
                .thenReturn(null);
        CustomerDto response = customerService.findByIdentifier("C404");
        Assertions.assertNull(response);
    }

    @Test
    void findByIdentifierWithAddressDto_Success() {
        Customer customer = new Customer();
        customer.setIdentifier("C1");
        CustomerDto dto = new CustomerDto();
        dto.setIdentifier("C1");
        Mockito.when(customerRepository.findByIdentifier("C1"))
                .thenReturn(customer);
        Mockito.when(modelMapper.map(customer, CustomerDto.class))
                .thenReturn(dto);
        Mockito.when(addressService.findAllByPhoneNo("C1"))
                .thenReturn(List.of(new AddressDto(), new AddressDto()));
        CustomerDto response =
                customerService.findByIdentifierWithAddressDto("C1");
        Assertions.assertNotNull(response.getBillingAddress());
        Assertions.assertNotNull(response.getShippingAddress());
    }

    @Test
    void findByIdentifierWithAddressDto_EmptyList_ShouldThrowException() {
        Customer customer = new Customer();
        Mockito.when(customerRepository.findByIdentifier("C404"))
                .thenReturn(customer);
        Mockito.when(modelMapper.map(customer, CustomerDto.class))
                .thenReturn(new CustomerDto());
        Mockito.when(addressService.findAllByPhoneNo("C404"))
                .thenReturn(List.of());
        Assertions.assertThrows(
                IndexOutOfBoundsException.class,
                () -> customerService.findByIdentifierWithAddressDto("C404"));
    }

    @Test
    void updateTest_Success() {
        CustomerDto dto = new CustomerDto();
        dto.setIdentifier("C1");
        dto.setBillingAddress(new AddressDto());
        dto.setShippingAddress(new AddressDto());
        Customer customer = new Customer();
        customer.setIdentifier("C1");
        Mockito.when(customerRepository.findByIdentifier("C1"))
                .thenReturn(customer);
        Mockito.when(customerRepository.save(customer))
                .thenReturn(customer);
        Mockito.when(addressService.findAllByPhoneNo("C1"))
                .thenReturn(List.of(new AddressDto(), new AddressDto()));
        CustomerDto response = customerService.update(dto);
        Mockito.verify(addressService, Mockito.times(2))
                .update(Mockito.any(AddressDto.class));
        Assertions.assertEquals("C1", response.getIdentifier());
    }

    @Test
    void updateTest_Failure() {
        CustomerDto dto = new CustomerDto();
        dto.setIdentifier("C1");
        Mockito.when(customerRepository.findByIdentifier("C1"))
                .thenReturn(null);
        CustomerDto response = customerService.update(dto);
        Assertions.assertFalse(response.isSuccess());
    }

    @Test
    void deleteTest() {
        Mockito.doNothing()
                .when(customerRepository)
                .deleteByIdentifier("C1");
        Mockito.when(addressService.delete("C1"))
                .thenReturn(true);
        boolean result = customerService.delete("C1");
        Assertions.assertTrue(result);
        Mockito.verify(addressService).delete("C1");
    }

    @Test
    void findAllTest_WithData() {
        Customer customer = new Customer();
        customer.setIdentifier("C1");
        CustomerDto dto = new CustomerDto();
        dto.setIdentifier("C1");
        List<Customer> customers = List.of(customer);
        List<CustomerDto> customerDtos = List.of(dto);
        Pageable pageable = PageRequest.of(0, 50, Sort.by(new ArrayList<>()));
        Page<Customer> customerPage =
                new PageImpl<>(customers, pageable, customers.size());
        Mockito.when(customerRepository.findAll(pageable))
                .thenReturn(customerPage);
        Mockito.when(
                modelMapper.map(
                        Mockito.eq(customers),
                        Mockito.any(java.lang.reflect.Type.class)
                )
        ).thenReturn(customerDtos);
        List<CustomerDto> response = customerService.findAll(pageable);
        Assertions.assertEquals(1, response.size());
        Assertions.assertEquals("C1", response.get(0).getIdentifier());
    }

    @Test
    void findAllTest_Empty() {
        Pageable pageable = PageRequest.of(0, 50, Sort.by(new ArrayList<>()));
        Page<Customer> customerPage =
                new PageImpl<>(List.of(), pageable, 0);
        Mockito.when(customerRepository.findAll(pageable))
                .thenReturn(customerPage);
        Mockito.when(
                modelMapper.map(
                        Mockito.eq(List.of()),
                        Mockito.any(java.lang.reflect.Type.class)
                )
        ).thenReturn(List.of());
        List<CustomerDto> response = customerService.findAll(pageable);
        Assertions.assertTrue(response.isEmpty());
    }

    @Test
    void toggleStatus_TrueToFalse() {
        Customer customer = new Customer();
        customer.setIdentifier("C1");
        customer.setStatus(true);
        Customer updated = new Customer();
        updated.setIdentifier("C1");
        updated.setStatus(false);
        CustomerDto dto = new CustomerDto();
        dto.setIdentifier("C1");
        Mockito.when(customerRepository.findByIdentifier("C1"))
                .thenReturn(customer);
        Mockito.when(customerRepository.save(customer))
                .thenReturn(updated);
        Mockito.when(modelMapper.map(updated, CustomerDto.class))
                .thenReturn(dto);
        CustomerDto response = customerService.toggleStatus("C1");
        Assertions.assertEquals("C1", response.getIdentifier());
    }

    @Test
    void toggleStatus_FalseToTrue() {
        Customer customer = new Customer();
        customer.setIdentifier("C2");
        customer.setStatus(false);
        Customer updated = new Customer();
        updated.setIdentifier("C2");
        updated.setStatus(true);
        CustomerDto dto = new CustomerDto();
        dto.setIdentifier("C2");
        Mockito.when(customerRepository.findByIdentifier("C2"))
                .thenReturn(customer);
        Mockito.when(customerRepository.save(customer))
                .thenReturn(updated);
        Mockito.when(modelMapper.map(updated, CustomerDto.class))
                .thenReturn(dto);
        CustomerDto response = customerService.toggleStatus("C2");
        Assertions.assertEquals("C2", response.getIdentifier());
    }

    @Test
    void toggleStatus_NullCustomer_ShouldThrowException() {
        Mockito.when(customerRepository.findByIdentifier("C404"))
                .thenReturn(null);
        Assertions.assertThrows(
                NullPointerException.class,
                () -> customerService.toggleStatus("C404"));
    }
}