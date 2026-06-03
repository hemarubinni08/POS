package com.ust.pos;

import com.ust.pos.dto.PriceDto;
import com.ust.pos.model.Price;
import com.ust.pos.model.PriceRepository;
import com.ust.pos.price.service.impl.PriceServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.modelmapper.ModelMapper;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PriceServiceTest {

    @Mock
    private PriceRepository priceRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private PriceServiceImpl priceService;

    private Price price;
    private PriceDto priceDto;

    @BeforeEach
    void setUp() {
        price = new Price();
        price.setIdentifier("P001");

        priceDto = new PriceDto();
        priceDto.setIdentifier("P001");
    }

    @Test
    void testFindByIdentifier_Found() {
        when(priceRepository.findByIdentifier("P001")).thenReturn(price);
        when(modelMapper.map(price, PriceDto.class)).thenReturn(priceDto);

        PriceDto result = priceService.findByIdentifier("P001");

        assertNotNull(result);
        assertEquals("P001", result.getIdentifier());
    }

    @Test
    void testFindByIdentifier_NotFound() {
        when(priceRepository.findByIdentifier("P001")).thenReturn(null);

        PriceDto result = priceService.findByIdentifier("P001");

        assertNull(result);
    }

    @Test
    void testSave_AlreadyExists() {
        when(priceRepository.findByIdentifier("P001")).thenReturn(price);

        PriceDto result = priceService.save(priceDto);

        assertFalse(result.isSuccess());
        assertTrue(result.getMessage().contains("already exists"));
        verify(priceRepository, never()).save(any());
    }

    @Test
    void testSave_Success() {
        when(priceRepository.findByIdentifier("P001")).thenReturn(null);
        when(modelMapper.map(priceDto, Price.class)).thenReturn(price);

        PriceDto result = priceService.save(priceDto);

        assertNotNull(result);
        verify(priceRepository).save(price);
    }

    @Test
    void testUpdate_NotFound() {
        when(priceRepository.findByIdentifier("P001")).thenReturn(null);

        PriceDto result = priceService.update(priceDto);

        assertFalse(result.isSuccess());
        assertTrue(result.getMessage().contains("not found"));
        verify(priceRepository, never()).save(any());
    }

    @Test
    void testUpdate_Success() {
        when(priceRepository.findByIdentifier("P001")).thenReturn(price);

        PriceDto result = priceService.update(priceDto);

        verify(modelMapper).map(priceDto, price);
        verify(priceRepository).save(price);
        assertNotNull(result);
    }

    @Test
    void testDelete() {
        doNothing().when(priceRepository).deleteByIdentifier("P001");

        priceService.delete("P001");

        verify(priceRepository).deleteByIdentifier("P001");
    }

    @Test
    void testFindAll() {
        Pageable pageable = PageRequest.of(0, 10);

        List<Price> priceList = Arrays.asList(price);
    }
}