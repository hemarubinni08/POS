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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

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
    void saveTest() {

        //request data
        StockDto stockDto = new StockDto();
        stockDto.setIdentifier("Admin");

        Mockito.when(stockRepository.findByIdentifier("Admin")).thenReturn(null);
        Stock stock = new Stock();

        Mockito.when(modelMapper.map(stockDto, Stock.class)).thenReturn(stock);
        Mockito.when(stockRepository.save(stock)).thenReturn(stock);
        StockDto response = stockService.save(stockDto);
        Assertions.assertEquals("Admin", response.getIdentifier());
        Assertions.assertEquals(true, response.isSuccess());
    }

    @Test
    void saveTestFailure() {
        //request data
        StockDto stockDto = new StockDto();
        stockDto.setIdentifier("Admin");
        Stock stock = new Stock();
        Mockito.when(stockRepository.findByIdentifier("Admin")).thenReturn(stock);
        StockDto response = stockService.save(stockDto);
        Assertions.assertEquals("Admin", response.getIdentifier());
        Assertions.assertNotNull(response.getMessage(), "Message cannot be null");
        Assertions.assertEquals(false, response.isSuccess());
    }

    @Test
    void findByIdentifierTest() {
        Stock stock = new Stock();
        stock.setIdentifier("Admin");
        StockDto stockDto = new StockDto();
        stockDto.setIdentifier("Admin");
        Mockito.when(stockRepository.findByIdentifier("Admin")).thenReturn(stock);
        Mockito.when(modelMapper.map(stock, StockDto.class)).thenReturn(stockDto);
        StockDto response = stockService.findByIdentifier("Admin");
        Assertions.assertEquals("Admin", response.getIdentifier());
    }

    @Test
    void updateTest() {
        StockDto stockDto = new StockDto();
        stockDto.setIdentifier("Admin");
        Stock existingStock = new Stock();
        existingStock.setIdentifier("Admin");
        Mockito.when(stockRepository.findByIdentifier("Admin"))
                .thenReturn(existingStock);
        Mockito.when(stockRepository.save(existingStock))
                .thenReturn(existingStock);
        StockDto response = stockService.update(stockDto);
        Assertions.assertTrue(response.isSuccess());
    }

    @Test
    void updateTestFailure() {
        StockDto stockDto = new StockDto();
        stockDto.setIdentifier("Admin");
        Mockito.when(stockRepository.findByIdentifier("Admin"))
                .thenReturn(null);
        StockDto response = stockService.update(stockDto);
        Assertions.assertFalse(response.isSuccess());
    }

    @Test
    void deleteTest() {
        Mockito.doNothing().when(stockRepository)
                .deleteByIdentifier("Admin");
        stockService.delete("Admin");
        Mockito.verify(stockRepository).deleteByIdentifier("Admin");
    }

    @Test
    void findAllTest() {

        // ARRANGE
        Stock stock = new Stock();
        stock.setIdentifier("Admin");

        StockDto stockDto = new StockDto();
        stockDto.setIdentifier("Admin");

        List<Stock> stocks = List.of(stock);
        List<StockDto> stockDtos = List.of(stockDto);

        Pageable pageable = PageRequest.of(0, 10);
        Page<Stock> stockPage =
                new PageImpl<>(stocks, pageable, stocks.size());

        Mockito.when(stockRepository.findAll(pageable))
                .thenReturn(stockPage);

        Mockito.when(modelMapper.map(
                Mockito.eq(stocks),
                Mockito.any(java.lang.reflect.Type.class)
        )).thenReturn(stockDtos);

        // ACT
        List<StockDto> response = stockService.findAll(pageable);

        // ASSERT
        Assertions.assertNotNull(response);
        Assertions.assertEquals(1, response.size());
        Assertions.assertEquals("Admin", response.get(0).getIdentifier());
    }

    @Test
    void toggleStatusSuccessTest() {

        // ARRANGE
        Stock stock = new Stock();
        stock.setIdentifier("Admin");
        stock.setStatus(false); // currently inactive
        StockDto stockDto = new StockDto();
        stockDto.setIdentifier("Admin");
        stockDto.setStatus(true); // after toggle should be active
        // MOCK
        // stock exists in DB
        Mockito.when(stockRepository.findByIdentifier("Admin")).thenReturn(stock);
        // after save, stock status is updated
        Mockito.when(stockRepository.save(stock)).thenReturn(stock);
        // mapper returns stockDto
        Mockito.when(modelMapper.map(stock, StockDto.class)).thenReturn(stockDto);
        // ACT
        StockDto response = stockService.toggleStatus("Admin", true);
        // ASSERT
        Assertions.assertEquals("Admin", response.getIdentifier());
        Assertions.assertTrue(response.isStatus()); // status should be true now
    }

    @Test
    void changeStockStatusFailureTest() {

        // ARRANGE - stock does NOT exist in DB
        StockDto stockDto = new StockDto();
        stockDto.setIdentifier("Admin");
        // MOCK
        // stock not found → returns null
        Mockito.when(stockRepository.findByIdentifier("Admin")).thenReturn(null);
        // ACT
        StockDto response = stockService.toggleStatus("Admin", true);
        // ASSERT
        // since stock is null, modelMapper.map(null, StockDto.class) returns null
        Assertions.assertNull(response);
        // verify save was NEVER called because stock was null
        Mockito.verify(stockRepository, Mockito.never()).save(Mockito.any());
    }
}