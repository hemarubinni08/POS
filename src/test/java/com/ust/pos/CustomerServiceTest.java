package com.ust.pos;

import com.ust.pos.customer.service.AddressService;
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

    //  SAVE SUCCESS
    @Test
    void saveTest() {
        CustomerDto customerDto = new CustomerDto();
        customerDto.setIdentifier("C1");
        customerDto.setPhoneNo(999999);

        AddressDto billing = new AddressDto();
        AddressDto shipping = new AddressDto();

        customerDto.setBillingAddress(billing);
        customerDto.setShippingAddress(shipping);

        Mockito.when(customerRepository.findByIdentifier("C1"))
                .thenReturn(null);

        Customer customer = new Customer();

        Mockito.when(modelMapper.map(Mockito.any(CustomerDto.class), Mockito.eq(Customer.class)))
                .thenReturn(customer);

        Mockito.when(customerRepository.save(Mockito.any(Customer.class)))
                .thenReturn(customer);

        customerDto.setSuccess(true);

        CustomerDto response = customerService.save(customerDto);

        Assertions.assertNotNull(response);
        Assertions.assertTrue(response.isSuccess());
    }

    //  SAVE FAILURE
    @Test
    void saveTestFailure() {
        CustomerDto customerDto = new CustomerDto();
        customerDto.setIdentifier("C1");

        Mockito.when(customerRepository.findByIdentifier("C1"))
                .thenReturn(new Customer());

        CustomerDto response = customerService.save(customerDto);

        Assertions.assertFalse(response.isSuccess());
        Assertions.assertNotNull(response.getMessage());
    }

    //  FIND BY IDENTIFIER
    @Test
    void findByIdentifierTest() {
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

    //  UPDATE SUCCESS
    @Test
    void updateTest() {
        CustomerDto customerDto = new CustomerDto();
        customerDto.setIdentifier("C1");
        customerDto.setPhoneNo(999999);

        AddressDto billing = new AddressDto();
        AddressDto shipping = new AddressDto();

        customerDto.setBillingAddress(billing);
        customerDto.setShippingAddress(shipping);

        Customer existing = new Customer();
        existing.setIdentifier("C1");
        existing.setPhoneNo(999999);

        Mockito.when(customerRepository.findByIdentifier("C1"))
                .thenReturn(existing);

        //  important (void mapping)
        Mockito.doNothing().when(modelMapper)
                .map(Mockito.eq(customerDto), Mockito.eq(existing));

        Mockito.when(customerRepository.save(Mockito.any(Customer.class)))
                .thenReturn(existing);

        Mockito.when(addressService.findByPhoneNoAndAddressType(Mockito.any(), Mockito.any()))
                .thenReturn(new AddressDto());

        customerDto.setSuccess(true);

        CustomerDto response = customerService.update(customerDto);

        Assertions.assertTrue(response.isSuccess());
    }

    //  UPDATE FAILURE
    @Test
    void updateTestFailure() {
        CustomerDto customerDto = new CustomerDto();
        customerDto.setIdentifier("C1");

        Mockito.when(customerRepository.findByIdentifier("C1"))
                .thenReturn(null);

        CustomerDto response = customerService.update(customerDto);

        Assertions.assertFalse(response.isSuccess());
    }

    //  DELETE
    @Test
    void deleteTest() {
        Mockito.doNothing().when(customerRepository)
                .deleteByIdentifier("C1");

        customerService.delete("C1");

        Mockito.verify(customerRepository)
                .deleteByIdentifier("C1");
    }

    //  FIND ALL
    @Test
    void findAllTest() {
        //  Arrange
        Customer customer = new Customer();
        customer.setIdentifier("C1");

        CustomerDto customerDto = new CustomerDto();
        customerDto.setIdentifier("C1");

        List<Customer> customerList = List.of(customer);
        List<CustomerDto> customerDtoList = List.of(customerDto);

        //  Pageable
        Pageable pageable = PageRequest.of(0, 10);

        //  Mock Page
        Page<Customer> customerPage = new PageImpl<>(customerList);

        //  Mock repository
        Mockito.when(customerRepository.findAll(pageable))
                .thenReturn(customerPage);

        //  Mock model mapper
        Mockito.when(modelMapper.map(
                Mockito.eq(customerList),
                Mockito.any(java.lang.reflect.Type.class)
        )).thenReturn(customerDtoList);

        //  Act
        List<CustomerDto> response = customerService.findAll(pageable);

        //  Assert
        Assertions.assertEquals(1, response.size());
        Assertions.assertEquals("C1", response.get(0).getIdentifier());
    }

    //  BUILD ADDRESS IDENTIFIER
    @Test
    void buildAddressIdentifierTest() {
        AddressDto address = new AddressDto();
        address.setAddressLine("abc street");
        address.setZipcode(12345L);
        address.setAddressType("billingAddress");

        String result = customerService.buildAddressIdentifier(address);

        Assertions.assertEquals("ABC STREET-12345-BILLINGADDRESS", result);
    }

    //  FIND BY IDENTIFIER FAILURE
    @Test
    void findByIdentifierTestFailure() {

        Mockito.when(customerRepository.findByIdentifier("C1"))
                .thenReturn(null);

        CustomerDto response =
                customerService.findByIdentifier("C1");

        Assertions.assertNull(response);
    }

    //  BUILD ADDRESS IDENTIFIER NULL
    @Test
    void buildAddressIdentifierTestFailure() {

        String result =
                customerService.buildAddressIdentifier(null);

        Assertions.assertNull(result);
    }
}