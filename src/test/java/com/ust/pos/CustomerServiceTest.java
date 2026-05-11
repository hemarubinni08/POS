package com.ust.pos;

import com.ust.pos.address.service.AddressService;
import com.ust.pos.customer.service.impl.CustomerServiceImpl;
import com.ust.pos.dto.AddressDto;
import com.ust.pos.dto.CustomerDto;
import com.ust.pos.modell.Customer;
import com.ust.pos.modell.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.lang.reflect.Type;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private AddressService addressService;

    @InjectMocks
    private CustomerServiceImpl customerService;

    private Customer customer;
    private CustomerDto customerDto;
    private AddressDto billingAddress;
    private AddressDto shippingAddress;

    @BeforeEach
    void setup() {
        customer = new Customer();
        customer.setIdentifier("C001");
        customer.setPhoneNo("9876543210");
        customer.setStatus(true);

        billingAddress = new AddressDto();
        billingAddress.setAddressType("Billing");

        shippingAddress = new AddressDto();
        shippingAddress.setAddressType("Shipping");

        customerDto = new CustomerDto();
        customerDto.setIdentifier("C001");
        customerDto.setPhoneNo("9876543210");
        customerDto.setBillingAddress(billingAddress);
        customerDto.setShippingAddress(shippingAddress);
    }

    @Test
    void findById_shouldReturnCustomerDto() {
        when(customerRepository.findById("C001")).thenReturn(customer);
        when(modelMapper.map(customer, CustomerDto.class)).thenReturn(customerDto);

        CustomerDto result = customerService.findById("C001");

        assertNotNull(result);
        assertEquals("9876543210", result.getPhoneNo());
    }

    @Test
    void findByIdentifierWithAddressDto_shouldReturnCustomerWithAddresses() {
        when(customerRepository.findByPhoneNo("9876543210")).thenReturn(customer);
        when(modelMapper.map(customer, CustomerDto.class)).thenReturn(customerDto);
        when(addressService.findAllByPhoneNo("9876543210"))
                .thenReturn(List.of(billingAddress, shippingAddress));

        CustomerDto result =
                customerService.findByIdentifierWithAddressDto("9876543210");

        assertNotNull(result);
        assertNotNull(result.getBillingAddress());
        assertNotNull(result.getShippingAddress());
    }

    @Test
    void save_shouldPersistCustomerAndAddresses() {
        when(customerRepository.findById("C001")).thenReturn(null);
        when(modelMapper.map(customerDto, Customer.class)).thenReturn(customer);
        when(modelMapper.map(any(AddressDto.class), eq(AddressDto.class)))
                .thenAnswer(inv -> inv.getArgument(0));

        CustomerDto result = customerService.save(customerDto);

        verify(customerRepository).save(customer);
        verify(addressService, times(2)).save(any(AddressDto.class));
        assertEquals("C001", result.getIdentifier());
    }

    @Test
    void save_shouldFail_whenCustomerExists() {
        when(customerRepository.findById("C001")).thenReturn(customer);

        CustomerDto result = customerService.save(customerDto);

        assertFalse(result.isSuccess());
        assertTrue(result.getMessage().contains("already exists"));
        verify(customerRepository, never()).save(any());
    }

    @Test
    void update_shouldUpdateCustomerAndAddresses() {
        when(customerRepository.findByPhoneNo("9876543210")).thenReturn(customer);
        when(addressService.findAllByPhoneNo("9876543210"))
                .thenReturn(List.of(billingAddress, shippingAddress));

        CustomerDto result = customerService.update(customerDto);

        verify(modelMapper).map(customerDto, customer);
        verify(customerRepository).save(customer);
        verify(addressService, times(2)).update(any(AddressDto.class));
        assertEquals("9876543210", result.getPhoneNo());
    }

    @Test
    void update_shouldFail_whenCustomerNotFound() {
        when(customerRepository.findByPhoneNo("9876543210")).thenReturn(null);

        CustomerDto result = customerService.update(customerDto);

        assertFalse(result.isSuccess());
        assertTrue(result.getMessage().contains("not found"));
    }

    @Test
    void delete_shouldDeleteCustomerAndAddresses() {
        doNothing().when(customerRepository).deleteByPhoneNo("9876543210");
        when(addressService.delete("9876543210")).thenReturn(true);

        boolean result = customerService.delete("9876543210");

        verify(customerRepository).deleteByPhoneNo("9876543210");
        verify(addressService).delete("9876543210");
        assertTrue(result);
    }

    @Test
    void findAll_shouldReturnPagedCustomers() {
        Customer cust = new Customer();
        cust.setIdentifier("C1");

        CustomerDto dto = new CustomerDto();
        dto.setIdentifier("C1");

        Page<Customer> page =
                new PageImpl<>(List.of(cust), PageRequest.of(0, 5), 1);

        when(customerRepository.findAll(any(Pageable.class))).thenReturn(page);
        when(modelMapper.map(eq(List.of(cust)), any(Type.class)))
                .thenReturn(List.of(dto));

        List<CustomerDto> result =
                customerService.findAll(PageRequest.of(0, 5));

        assertEquals(1, result.size());
    }

    @Test
    void toggleStatus_shouldToggleCustomerStatus() {
        when(customerRepository.findById("C001")).thenReturn(customer);
        when(modelMapper.map(customer, CustomerDto.class)).thenReturn(customerDto);

        CustomerDto result = customerService.toggleStatus("C001");

        assertFalse(customer.isStatus());
        verify(customerRepository).save(customer);
        assertNotNull(result);
    }

    @Test
    void findIfTrue_shouldReturnOnlyActiveCustomers() {
        when(customerRepository.findByStatusIsTrue())
                .thenReturn(List.of(customer));
        when(modelMapper.map(any(List.class), any(Type.class)))
                .thenReturn(List.of(customerDto));

        List<CustomerDto> result = customerService.findIfTrue();

        assertEquals(1, result.size());
    }

    @Test
    void findById_shouldReturnNull_whenCustomerNotFound() {

        when(customerRepository.findById("C404")).thenReturn(null);
        when(modelMapper.map(null, CustomerDto.class)).thenReturn(null);

        CustomerDto result = customerService.findById("C404");

        assertNull(result);

        verify(customerRepository).findById("C404");
    }

    @Test
    void save_shouldGenerateCorrectAddressIdentifiers() {

        when(customerRepository.findById("C001")).thenReturn(null);
        when(modelMapper.map(customerDto, Customer.class)).thenReturn(customer);
        when(modelMapper.map(any(AddressDto.class), eq(AddressDto.class)))
                .thenAnswer(inv -> inv.getArgument(0));

        customerService.save(customerDto);

        verify(addressService).save(argThat(addr ->
                addr.getIdentifier().equals("C001_Billing") &&
                        addr.getAddressType().equals("Billing")
        ));

        verify(addressService).save(argThat(addr ->
                addr.getIdentifier().equals("C001_Shipping") &&
                        addr.getAddressType().equals("Shipping")
        ));
    }

    @Test
    void delete_shouldAlwaysDeleteCustomerAndAddresses() {

        doNothing().when(customerRepository).deleteByPhoneNo(anyString());
        when(addressService.delete(anyString())).thenReturn(true);

        boolean result = customerService.delete("9876543210");

        assertTrue(result);

        verify(customerRepository).deleteByPhoneNo("9876543210");
        verify(addressService).delete("9876543210");
    }

    @Test
    void findAll_shouldReturnEmptyList_whenNoCustomers() {

        Page<Customer> emptyPage =
                new PageImpl<>(List.of(), PageRequest.of(0, 5), 0);

        when(customerRepository.findAll(any(Pageable.class)))
                .thenReturn(emptyPage);
        when(modelMapper.map(eq(List.of()), any(Type.class)))
                .thenReturn(List.of());

        List<CustomerDto> result =
                customerService.findAll(PageRequest.of(0, 5));

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }
}