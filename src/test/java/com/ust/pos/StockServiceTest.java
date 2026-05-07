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

import java.util.List;

@ExtendWith(MockitoExtension.class)
class StockServiceTest {

    @InjectMocks
    private StockServiceImpl stockService;

    @Mock
    private StockRepository stockRepository;

    @Mock
    private ModelMapper modelMapper;

    // ================= SAVE =================
    @Test
    void save_success() {

        StockDto dto = new StockDto();
        dto.setProductIdentifier("P1");
        dto.setWarehouseIdentifier("W1");
        dto.setAvailableQuantity(10);

        String identifier = "P1_W1";

        Mockito.when(stockRepository.findByIdentifier(identifier))
                .thenReturn(null);

        Mockito.when(modelMapper.map(dto, Stock.class))
                .thenReturn(new Stock());

        StockDto response = stockService.save(dto);

        Assertions.assertTrue(response.isSuccess());
        Assertions.assertEquals("Stock saved successfully", response.getMessage());
        Assertions.assertEquals(identifier, response.getIdentifier());
    }

    @Test
    void save_failure_alreadyExists() {

        StockDto dto = new StockDto();
        dto.setProductIdentifier("P1");
        dto.setWarehouseIdentifier("W1");

        Mockito.when(stockRepository.findByIdentifier("P1_W1"))
                .thenReturn(new Stock());

        StockDto response = stockService.save(dto);

        Assertions.assertFalse(response.isSuccess());
        Assertions.assertEquals("Product already exists in this warehouse", response.getMessage());
    }

    // ================= UPDATE =================
    @Test
    void update_success() {

        StockDto dto = new StockDto();
        dto.setProductIdentifier("P1");
        dto.setWarehouseIdentifier("W1");
        dto.setAvailableQuantity(5);
        dto.setReorderLevel(10);
        dto.setStatus(true);

        Stock existing = new Stock();

        Mockito.when(stockRepository.findByIdentifier("P1_W1"))
                .thenReturn(existing);

        Mockito.when(stockRepository.save(existing))
                .thenReturn(existing);

        StockDto response = stockService.update(dto);

        Assertions.assertTrue(response.isSuccess());
        Assertions.assertEquals("Stock updated successfully", response.getMessage());
    }

    @Test
    void update_failure_notFound() {

        StockDto dto = new StockDto();
        dto.setProductIdentifier("P1");
        dto.setWarehouseIdentifier("W1");

        Mockito.when(stockRepository.findByIdentifier("P1_W1"))
                .thenReturn(null);

        StockDto response = stockService.update(dto);

        Assertions.assertFalse(response.isSuccess());
        Assertions.assertEquals("Stock not found", response.getMessage());
    }

    // ================= FIND =================
    @Test
    void find_success() {

        Stock stock = new Stock();
        stock.setIdentifier("P1_W1");
        stock.setAvailableQuantity(10);

        StockDto mapped = new StockDto();
        mapped.setIdentifier("P1_W1");

        Mockito.when(stockRepository.findByIdentifier("P1_W1"))
                .thenReturn(stock);

        Mockito.when(modelMapper.map(stock, StockDto.class))
                .thenReturn(mapped);

        StockDto response = stockService.findByIdentifier("P1_W1");

        Assertions.assertEquals("P1_W1", response.getIdentifier());
    }

    @Test
    void find_notFound() {

        Mockito.when(stockRepository.findByIdentifier("P1_W1"))
                .thenReturn(null);

        StockDto response = stockService.findByIdentifier("P1_W1");

        Assertions.assertFalse(response.isSuccess());
        Assertions.assertEquals("Stock not found", response.getMessage());
    }

    // ================= FIND ALL =================
    @Test
    void findAllTest() {

        Stock stock = new Stock();
        stock.setIdentifier("P1_W1");

        StockDto dto = new StockDto();
        dto.setIdentifier("P1_W1");

        Mockito.when(stockRepository.findAll())
                .thenReturn(List.of(stock));

        Mockito.when(modelMapper.map(stock, StockDto.class))
                .thenReturn(dto);

        List<StockDto> result = stockService.findAll();

        Assertions.assertEquals(1, result.size());
    }

    // ================= DELETE =================
    @Test
    void deleteTest() {

        Mockito.doNothing().when(stockRepository)
                .deleteByIdentifier("P1_W1");

        stockService.delete("P1_W1");

        Mockito.verify(stockRepository).deleteByIdentifier("P1_W1");
    }

    // ================= TOGGLE =================
    @Test
    void toggle_success() {

        Stock stock = new Stock();
        stock.setIdentifier("P1_W1");
        stock.setStatus(true);

        Mockito.when(stockRepository.findByIdentifier("P1_W1"))
                .thenReturn(stock);

        Mockito.when(stockRepository.save(stock))
                .thenReturn(stock);

        Mockito.when(modelMapper.map(stock, StockDto.class))
                .thenReturn(new StockDto());

        StockDto response = stockService.toggleStatus("P1_W1");

        Assertions.assertTrue(response.isSuccess());
    }

    @Test
    void toggle_notFound() {

        Mockito.when(stockRepository.findByIdentifier("P1_W1"))
                .thenReturn(null);

        StockDto response = stockService.toggleStatus("P1_W1");

        Assertions.assertFalse(response.isSuccess());
        Assertions.assertEquals("Stock not found", response.getMessage());
    }
}