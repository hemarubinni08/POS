package com.ust.pos;

import com.ust.pos.address.service.impl.AddressServiceImpl;
import com.ust.pos.dto.AddressDto;
import com.ust.pos.model.Address;
import com.ust.pos.model.AddressRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.modelmapper.ModelMapper;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class AddressServiceTest {

    @InjectMocks
    private AddressServiceImpl addressService;

    @Mock
    private AddressRepository addressRepository;

    @Mock
    private ModelMapper modelMapper;

    //  SAVE - SUCCESS (INSERT NEW) 
    @Test
    void save_success_insertNew() {

        AddressDto dto = new AddressDto();
        dto.setPhoneNo("9876543210");
        dto.setAddressType("billing");
        dto.setAddressLine("Street 1");
        dto.setCity("Chennai");
        dto.setState("TN");
        dto.setZip("600001");
        dto.setCountry("India");

        Mockito.when(addressRepository
                        .findTopByPhoneNoAndAddressTypeOrderByIdDesc("9876543210", "billing"))
                .thenReturn(null);

        Mockito.when(modelMapper.map(dto, Address.class))
                .thenReturn(new Address());

        addressService.save(dto);

        Assertions.assertTrue(dto.isSuccess());
        Assertions.assertEquals("Address saved successfully", dto.getMessage());

        Mockito.verify(addressRepository).save(Mockito.any(Address.class));
    }

    //  SAVE - UPDATE EXISTING 
    @Test
    void save_success_updateExisting() {

        AddressDto dto = new AddressDto();
        dto.setPhoneNo("9876543210");
        dto.setAddressType("billing");
        dto.setAddressLine("Street 1");
        dto.setCity("Chennai");
        dto.setState("TN");
        dto.setZip("600001");
        dto.setCountry("India");

        Address existing = new Address();

        Mockito.when(addressRepository
                        .findTopByPhoneNoAndAddressTypeOrderByIdDesc("9876543210", "billing"))
                .thenReturn(existing);

        addressService.save(dto);

        Assertions.assertTrue(dto.isSuccess());
        Assertions.assertEquals("Address saved successfully", dto.getMessage());

        Mockito.verify(addressRepository).save(existing);
    }

    //  SAVE - VALIDATION FAILURE 
    @Test
    void save_failure_missingFields() {

        AddressDto dto = new AddressDto(); // empty

        addressService.save(dto);

        Assertions.assertFalse(dto.isSuccess());
        Assertions.assertEquals("All address fields are required", dto.getMessage());
    }

    //  FIND 
    @Test
    void find_success() {

        Address address = new Address();

        AddressDto mapped = new AddressDto();

        Mockito.when(addressRepository
                        .findTopByPhoneNoAndAddressTypeOrderByIdDesc("9876543210", "billing"))
                .thenReturn(address);

        Mockito.when(modelMapper.map(address, AddressDto.class))
                .thenReturn(mapped);

        AddressDto result = addressService
                .findByPhoneNoAndAddressType("9876543210", "billing");

        Assertions.assertNotNull(result);
    }

    //  FIND - NOT FOUND 
    @Test
    void find_notFound() {

        Mockito.when(addressRepository
                        .findTopByPhoneNoAndAddressTypeOrderByIdDesc("9876543210", "billing"))
                .thenReturn(null);

        AddressDto result = addressService
                .findByPhoneNoAndAddressType("9876543210", "billing");

        Assertions.assertNull(result);
    }

    //  DELETE 
    @Test
    void delete_test() {

        Mockito.doNothing().when(addressRepository).deleteByPhoneNo("9876543210");

        addressService.delete("9876543210");

        Mockito.verify(addressRepository).deleteByPhoneNo("9876543210");
    }
}