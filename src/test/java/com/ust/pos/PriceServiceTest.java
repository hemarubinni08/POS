package com.ust.pos;

import com.ust.pos.price.service.impl.PriceServiceImpl;
import com.ust.pos.dto.PriceDto;
import com.ust.pos.model.Price;
import com.ust.pos.model.PriceRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.modelmapper.ModelMapper;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

@ExtendWith(MockitoExtension.class)
public class PriceServiceTest {

    @InjectMocks
    private PriceServiceImpl priceService;

    @Mock
    private PriceRepository priceRepository;

    @Mock
    private ModelMapper modelMapper;

    // ================= SAVE =================

    @Test
    void saveTest_Success() {

        PriceDto dto = new PriceDto();
        dto.setIdentifier("P1");

        Mockito.when(priceRepository.findByIdentifier("P1"))
                .thenReturn(null);

        Mockito.when(modelMapper.map(dto, Price.class))
                .thenReturn(new Price());

        PriceDto response = priceService.save(dto);

        Assertions.assertTrue(response.isSuccess());
    }

    @Test
    void saveTest_Failure_EmptyIdentifier() {

        PriceDto dto = new PriceDto();

        PriceDto response = priceService.save(dto);

        Assertions.assertFalse(response.isSuccess());
        Assertions.assertEquals("Identifier required", response.getMessage());
    }

    @Test
    void saveTest_Failure_AlreadyExists() {

        PriceDto dto = new PriceDto();
        dto.setIdentifier("P1");

        Mockito.when(priceRepository.findByIdentifier("P1"))
                .thenReturn(new Price());

        PriceDto response = priceService.save(dto);

        Assertions.assertFalse(response.isSuccess());
        Assertions.assertEquals("Price already exists", response.getMessage());
    }

    // ================= FIND BY IDENTIFIER =================

    @Test
    void findByIdentifierTest() {

        Price price = new Price();
        price.setIdentifier("P1");

        PriceDto mappedDto = new PriceDto();
        mappedDto.setIdentifier("P1");

        Mockito.when(priceRepository.findByIdentifier("P1"))
                .thenReturn(price);

        Mockito.when(modelMapper.map(price, PriceDto.class))
                .thenReturn(mappedDto);

        PriceDto response = priceService.findByIdentifier("P1");

        Assertions.assertTrue(response.isSuccess());
        Assertions.assertEquals("P1", response.getIdentifier());
    }

    @Test
    void findByIdentifier_NotFound() {

        Mockito.when(priceRepository.findByIdentifier("P1"))
                .thenReturn(null);

        PriceDto response = priceService.findByIdentifier("P1");

        Assertions.assertFalse(response.isSuccess());
        Assertions.assertEquals("Price not found", response.getMessage());
    }

    // ================= UPDATE =================

    @Test
    void updateTest_Success() {

        PriceDto dto = new PriceDto();
        dto.setIdentifier("P1");

        Price existing = new Price();
        existing.setIdentifier("P1");

        Mockito.when(priceRepository.findByIdentifier("P1"))
                .thenReturn(existing);

        Mockito.doNothing().when(modelMapper).map(dto, existing);

        Mockito.when(priceRepository.save(existing))
                .thenReturn(existing);

        PriceDto response = priceService.update(dto);

        Assertions.assertTrue(response.isSuccess());
    }

    @Test
    void updateTest_Failure_NotFound() {

        PriceDto dto = new PriceDto();
        dto.setIdentifier("P1");

        Mockito.when(priceRepository.findByIdentifier("P1"))
                .thenReturn(null);

        PriceDto response = priceService.update(dto);

        Assertions.assertFalse(response.isSuccess());
        Assertions.assertEquals("Price not found", response.getMessage());
    }

    // ================= DELETE =================

    @Test
    void deleteTest() {

        Mockito.doNothing()
                .when(priceRepository)
                .deleteByIdentifier("P1");

        priceService.delete("P1");

        Mockito.verify(priceRepository).deleteByIdentifier("P1");
    }

    // ================= FIND ALL =================

    @Test
    void findAllTest() {

        Price price = new Price();
        price.setIdentifier("P1");

        PriceDto dto = new PriceDto();
        dto.setIdentifier("P1");

        Mockito.when(priceRepository.findAll())
                .thenReturn(List.of(price));

        Mockito.when(modelMapper.map(price, PriceDto.class))
                .thenReturn(dto);

        List<PriceDto> result = priceService.findAll();

        Assertions.assertEquals(1, result.size());
        Assertions.assertEquals("P1", result.get(0).getIdentifier());
    }
}