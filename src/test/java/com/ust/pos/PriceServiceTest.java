package com.ust.pos;

import com.ust.pos.dto.PriceDto;
import com.ust.pos.dto.ProductDto;
import com.ust.pos.model.Price;
import com.ust.pos.model.PriceRepository;
import com.ust.pos.price.service.impl.PriceServiceImpl;
import com.ust.pos.product.service.ProductService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.lang.reflect.Type;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PriceServiceTest {

    @InjectMocks
    private PriceServiceImpl priceService;

    @Mock
    private PriceRepository priceRepository;

    @Mock
    private ProductService productService;

    @Mock
    private ModelMapper modelMapper;

    @Test
    void save_success() {
        PriceDto dto = new PriceDto();
        dto.setIdentifier("P1");
        dto.setProductId("PR1");
        Price priceEntity = new Price();
        ProductDto productDto = new ProductDto();
        productDto.setProductName("Phone");
        when(priceRepository.findByIdentifier("P1")).thenReturn(null);
        when(productService.findByIdentifier("PR1")).thenReturn(productDto);
        when(modelMapper.map(any(PriceDto.class), eq(Price.class))).thenReturn(priceEntity);
        when(priceRepository.save(any(Price.class))).thenReturn(priceEntity);
        PriceDto response = priceService.save(dto);
        Assertions.assertTrue(response.isSuccess());
        verify(priceRepository).save(any(Price.class));
    }

    @Test
    void save_failure_duplicate() {
        PriceDto dto = new PriceDto();
        dto.setIdentifier("P1");
        when(priceRepository.findByIdentifier("P1")).thenReturn(new Price());
        PriceDto response = priceService.save(dto);
        Assertions.assertFalse(response.isSuccess());
        Assertions.assertEquals("Price already exists", response.getMessage());
        verify(priceRepository, never()).save(any());
    }

    @Test
    void save_failure_empty_identifier() {
        PriceDto dto = new PriceDto();
        dto.setIdentifier("");
        PriceDto response = priceService.save(dto);
        Assertions.assertFalse(response.isSuccess());
        Assertions.assertEquals("Identifier required", response.getMessage());
    }

    @Test
    void find_success() {
        Price price = new Price();
        PriceDto dto = new PriceDto();
        when(priceRepository.findByIdentifier("P1")).thenReturn(price);
        when(modelMapper.map(price, PriceDto.class)).thenReturn(dto);
        PriceDto response = priceService.findByIdentifier("P1");
        Assertions.assertTrue(response.isSuccess());
    }

    @Test
    void find_not_found() {
        when(priceRepository.findByIdentifier("P1")).thenReturn(null);
        PriceDto response = priceService.findByIdentifier("P1");
        Assertions.assertFalse(response.isSuccess());
        Assertions.assertEquals("Price not found", response.getMessage());
    }

    @Test
    void update_success() {
        PriceDto dto = new PriceDto();
        dto.setIdentifier("P1");
        dto.setProductId("PR1");
        Price existing = new Price();
        ProductDto productDto = new ProductDto();
        productDto.setProductName("Phone");
        when(priceRepository.findByIdentifier("P1")).thenReturn(existing);
        when(productService.findByIdentifier("PR1")).thenReturn(productDto);
        doNothing().when(modelMapper).map(any(PriceDto.class), any(Price.class));
        when(priceRepository.save(existing)).thenReturn(existing);
        PriceDto response = priceService.update(dto);
        Assertions.assertTrue(response.isSuccess());
        verify(priceRepository).save(existing);
    }

    @Test
    void update_failure_not_found() {
        PriceDto dto = new PriceDto();
        dto.setIdentifier("P1");
        when(priceRepository.findByIdentifier("P1")).thenReturn(null);
        PriceDto response = priceService.update(dto);
        Assertions.assertFalse(response.isSuccess());
        Assertions.assertEquals("Price not found", response.getMessage());
    }

    @Test
    void find_all_test() {
        List<Price> list = List.of(new Price());
        Page<Price> page = new PageImpl<>(list);
        List<PriceDto> mappedList = List.of(new PriceDto());
        when(priceRepository.findAll(any(Pageable.class))).thenReturn(page);
        when(modelMapper.map(eq(list), any(Type.class))).thenReturn(mappedList);
        List<PriceDto> result = priceService.findAll(Pageable.unpaged());
        Assertions.assertNotNull(result);
        Assertions.assertEquals(1, result.size());
    }

    @Test
    void delete_test() {
        priceService.delete("P1");
        verify(priceRepository).deleteByIdentifier("P1");
    }
}