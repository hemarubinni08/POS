package com.ust.pos;

import com.ust.pos.dto.PriceDto;
import com.ust.pos.model.Price;
import com.ust.pos.model.PriceRepository;
import com.ust.pos.model.Product;
import com.ust.pos.model.ProductRepository;
import com.ust.pos.price.service.impl.PriceServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class PriceServiceTest {

    @InjectMocks
    private PriceServiceImpl priceService;

    @Mock
    private PriceRepository priceRepository;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ModelMapper modelMapper;

    @Test
    void createPriceTest() {
        PriceDto dto = new PriceDto();
        dto.setProductId(1L);

        Product product = new Product();

        Mockito.when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        Mockito.when(priceRepository.existsByProductId(1L)).thenReturn(false);
        Mockito.when(modelMapper.map(dto, Price.class)).thenReturn(new Price());

        PriceDto response = priceService.createPrice(dto);

        Assertions.assertEquals(1L, response.getProductId());
        Mockito.verify(priceRepository).save(Mockito.any(Price.class));
    }

    @Test
    void createPriceProductNotFoundTest() {
        PriceDto dto = new PriceDto();
        dto.setProductId(1L);

        Mockito.when(productRepository.findById(1L)).thenReturn(Optional.empty());

        Assertions.assertThrows(ResponseStatusException.class, () ->
                priceService.createPrice(dto));
    }

    @Test
    void createPriceAlreadyExistsTest() {
        PriceDto dto = new PriceDto();
        dto.setProductId(1L);

        Mockito.when(productRepository.findById(1L)).thenReturn(Optional.of(new Product()));
        Mockito.when(priceRepository.existsByProductId(1L)).thenReturn(true);

        Assertions.assertThrows(ResponseStatusException.class, () ->
                priceService.createPrice(dto));
    }

    @Test
    void updatePriceTest() {
        PriceDto dto = new PriceDto();
        dto.setId(1L);

        Price price = new Price();

        Mockito.when(priceRepository.findById(1L)).thenReturn(Optional.of(price));

        PriceDto response = priceService.updatePrice(dto);

        Assertions.assertEquals(1L, response.getId());
        Mockito.verify(priceRepository).save(price);
    }

    @Test
    void updatePriceNotFoundTest() {
        PriceDto dto = new PriceDto();
        dto.setId(1L);

        Mockito.when(priceRepository.findById(1L)).thenReturn(Optional.empty());

        Assertions.assertThrows(RuntimeException.class, () ->
                priceService.updatePrice(dto));
    }

    @Test
    void getAllPricesTest() {
        Price price = new Price();
        price.setProductId(1L);

        PriceDto mappedDto = new PriceDto();

        Product product = new Product();
        product.setIdentifier("PROD1");

        Mockito.when(priceRepository.findAll()).thenReturn(List.of(price));
        Mockito.when(modelMapper.map(price, PriceDto.class)).thenReturn(mappedDto);
        Mockito.when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        List<PriceDto> response = priceService.getAllPrices();

        Assertions.assertEquals(1, response.size());
        Assertions.assertEquals("PROD1", response.get(0).getProductName());
    }

    @Test
    void deletePriceTest() {
        Mockito.when(priceRepository.existsById(1L)).thenReturn(true);
        Mockito.doNothing().when(priceRepository).deleteById(1L);

        boolean response = priceService.deletePrice(1L);

        Assertions.assertTrue(response);
        Mockito.verify(priceRepository).deleteById(1L);
    }

    @Test
    void deletePriceNotFoundTest() {
        Mockito.when(priceRepository.existsById(1L)).thenReturn(false);

        boolean response = priceService.deletePrice(1L);

        Assertions.assertFalse(response);
    }

    @Test
    void getPriceByIdTest() {
        Price price = new Price();

        Mockito.when(priceRepository.findById(1L)).thenReturn(Optional.of(price));

        PriceDto response = priceService.getPriceById(1L);

        Assertions.assertTrue(response.isSuccess());
    }

    @Test
    void getPriceByIdNotFoundTest() {
        Mockito.when(priceRepository.findById(1L)).thenReturn(Optional.empty());

        PriceDto response = priceService.getPriceById(1L);

        Assertions.assertFalse(response.isSuccess());
        Assertions.assertEquals("Price not found", response.getMessage());
    }
}