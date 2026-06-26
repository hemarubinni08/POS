package com.ust.pos;

import com.ust.pos.address.service.impl.AddressServiceImpl;
import com.ust.pos.dto.AddressDto;
import com.ust.pos.modell.Address;
import com.ust.pos.modell.AddressRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AddressServiceTest {

    @Mock
    private AddressRepository addressRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private AddressServiceImpl addressService;

    private Address address;
    private AddressDto addressDto;

    @BeforeEach
    void setUp() {

        address = new Address();
        address.setPhoneNo("9876543210");
        address.setAddressType("HOME");
        address.setDeleted(false);

        addressDto = new AddressDto();
        addressDto.setPhoneNo("9876543210");
        addressDto.setAddressType("HOME");
    }

    @Test
    void testFindByIdentifier() {
        when(addressRepository.findByIdentifierAndDeletedFalse("ID001")).thenReturn(address);
        when(modelMapper.map(address, AddressDto.class)).thenReturn(addressDto);
        AddressDto result = addressService.findByIdentifier("ID001");
        assertNotNull(result);
        assertEquals("9876543210", result.getPhoneNo());
        verify(addressRepository).findByIdentifierAndDeletedFalse("ID001");
        verify(modelMapper).map(address, AddressDto.class);
    }

    @Test
    void testFindByIdentifierWhenNotFound() {
        when(addressRepository.findByIdentifierAndDeletedFalse("ID002")).thenReturn(null);
        AddressDto result = addressService.findByIdentifier("ID002");
        assertNull(result);
        verify(addressRepository).findByIdentifierAndDeletedFalse("ID002");
        verify(modelMapper, never()).map(any(), eq(AddressDto.class));
    }

    @Test
    void testFindAllByPhoneNo() {
        List<Address> addressList = List.of(address);
        List<AddressDto> dtoList = List.of(addressDto);

        when(addressRepository.findAllByPhoneNoAndDeletedFalse("9876543210")).thenReturn(addressList);
        when(modelMapper.map(any(), any(Type.class))).thenReturn(dtoList);
        List<AddressDto> result = addressService.findAllByPhoneNo("9876543210");
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(addressRepository).findAllByPhoneNoAndDeletedFalse("9876543210");
    }

    @Test
    void testFindAllByPhoneNoEmptyList() {
        when(addressRepository.findAllByPhoneNoAndDeletedFalse("000")).thenReturn(Collections.emptyList());
        when(modelMapper.map(any(), any(Type.class))).thenReturn(Collections.emptyList());
        List<AddressDto> result = addressService.findAllByPhoneNo("000");
        assertTrue(result.isEmpty());
        verify(addressRepository).findAllByPhoneNoAndDeletedFalse("000");
    }

    @Test
    void testSave() {
        when(modelMapper.map(addressDto, Address.class)).thenReturn(address);
        when(modelMapper.map(address, AddressDto.class)).thenReturn(addressDto);
        AddressDto result = addressService.save(addressDto);
        assertNotNull(result);
        assertEquals(addressDto.getPhoneNo(), result.getPhoneNo());
        verify(modelMapper).map(addressDto, Address.class);
        verify(addressRepository).save(address);
    }

    @Test
    void testUpdateSuccess() {

        when(addressRepository.findByPhoneNoAndAddressTypeAndDeletedFalse(
                "9876543210", "HOME"))
                .thenReturn(address);

        doAnswer(invocation -> null)
                .when(modelMapper)
                .map(addressDto, address);

        when(modelMapper.map(address, AddressDto.class))
                .thenReturn(addressDto);

        AddressDto result = addressService.update(addressDto);

        assertNotNull(result);

        verify(modelMapper).map(addressDto, address);
        verify(addressRepository).save(address);
        verify(modelMapper).map(address, AddressDto.class);
    }

    @Test
    void testUpdateAddressNotFound() {
        when(addressRepository.findByPhoneNoAndAddressTypeAndDeletedFalse("9876543210", "HOME")).thenReturn(null);
        RuntimeException exception = assertThrows(RuntimeException.class, () -> addressService.update(addressDto));
        assertEquals("Address not found", exception.getMessage());
        verify(addressRepository).findByPhoneNoAndAddressTypeAndDeletedFalse("9876543210", "HOME");
        verify(addressRepository, never()).save(any());
    }

    @Test
    void testDeleteWithRecords() {
        List<Address> addresses = List.of(address);
        when(addressRepository.findAllByPhoneNoAndDeletedFalse("9876543210")).thenReturn(addresses);
        boolean result = addressService.delete("9876543210");
        assertTrue(result);
        verify(addressRepository).findAllByPhoneNoAndDeletedFalse("9876543210");
        verify(addressRepository).saveAll(addresses);
        assertTrue(address.getDeleted());
    }

    @Test
    void testDeleteWithEmptyRecords() {
        when(addressRepository.findAllByPhoneNoAndDeletedFalse("000")).thenReturn(Collections.emptyList());
        boolean result = addressService.delete("000");
        assertTrue(result);
        verify(addressRepository).findAllByPhoneNoAndDeletedFalse("000");
        verify(addressRepository).saveAll(Collections.emptyList());
    }

    @Test
    void testFindAll() {
        List<Address> addressList = List.of(address);
        List<AddressDto> dtoList = List.of(addressDto);

        when(addressRepository.findAllByDeletedFalse()).thenReturn(addressList);
        when(modelMapper.map(any(), any(Type.class))).thenReturn(dtoList);
        List<AddressDto> result = addressService.findAll();
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(addressRepository).findAllByDeletedFalse();
    }

    @Test
    void testFindAllEmptyList() {
        when(addressRepository.findAllByDeletedFalse()).thenReturn(Collections.emptyList());
        when(modelMapper.map(any(), any(Type.class))).thenReturn(Collections.emptyList());
        List<AddressDto> result = addressService.findAll();
        assertTrue(result.isEmpty());
        verify(addressRepository).findAllByDeletedFalse();
    }
}