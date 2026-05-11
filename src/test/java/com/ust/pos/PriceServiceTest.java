package com.ust.pos;

import com.ust.pos.dto.PriceDto;
import com.ust.pos.modell.Price;
import com.ust.pos.modell.PriceRepository;
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
        PriceDto priceDto = new PriceDto();
        priceDto.setIdentifier("PR1");

        Mockito.when(priceRepository.findByIdentifier("PR1"))
                .thenReturn(null);

        Price price = new Price();
        price.setIdentifier("PR1");

        Mockito.when(modelMapper.map(priceDto, Price.class))
                .thenReturn(price);
        Mockito.when(priceRepository.save(price))
                .thenReturn(price);

        PriceDto response = priceService.save(priceDto);

        Assertions.assertEquals("PR1", response.getIdentifier());
        Assertions.assertTrue(response.isSuccess());
        Assertions.assertNull(response.getMessage());
    }

    @Test
    void saveTestFailure() {
        PriceDto priceDto = new PriceDto();
        priceDto.setIdentifier("PR1");

        Price existingPrice = new Price();
        existingPrice.setIdentifier("PR1");

        Mockito.when(priceRepository.findByIdentifier("PR1"))
                .thenReturn(existingPrice);

        PriceDto response = priceService.save(priceDto);

        Assertions.assertEquals("PR1", response.getIdentifier());
        Assertions.assertFalse(response.isSuccess());
        Assertions.assertNotNull(response.getMessage());
    }

    @Test
    void findByIdentifierTest() {
        Price price = new Price();
        price.setIdentifier("PR1");

        PriceDto priceDto = new PriceDto();
        priceDto.setIdentifier("PR1");

        Mockito.when(priceRepository.findByIdentifier("PR1"))
                .thenReturn(price);
        Mockito.when(modelMapper.map(price, PriceDto.class))
                .thenReturn(priceDto);

        PriceDto response = priceService.findByIdentifier("PR1");

        Assertions.assertEquals("PR1", response.getIdentifier());
    }

    @Test
    void updateTest() {
        PriceDto priceDto = new PriceDto();
        priceDto.setIdentifier("PR1");

        Price existingPrice = new Price();
        existingPrice.setIdentifier("PR1");

        Mockito.when(priceRepository.findByIdentifier("PR1"))
                .thenReturn(existingPrice);
        Mockito.when(priceRepository.save(existingPrice))
                .thenReturn(existingPrice);

        PriceDto response = priceService.update(priceDto);

        Assertions.assertTrue(response.isSuccess());
        Assertions.assertNull(response.getMessage());
    }

    @Test
    void updateTestFailure() {
        PriceDto priceDto = new PriceDto();
        priceDto.setIdentifier("PR1");

        Mockito.when(priceRepository.findByIdentifier("PR1"))
                .thenReturn(null);

        PriceDto response = priceService.update(priceDto);

        Assertions.assertFalse(response.isSuccess());
        Assertions.assertNotNull(response.getMessage());
    }

    @Test
    void deleteTest() {
        Mockito.doNothing()
                .when(priceRepository)
                .deleteByIdentifier("PR1");

        priceService.delete("PR1");

        Mockito.verify(priceRepository)
                .deleteByIdentifier("PR1");
    }

    @Test
    void findAllTest() {
        Price price = new Price();
        price.setIdentifier("PR1");

        PriceDto priceDto = new PriceDto();
        priceDto.setIdentifier("PR1");

        List<Price> prices = List.of(price);
        List<PriceDto> priceDtos = List.of(priceDto);

        Pageable pageable = PageRequest.of(0, 20, Sort.by(new ArrayList<>()));
        Page<Price> pricePage =
                new PageImpl<>(prices, pageable, prices.size());

        Mockito.when(priceRepository.findAll(pageable))
                .thenReturn(pricePage);

        Mockito.when(modelMapper.map(
                Mockito.eq(prices),
                Mockito.any(java.lang.reflect.Type.class)
        )).thenReturn(priceDtos);

        List<PriceDto> response = priceService.findAll(pageable);

        Assertions.assertEquals(1, response.size());
    }
}