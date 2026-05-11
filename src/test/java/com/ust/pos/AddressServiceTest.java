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
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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

        addressDto = new AddressDto();
        addressDto.setPhoneNo("9876543210");
        addressDto.setAddressType("HOME");
    }

    @Test
    void findByIdentifier_shouldReturnAddressDto() {
        when(addressRepository.findByIdentifier("ADDR01")).thenReturn(address);
        when(modelMapper.map(address, AddressDto.class)).thenReturn(addressDto);

        AddressDto result = addressService.findByIdentifier("ADDR01");

        assertNotNull(result);
        assertEquals("9876543210", result.getPhoneNo());
    }

    @Test
    void findAllByPhoneNo_shouldReturnAddressList() {
        when(addressRepository.findAllByPhoneNo("9876543210"))
                .thenReturn(List.of(address));

        when(modelMapper.map(any(), any(Type.class)))
                .thenReturn(List.of(addressDto));

        List<AddressDto> result = addressService.findAllByPhoneNo("9876543210");

        assertEquals(1, result.size());
        assertEquals("HOME", result.get(0).getAddressType());
    }

    @Test
    void save_shouldPersistAddress() {
        when(modelMapper.map(addressDto, Address.class))
                .thenReturn(address);

        AddressDto result = addressService.save(addressDto);

        verify(addressRepository).save(address);
        assertNotNull(result);
    }

    @Test
    void update_shouldUpdateAddress() {
        when(addressRepository.findByPhoneNoAndAddressType(
                "9876543210", "HOME"))
                .thenReturn(address);

        AddressDto result = addressService.update(addressDto);

        verify(modelMapper).map(addressDto, address);
        verify(addressRepository).save(address);
        assertEquals("9876543210", result.getPhoneNo());
    }

    @Test
    void delete_shouldRemoveAddressesByPhoneNo() {
        when(addressRepository.findAllByPhoneNo("9876543210"))
                .thenReturn(List.of(address));

        boolean result = addressService.delete("9876543210");

        verify(addressRepository).deleteAll(anyList());
        assertTrue(result);
    }

    @Test
    void findAll_shouldReturnAllAddresses() {
        when(addressRepository.findAll())
                .thenReturn(List.of(address));

        when(modelMapper.map(any(), any(Type.class)))
                .thenReturn(List.of(addressDto));

        List<AddressDto> result = addressService.findAll();

        assertEquals(1, result.size());
        assertEquals("HOME", result.get(0).getAddressType());
    }

    @Test
    void findByIdentifier_shouldReturnNull_whenAddressNotFound() {

        when(addressRepository.findByIdentifier("ADDR404"))
                .thenReturn(null);
        when(modelMapper.map(null, AddressDto.class))
                .thenReturn(null);

        AddressDto result = addressService.findByIdentifier("ADDR404");

        assertNull(result);

        verify(addressRepository).findByIdentifier("ADDR404");
    }

    @Test
    void findAllByPhoneNo_shouldReturnEmptyList_whenNoAddresses() {

        when(addressRepository.findAllByPhoneNo("0000000000"))
                .thenReturn(List.of());

        when(modelMapper.map(eq(List.of()), any(Type.class)))
                .thenReturn(List.of());

        List<AddressDto> result =
                addressService.findAllByPhoneNo("0000000000");

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void save_shouldInvokeMapperAndRepository() {

        when(modelMapper.map(addressDto, Address.class))
                .thenReturn(address);

        addressService.save(addressDto);

        verify(modelMapper).map(addressDto, Address.class);
        verify(addressRepository).save(address);
    }

    @Test
    void update_shouldFetchAddressUsingPhoneAndType() {

        when(addressRepository.findByPhoneNoAndAddressType(
                "9876543210", "HOME"))
                .thenReturn(address);

        addressService.update(addressDto);

        verify(addressRepository)
                .findByPhoneNoAndAddressType("9876543210", "HOME");
    }

    @Test
    void update_shouldApplyDtoValuesToExistingAddress() {

        when(addressRepository.findByPhoneNoAndAddressType(
                "9876543210", "HOME"))
                .thenReturn(address);

        addressService.update(addressDto);

        verify(modelMapper).map(addressDto, address);
        verify(addressRepository).save(address);
    }

    @Test
    void delete_shouldHandleEmptyAddressListGracefully() {

        when(addressRepository.findAllByPhoneNo("1111111111"))
                .thenReturn(List.of());

        boolean result = addressService.delete("1111111111");

        verify(addressRepository).deleteAll(List.of());
        assertTrue(result);
    }

    @Test
    void findAll_shouldReturnEmptyList_whenRepositoryEmpty() {

        when(addressRepository.findAll())
                .thenReturn(List.of());

        when(modelMapper.map(eq(List.of()), any(Type.class)))
                .thenReturn(List.of());

        List<AddressDto> result = addressService.findAll();

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }
}