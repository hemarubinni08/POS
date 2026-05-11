package com.ust.pos;

import com.ust.pos.dto.StockDto;
import com.ust.pos.model.Stock;
import com.ust.pos.model.StockRepository;
import com.ust.pos.stock.service.impl.StockServiceImpl;
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
class StockServiceTest {

    @InjectMocks
    private StockServiceImpl stockService;
    @Mock
    private StockRepository stockRepository;
    @Mock
    private ModelMapper modelMapper;

    @Test
    void saveTest_Success() {
        StockDto dto = new StockDto();
        dto.setIdentifier("ST1");
        Mockito.when(stockRepository.findByIdentifier("ST1"))
                .thenReturn(null);
        Stock stock = new Stock();
        Mockito.when(modelMapper.map(dto, Stock.class))
                .thenReturn(stock);

        Mockito.when(stockRepository.save(stock))
                .thenReturn(stock);
        StockDto response = stockService.save(dto);
        Mockito.verify(modelMapper).map(dto, Stock.class);
        Assertions.assertTrue(response.isSuccess());
        Assertions.assertEquals("Stock created successfully", response.getMessage());
    }

    @Test
    void saveTest_Failure() {
        StockDto dto = new StockDto();
        dto.setIdentifier("ST1");
        Mockito.when(stockRepository.findByIdentifier("ST1"))
                .thenReturn(new Stock());
        StockDto response = stockService.save(dto);
        Assertions.assertFalse(response.isSuccess());
        Assertions.assertNotNull(response.getMessage());
    }

    @Test
    void findByIdentifier_Success() {
        Stock stock = new Stock();
        stock.setIdentifier("ST1");
        StockDto dto = new StockDto();
        dto.setIdentifier("ST1");
        Mockito.when(stockRepository.findByIdentifier("ST1"))
                .thenReturn(stock);
        Mockito.when(modelMapper.map(stock, StockDto.class))
                .thenReturn(dto);
        StockDto response = stockService.findByIdentifier("ST1");
        Assertions.assertEquals("ST1", response.getIdentifier());
    }

    @Test
    void findByIdentifier_Null() {
        Mockito.when(stockRepository.findByIdentifier("ST404"))
                .thenReturn(null);
        Mockito.when(modelMapper.map(null, StockDto.class))
                .thenReturn(null);
        StockDto response = stockService.findByIdentifier("ST404");
        Assertions.assertNull(response);
    }

    @Test
    void updateTest_Success() {
        StockDto dto = new StockDto();
        dto.setIdentifier("ST1");
        Stock stock = new Stock();
        stock.setIdentifier("ST1");
        Mockito.when(stockRepository.findByIdentifier("ST1"))
                .thenReturn(stock);
        Mockito.when(stockRepository.save(stock))
                .thenReturn(stock);
        StockDto response = stockService.update(dto);
        Mockito.verify(modelMapper).map(dto, stock);
        Assertions.assertTrue(response.isSuccess());
        Assertions.assertEquals("Stock updated successfully", response.getMessage());
    }

    @Test
    void updateTest_Failure() {
        StockDto dto = new StockDto();
        dto.setIdentifier("ST1");
        Mockito.when(stockRepository.findByIdentifier("ST1"))
                .thenReturn(null);
        StockDto response = stockService.update(dto);
        Assertions.assertFalse(response.isSuccess());
        Assertions.assertNotNull(response.getMessage());
    }

    @Test
    void deleteTest() {
        Mockito.doNothing().when(stockRepository)
                .deleteByIdentifier("ST1");
        boolean result = stockService.delete("ST1");
        Mockito.verify(stockRepository).deleteByIdentifier("ST1");
        Assertions.assertTrue(result);
    }

    @Test
    void findAll_WithData() {
        Stock stock = new Stock();
        stock.setIdentifier("ST1");
        StockDto dto = new StockDto();
        dto.setIdentifier("ST1");
        List<Stock> stocks = List.of(stock);
        List<StockDto> stockDtos = List.of(dto);
        Pageable pageable = PageRequest.of(0, 50, Sort.by(new ArrayList<>()));
        Page<Stock> stockPage =
                new PageImpl<>(stocks, pageable, stocks.size());
        Mockito.when(stockRepository.findAll(pageable))
                .thenReturn(stockPage);
        Mockito.when(
                modelMapper.map(
                        Mockito.eq(stocks),
                        Mockito.any(java.lang.reflect.Type.class))).thenReturn(stockDtos);
        List<StockDto> response = stockService.findAll(pageable);
        Assertions.assertEquals(1, response.size());
        Assertions.assertEquals("ST1", response.get(0).getIdentifier());
    }

    @Test
    void findAll_Empty() {
        Pageable pageable = PageRequest.of(0, 50, Sort.by(new ArrayList<>()));
        Page<Stock> stockPage =
                new PageImpl<>(List.of(), pageable, 0);
        Mockito.when(stockRepository.findAll(pageable))
                .thenReturn(stockPage);
        Mockito.when(
                modelMapper.map(
                        Mockito.eq(List.of()),
                        Mockito.any(java.lang.reflect.Type.class))).thenReturn(List.of());
        List<StockDto> response = stockService.findAll(pageable);
        Assertions.assertTrue(response.isEmpty());
    }

    @Test
    void toggleStatus_TrueToFalse() {
        Stock stock = new Stock();
        stock.setIdentifier("ST1");
        stock.setStatus(true);
        Stock updated = new Stock();
        updated.setIdentifier("ST1");
        updated.setStatus(false);
        StockDto dto = new StockDto();
        dto.setIdentifier("ST1");
        dto.setStatus(false);
        Mockito.when(stockRepository.findByIdentifier("ST1"))
                .thenReturn(stock);
        Mockito.when(stockRepository.save(stock))
                .thenReturn(updated);
        Mockito.when(modelMapper.map(updated, StockDto.class))
                .thenReturn(dto);
        StockDto response = stockService.toggleStatus("ST1");
        Assertions.assertFalse(response.isStatus());
    }

    @Test
    void toggleStatus_FalseToTrue() {
        Stock stock = new Stock();
        stock.setIdentifier("ST2");
        stock.setStatus(false);
        Stock updated = new Stock();
        updated.setIdentifier("ST2");
        updated.setStatus(true);
        StockDto dto = new StockDto();
        dto.setIdentifier("ST2");
        dto.setStatus(true);
        Mockito.when(stockRepository.findByIdentifier("ST2"))
                .thenReturn(stock);
        Mockito.when(stockRepository.save(stock))
                .thenReturn(updated);
        Mockito.when(modelMapper.map(updated, StockDto.class))
                .thenReturn(dto);
        StockDto response = stockService.toggleStatus("ST2");
        Assertions.assertTrue(response.isStatus());
    }

    @Test
    void toggleStatus_NullStock_ShouldThrowException() {
        Mockito.when(stockRepository.findByIdentifier("ST404"))
                .thenReturn(null);
        Assertions.assertThrows(
                NullPointerException.class,
                () -> stockService.toggleStatus("ST404"));
    }
}