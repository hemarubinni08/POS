package com.ust.pos;
import com.ust.pos.dto.AddressDto;
import com.ust.pos.model.Address;
import com.ust.pos.model.AddressRepository;
import com.ust.pos.address.service.impl.AddressServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.List;
@ExtendWith(MockitoExtension.class)
class AddressServiceTest {
    @Mock
    private AddressRepository addressRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private AddressServiceImpl addressService;

    /* ===================== SAVE ===================== */

    @Test
    void saveTest() {
        AddressDto addressDto = new AddressDto();
        addressDto.setIdentifier("Admin");


        Mockito.when(addressRepository.findByIdentifier("Admin")).thenReturn(null);
        Address address=new Address();
        Mockito.when(modelMapper.map(addressDto, Address.class)).thenReturn(address);
        Mockito.when(addressRepository.save(address)).thenReturn(address);

        AddressDto response = addressService.save(addressDto);

        Assertions.assertEquals("Admin", response.getIdentifier());
        Assertions.assertTrue(response.isSuccess());
    }

    @Test
    void saveTestFailure() {
        AddressDto addressDto = new AddressDto();
        addressDto.setIdentifier("Admin");


        Address existingAddress = new Address();
        existingAddress.setIdentifier("Admin");


        Mockito.when(addressRepository.findByIdentifier("Admin"))
                .thenReturn(existingAddress);

        AddressDto response = addressService.save(addressDto);

        Assertions.assertFalse(response.isSuccess());
    }

    /* ===================== FIND BY IDENTIFIER ===================== */

    @Test
    void findByIdentifierTest() {
        Address address = new Address();
        address.setIdentifier("Admin");

        AddressDto addressDto = new AddressDto();
        addressDto.setIdentifier("Admin");

        Mockito.when(addressRepository.findByIdentifier("Admin")).thenReturn(address);
        Mockito.when(modelMapper.map(address, AddressDto.class)).thenReturn(addressDto);

        AddressDto response = addressService.findByIdentifier("Admin");

        Assertions.assertEquals("Admin", response.getIdentifier());
    }

    /* ===================== UPDATE ===================== */

    @Test
    void updateTest() {
        AddressDto addressDto = new AddressDto();
        addressDto.setIdentifier("Admin");

        Address existingAddress = new Address();
        existingAddress.setIdentifier("Admin");

        Mockito.when(addressRepository.findByIdentifier("Admin"))
                .thenReturn(existingAddress);
        Mockito.when(addressRepository.save(existingAddress))
                .thenReturn(existingAddress);

        AddressDto response = addressService.update(addressDto);

        Assertions.assertTrue(response.isSuccess());
    }

    @Test
    void updateTestFailure() {
        AddressDto addressDto = new AddressDto();
        addressDto.setIdentifier("Admin");

        Mockito.when(addressRepository.findByIdentifier("Admin"))
                .thenReturn(null);

        AddressDto response = addressService.update(addressDto);

        Assertions.assertFalse(response.isSuccess());
    }

    /* ===================== DELETE ===================== */

    @Test
    void deleteTest() {
        Address address = new Address();
        List<Address> addressList = List.of(address);
        Mockito.when(addressRepository.findAllByPhoneNo("8919")).thenReturn(addressList);
        Mockito.doNothing().when(addressRepository)
                .deleteAll(addressList);

        boolean response = addressService.delete("8919");

        Assertions.assertEquals(true, response);
    }

    /* ===================== FIND ALL ===================== */

    @Test
    void findAllTest() {
        Address address = new Address();
        address.setIdentifier("Admin");

        AddressDto addressDto = new AddressDto();
        addressDto.setIdentifier("Admin");

        List<Address> addresss = List.of(address);
        List<AddressDto> addressDtos = List.of(addressDto);

        Mockito.when(addressRepository.findAll()).thenReturn(addresss);
        Mockito.when(modelMapper.map(
                Mockito.eq(addresss),
                Mockito.any(java.lang.reflect.Type.class)
        )).thenReturn(addressDtos);

        List<AddressDto> response = addressService.findAll();

        Assertions.assertEquals(1, response.size());
    }

    @Test
    void findAllByPhoneNoTest() {

        Address address = new Address();
        address.setIdentifier("Admin");

        AddressDto addressDto = new AddressDto();
        addressDto.setIdentifier("Admin");


        List<Address> addresss = List.of(address);
        List<AddressDto> addressDtos = List.of(addressDto);

        Mockito.when(addressRepository.findAllByPhoneNo("8919")).thenReturn(addresss);
        Mockito.when(modelMapper.map(
                Mockito.eq(addresss),
                Mockito.any(java.lang.reflect.Type.class)
        )).thenReturn(addressDtos);

        List<AddressDto> response = addressService.findAllByPhoneNo("8919");

        Assertions.assertEquals(1, response.size());
    }

}
