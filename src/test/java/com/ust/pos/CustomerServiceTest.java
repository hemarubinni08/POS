package com.ust.pos;

import com.ust.pos.address.service.AddressService;
import com.ust.pos.customer.service.impl.CustomerServiceImpl;
import com.ust.pos.dto.CustomerDto;
import com.ust.pos.model.Customer;
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

import java.lang.reflect.Type;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class CustomerServiceTest {

    @InjectMocks
    private CustomerServiceImpl customerService;

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private AddressService addressService;

    // ================= SAVE =================
    @Test
    void saveTest_Success() {

        CustomerDto dto = new CustomerDto();
        dto.setPhoneNo("9876543210");
        dto.setName("Shashi");

        Customer mapped = new Customer();
        mapped.setPhoneNo("9876543210");
        mapped.setName("Shashi");

        Customer saved = new Customer();
        saved.setIdentifier("9876543210");
        saved.setPhoneNo("9876543210");

        Mockito.when(customerRepository.findByPhoneNo("9876543210"))
                .thenReturn(null);

        Mockito.when(modelMapper.map(dto, Customer.class))
                .thenReturn(mapped);

        Mockito.when(customerRepository.save(Mockito.any(Customer.class)))
                .thenReturn(saved);

        CustomerDto response = customerService.save(dto);

        Assertions.assertTrue(response.isSuccess());
    }

    @Test
    void saveTest_Failure_EmptyPhone() {

        CustomerDto dto = new CustomerDto();

        CustomerDto response = customerService.save(dto);

        Assertions.assertFalse(response.isSuccess());
        Assertions.assertEquals("Phone number is required", response.getMessage());
    }

    @Test
    void saveTest_Failure_InvalidPhone() {

        CustomerDto dto = new CustomerDto();
        dto.setPhoneNo("123");

        CustomerDto response = customerService.save(dto);

        Assertions.assertFalse(response.isSuccess());
        Assertions.assertEquals("Phone number must be 10 digits", response.getMessage());
    }

    @Test
    void saveTest_Failure_Duplicate() {

        CustomerDto dto = new CustomerDto();
        dto.setPhoneNo("9876543210");

        Mockito.when(customerRepository.findByPhoneNo("9876543210"))
                .thenReturn(new Customer());

        CustomerDto response = customerService.save(dto);

        Assertions.assertFalse(response.isSuccess());
        Assertions.assertEquals("Customer already exists", response.getMessage());
    }

    // ================= FIND =================
    @Test
    void findByIdentifierTest() {

        Customer customer = new Customer();
        customer.setIdentifier("9876543210");
        customer.setPhoneNo("9876543210");

        CustomerDto mapped = new CustomerDto();
        mapped.setIdentifier("9876543210");

        Mockito.when(customerRepository.findByIdentifier("9876543210"))
                .thenReturn(customer);

        Mockito.when(modelMapper.map(customer, CustomerDto.class))
                .thenReturn(mapped);

        Mockito.when(addressService.findByPhoneNoAndAddressType(Mockito.any(), Mockito.any()))
                .thenReturn(null);

        CustomerDto response = customerService.findByIdentifier("9876543210");

        Assertions.assertNotNull(response);
        Assertions.assertEquals("9876543210", response.getIdentifier());
    }

    @Test
    void findByIdentifier_NotFound() {

        Mockito.when(customerRepository.findByIdentifier("123"))
                .thenReturn(null);

        CustomerDto response = customerService.findByIdentifier("123");

        Assertions.assertNull(response);
    }

    // ================= UPDATE =================
    @Test
    void updateTest_Success() {

        CustomerDto dto = new CustomerDto();
        dto.setIdentifier("9876543210");
        dto.setPhoneNo("9876543210");

        Customer existing = new Customer();
        existing.setIdentifier("9876543210");
        existing.setPhoneNo("9876543210");

        Mockito.when(customerRepository.findByIdentifier("9876543210"))
                .thenReturn(existing);

        Mockito.when(customerRepository.save(existing))
                .thenReturn(existing);

        CustomerDto response = customerService.update(dto);

        Assertions.assertTrue(response.isSuccess());
    }

    @Test
    void updateTest_Failure_NotFound() {

        CustomerDto dto = new CustomerDto();
        dto.setIdentifier("123");

        Mockito.when(customerRepository.findByIdentifier("123"))
                .thenReturn(null);

        CustomerDto response = customerService.update(dto);

        Assertions.assertFalse(response.isSuccess());
        Assertions.assertEquals("Customer not found", response.getMessage());
    }

    // ================= DELETE =================
    @Test
    void deleteTest() {

        Mockito.doNothing().when(customerRepository)
                .deleteByIdentifier("123");

        Mockito.doNothing().when(addressService)
                .delete("123");

        customerService.delete("123");

        Mockito.verify(customerRepository).deleteByIdentifier("123");
        Mockito.verify(addressService).delete("123");
    }

    // ================= FIND ALL =================
    @Test
    void findAllTest() {

        Customer customer = new Customer();

        List<Customer> list = List.of(customer);

        Type listType = new TypeToken<List<CustomerDto>>() {}.getType();

        Mockito.when(customerRepository.findAll())
                .thenReturn(list);

        Mockito.when(modelMapper.map(list, listType))
                .thenReturn(List.of(new CustomerDto()));

        List<CustomerDto> result = customerService.findAll();

        Assertions.assertEquals(1, result.size());
    }

    // ================= ACTIVE =================
    @Test
    void findActiveTest() {

        Customer active = new Customer();
        active.setStatus(true);

        Customer inactive = new Customer();
        inactive.setStatus(false);

        List<Customer> list = List.of(active, inactive);

        Type listType = new TypeToken<List<CustomerDto>>() {}.getType();

        Mockito.when(customerRepository.findAll())
                .thenReturn(list);

        Mockito.when(modelMapper.map(Mockito.anyList(), Mockito.eq(listType)))
                .thenReturn(List.of(new CustomerDto()));

        List<CustomerDto> result = customerService.findActive();

        Assertions.assertEquals(1, result.size());
    }

    // ================= TOGGLE =================
    @Test
    void toggleStatusTest() {

        Customer customer = new Customer();
        customer.setIdentifier("123");
        customer.setStatus(true);

        Mockito.when(customerRepository.findByIdentifier("123"))
                .thenReturn(customer);

        Mockito.when(customerRepository.save(customer))
                .thenReturn(customer);

        CustomerDto response = customerService.toggleStatus("123");

        Assertions.assertTrue(response.isSuccess());
        Assertions.assertFalse(response.getStatus());
    }

    @Test
    void toggleStatus_NotFound() {

        Mockito.when(customerRepository.findByIdentifier("123"))
                .thenReturn(null);

        CustomerDto response = customerService.toggleStatus("123");

        Assertions.assertFalse(response.isSuccess());
    }
}