package com.ust.pos;

import com.ust.pos.dto.PriceDto;
import com.ust.pos.model.Price;
import com.ust.pos.model.PriceRepository;
import com.ust.pos.price.service.impl.PriceServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.*;
import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class PriceServiceTest {

    @InjectMocks
    private PriceServiceImpl priceService;
    @Mock
    private PriceRepository priceRepository;
    @Mock
    private ModelMapper modelMapper;
    @Test
    void saveTest() {
        PriceDto dto = new PriceDto();
        dto.setIdentifier("P001");
        Mockito.when(priceRepository.findByIdentifier("P001"))
                .thenReturn(null);
        Price price = new Price();
        Mockito.when(modelMapper.map(dto, Price.class))
                .thenReturn(price);
        Mockito.when(priceRepository.save(price))
                .thenReturn(price);
        PriceDto response = priceService.save(dto);
        Assertions.assertTrue(response.isSuccess());
        Assertions.assertEquals("Price created successfully", response.getMessage());
    }

    @Test
    void saveTestFailure() {
        PriceDto dto = new PriceDto();
        dto.setIdentifier("P001");
        Price existing = new Price();
        Mockito.when(priceRepository.findByIdentifier("P001"))
                .thenReturn(existing);
        PriceDto response = priceService.save(dto);
        Assertions.assertFalse(response.isSuccess());
        Assertions.assertNotNull(response.getMessage());
    }

    @Test
    void updateTest() {
        PriceDto dto = new PriceDto();
        dto.setIdentifier("P001");
        Price existing = new Price();
        existing.setIdentifier("P001");
        Mockito.when(priceRepository.findByIdentifier("P001"))
                .thenReturn(existing);
        Mockito.when(priceRepository.save(existing))
                .thenReturn(existing);
        PriceDto response = priceService.update(dto);
        Assertions.assertTrue(response.isSuccess());
        Assertions.assertEquals("Price updated successfully", response.getMessage());
    }

    @Test
    void updateTestFailure() {
        PriceDto dto = new PriceDto();
        dto.setIdentifier("P001");
        Mockito.when(priceRepository.findByIdentifier("P001"))
                .thenReturn(null);
        PriceDto response = priceService.update(dto);
        Assertions.assertFalse(response.isSuccess());
        Assertions.assertNotNull(response.getMessage());
    }

    @Test
    void deleteTest() {
        Mockito.doNothing()
                .when(priceRepository)
                .deleteByIdentifier("P001");
        boolean result = priceService.delete("P001");
        Assertions.assertTrue(result);
        Mockito.verify(priceRepository).deleteByIdentifier("P001");
    }

    @Test
    void findAllTest() {
        Price price = new Price();
        price.setIdentifier("P001");
        PriceDto dto = new PriceDto();
        dto.setIdentifier("P001");
        List<Price> prices = List.of(price);
        List<PriceDto> dtos = List.of(dto);
        Pageable pageable = PageRequest.of(0, 50, Sort.by(new ArrayList<>()));
        Page<Price> pricePage =
                new PageImpl<>(prices, pageable, prices.size());
        Mockito.when(priceRepository.findAll(pageable))
                .thenReturn(pricePage);
        Mockito.when(
                modelMapper.map(
                        Mockito.eq(prices),
                        Mockito.any(java.lang.reflect.Type.class))).thenReturn(dtos);
        List<PriceDto> response = priceService.findAll(pageable);
        Assertions.assertEquals(1, response.size());
        Assertions.assertEquals("P001", response.get(0).getIdentifier());
    }

    @Test
    void findByIdentifierTest() {
        Price price = new Price();
        price.setIdentifier("P001");
        PriceDto dto = new PriceDto();
        dto.setIdentifier("P001");
        Mockito.when(priceRepository.findByIdentifier("P001"))
                .thenReturn(price);
        Mockito.when(modelMapper.map(price, PriceDto.class))
                .thenReturn(dto);
        PriceDto response = priceService.findByIdentifier("P001");
        Assertions.assertEquals("P001", response.getIdentifier());
    }
}