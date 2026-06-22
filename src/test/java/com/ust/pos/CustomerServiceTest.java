package com.ust.pos;

import com.ust.pos.address.service.AddressService;
import com.ust.pos.customer.service.impl.CustomerServiceImpl;
import com.ust.pos.dto.AddressDto;
import com.ust.pos.dto.CustomerDto;
import com.ust.pos.model.Customer;
import com.ust.pos.model.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

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

    // ---------------- SAVE SUCCESS ----------------
    @Test
    void save_success() {

        CustomerDto dto = new CustomerDto();
        dto.setPhoneNo("9876543210");

        Customer mapped = new Customer();
        mapped.setPhoneNo("9876543210");

        Customer saved = new Customer();
        saved.setIdentifier("9876543210");
        saved.setPhoneNo("9876543210");
        saved.setStatus(true);

        when(customerRepository.findByPhoneNo("9876543210"))
                .thenReturn(null);

        when(modelMapper.map(dto, Customer.class))
                .thenReturn(mapped);

        when(customerRepository.save(any(Customer.class)))
                .thenReturn(saved);

        CustomerDto response = customerService.save(dto);

        assertTrue(response.isSuccess());
        assertEquals("Customer created successfully", response.getMessage());
    }

    // ---------------- SAVE INVALID PHONE ----------------
    @Test
    void save_failure_invalid_phone() {

        CustomerDto dto = new CustomerDto();
        dto.setPhoneNo("123");

        CustomerDto response = customerService.save(dto);

        assertFalse(response.isSuccess());
        assertEquals("Valid 10-digit phone required", response.getMessage());
    }

    // ---------------- SAVE DUPLICATE ----------------
    @Test
    void save_failure_duplicate() {

        CustomerDto dto = new CustomerDto();
        dto.setPhoneNo("9876543210");

        when(customerRepository.findByPhoneNo("9876543210"))
                .thenReturn(new Customer());

        CustomerDto response = customerService.save(dto);

        assertFalse(response.isSuccess());
        assertEquals("Customer already exists", response.getMessage());
    }

    // ---------------- FIND SUCCESS ----------------
    @Test
    void find_success() {

        Customer customer = new Customer();
        customer.setIdentifier("9876543210");
        customer.setPhoneNo("9876543210");

        CustomerDto mapped = new CustomerDto();
        mapped.setIdentifier("9876543210");

        when(customerRepository.findByIdentifier("9876543210"))
                .thenReturn(customer);

        when(modelMapper.map(customer, CustomerDto.class))
                .thenReturn(mapped);

        when(addressService.findByPhoneNoAndAddressType("9876543210", "billing"))
                .thenReturn(new AddressDto());

        when(addressService.findByPhoneNoAndAddressType("9876543210", "shipping"))
                .thenReturn(new AddressDto());

        CustomerDto response = customerService.findByIdentifier("9876543210");

        assertNotNull(response);
        assertEquals("9876543210", response.getIdentifier());
    }

    // ---------------- FIND NOT FOUND ----------------
    @Test
    void find_not_found() {

        when(customerRepository.findByIdentifier("123"))
                .thenReturn(null);

        CustomerDto response = customerService.findByIdentifier("123");

        assertFalse(response.isSuccess());
        assertEquals("Customer not found", response.getMessage());
    }

    // ---------------- UPDATE SUCCESS ----------------
    @Test
    void update_success() {

        CustomerDto dto = new CustomerDto();
        dto.setIdentifier("9876543210");
        dto.setPhoneNo("9876543210");

        Customer existing = new Customer();
        existing.setIdentifier("9876543210");

        when(customerRepository.findByIdentifier("9876543210"))
                .thenReturn(existing);

        when(customerRepository.save(any(Customer.class)))
                .thenReturn(existing);

        CustomerDto response = customerService.update(dto);

        assertTrue(response.isSuccess());
        assertEquals("Customer updated successfully", response.getMessage());
    }

    // ---------------- UPDATE NOT FOUND ----------------
    @Test
    void update_failure_notFound() {

        CustomerDto dto = new CustomerDto();
        dto.setIdentifier("123");

        when(customerRepository.findByIdentifier("123"))
                .thenReturn(null);

        CustomerDto response = customerService.update(dto);

        assertFalse(response.isSuccess());
        assertEquals("Customer not found", response.getMessage());
    }

    // ---------------- DELETE (SOFT DELETE) ----------------
    @Test
    void delete_test() {

        Customer customer = new Customer();
        customer.setIdentifier("123");
        customer.setPhoneNo("9876543210");

        when(customerRepository.findByIdentifier("123"))
                .thenReturn(customer);

        when(customerRepository.save(any(Customer.class)))
                .thenReturn(customer);

        doNothing().when(addressService).delete("9876543210");

        customerService.delete("123");

        assertTrue(customer.getDeleted());

        verify(addressService).delete("9876543210");
        verify(customerRepository).save(customer);
    }

    // ---------------- ACTIVE ----------------
    @Test
    void active_test() {

        Customer active = new Customer();
        active.setStatus(true);

        Customer inactive = new Customer();
        inactive.setStatus(false);

        when(customerRepository.findByStatusTrueAndDeletedFalse())
                .thenReturn(List.of(active));

        when(modelMapper.map(anyList(), any()))
                .thenReturn(List.of(new CustomerDto()));

        List<CustomerDto> result = customerService.findActive();

        assertEquals(1, result.size());
    }

    // ---------------- TOGGLE SUCCESS ----------------
    @Test
    void toggle_success() {

        Customer customer = new Customer();
        customer.setIdentifier("123");
        customer.setStatus(true);

        when(customerRepository.findByIdentifier("123"))
                .thenReturn(customer);

        when(customerRepository.save(any(Customer.class)))
                .thenReturn(customer);

        CustomerDto response = customerService.toggleStatus("123");

        assertTrue(response.isSuccess());
        assertEquals("Status updated", response.getMessage());
    }

    // ---------------- TOGGLE NOT FOUND ----------------
    @Test
    void toggle_notFound() {

        when(customerRepository.findByIdentifier("123"))
                .thenReturn(null);

        CustomerDto response = customerService.toggleStatus("123");

        assertFalse(response.isSuccess());
        assertEquals("Customer not found", response.getMessage());
    }
}