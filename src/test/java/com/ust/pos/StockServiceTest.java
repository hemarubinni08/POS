package com.ust.pos;

import com.ust.pos.dto.StockDto;
import com.ust.pos.model.*;
import com.ust.pos.stock.service.impl.StockServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class StockServiceTest {

    @InjectMocks
    private StockServiceImpl stockService;

    @Mock
    private StockRepository stockRepository;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private WarehouseRepository warehouseRepository;

    @Mock
    private ModelMapper modelMapper;

    // ---------------- CREATE ----------------

    @Test
    void createStockSuccessTest() {
        StockDto dto = new StockDto();
        dto.setProductId(1L);
        dto.setWarehouseId(2L);

        Mockito.when(productRepository.existsById(1L)).thenReturn(true);
        Mockito.when(warehouseRepository.existsById(2L)).thenReturn(true);
        Mockito.when(stockRepository.existsByProductIdAndWarehouseId(1L, 2L)).thenReturn(false);

        Stock stock = new Stock();
        Mockito.when(modelMapper.map(dto, Stock.class)).thenReturn(stock);

        StockDto response = stockService.createStock(dto);

        Assertions.assertEquals(dto, response);
        Mockito.verify(stockRepository).save(stock);
    }

    @Test
    void createStockProductNotFoundTest() {
        StockDto dto = new StockDto();
        dto.setProductId(1L);

        Mockito.when(productRepository.existsById(1L)).thenReturn(false);

        StockDto response = stockService.createStock(dto);

        Assertions.assertFalse(response.isSuccess());
        Assertions.assertEquals("Product not found", response.getMessage());
    }

    @Test
    void createStockWarehouseNotFoundTest() {
        StockDto dto = new StockDto();
        dto.setProductId(1L);
        dto.setWarehouseId(2L);

        Mockito.when(productRepository.existsById(1L)).thenReturn(true);
        Mockito.when(warehouseRepository.existsById(2L)).thenReturn(false);

        StockDto response = stockService.createStock(dto);

        Assertions.assertFalse(response.isSuccess());
        Assertions.assertEquals("Warehouse not found", response.getMessage());
    }

    @Test
    void createStockAlreadyExistsTest() {
        StockDto dto = new StockDto();
        dto.setProductId(1L);
        dto.setWarehouseId(2L);

        Mockito.when(productRepository.existsById(1L)).thenReturn(true);
        Mockito.when(warehouseRepository.existsById(2L)).thenReturn(true);
        Mockito.when(stockRepository.existsByProductIdAndWarehouseId(1L, 2L)).thenReturn(true);

        StockDto response = stockService.createStock(dto);

        Assertions.assertFalse(response.isSuccess());
        Assertions.assertEquals("Stock already exists", response.getMessage());
    }

    @Test
    void updateStockQuantityTest() {
        Stock stock = new Stock();
        stock.setQuantity(5);

        Mockito.when(stockRepository.findById(1L)).thenReturn(Optional.of(stock));

        Mockito.doAnswer(invocation -> {
            Stock source = invocation.getArgument(0);
            StockDto target = invocation.getArgument(1);
            target.setQuantity(source.getQuantity());
            return null;
        }).when(modelMapper).map(Mockito.any(Stock.class), Mockito.any(StockDto.class));

        StockDto response = stockService.updateStockQuantity(1L, 10);

        Assertions.assertEquals(10, response.getQuantity());
        Mockito.verify(stockRepository).save(stock);
    }

    @Test
    void updateStockQuantityNotFoundTest() {
        Mockito.when(stockRepository.findById(1L)).thenReturn(Optional.empty());

        StockDto response = stockService.updateStockQuantity(1L, 10);

        Assertions.assertFalse(response.isSuccess());
        Assertions.assertEquals("Stock not found", response.getMessage());
    }

    @Test
    void getStockTest() {
        Stock stock = new Stock();
        stock.setProductId(1L);
        stock.setWarehouseId(2L);

        Product product = new Product();
        product.setIdentifier("Prod1");

        Warehouse warehouse = new Warehouse();
        warehouse.setName("WH1");

        Mockito.when(stockRepository.findByProductIdAndWarehouseId(1L, 2L))
                .thenReturn(Optional.of(stock));

        Mockito.when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        Mockito.when(warehouseRepository.findById(2L)).thenReturn(Optional.of(warehouse));

        Mockito.doAnswer(invocation -> {
            Stock source = invocation.getArgument(0);
            StockDto target = invocation.getArgument(1);
            target.setProductId(source.getProductId());
            target.setWarehouseId(source.getWarehouseId());
            return null;
        }).when(modelMapper).map(Mockito.any(Stock.class), Mockito.any(StockDto.class));

        StockDto response = stockService.getStock(1L, 2L);

        Assertions.assertEquals("Prod1", response.getProductName());
        Assertions.assertEquals("WH1", response.getWarehouseName());
    }

    @Test
    void getStockNotFoundTest() {
        Mockito.when(stockRepository.findByProductIdAndWarehouseId(1L, 2L))
                .thenReturn(Optional.empty());

        StockDto response = stockService.getStock(1L, 2L);

        Assertions.assertFalse(response.isSuccess());
        Assertions.assertEquals("Stock not found", response.getMessage());
    }

    @Test
    void getAllStocksTest() {
        Stock stock = new Stock();
        stock.setProductId(1L);
        stock.setWarehouseId(2L);

        Product product = new Product();
        product.setIdentifier("Prod1");

        Warehouse warehouse = new Warehouse();
        warehouse.setName("WH1");

        Mockito.when(stockRepository.findAll()).thenReturn(List.of(stock));
        Mockito.when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        Mockito.when(warehouseRepository.findById(2L)).thenReturn(Optional.of(warehouse));

        Mockito.when(modelMapper.map(stock, StockDto.class)).thenReturn(new StockDto());

        List<StockDto> response = stockService.getAllStocks();

        Assertions.assertEquals(1, response.size());
    }

    @Test
    void deleteStockSuccessTest() {
        Mockito.when(stockRepository.existsById(1L)).thenReturn(true);

        boolean result = stockService.deleteStock(1L);

        Assertions.assertTrue(result);
        Mockito.verify(stockRepository).deleteById(1L);
    }

    @Test
    void deleteStockFailureTest() {
        Mockito.when(stockRepository.existsById(1L)).thenReturn(false);

        boolean result = stockService.deleteStock(1L);

        Assertions.assertFalse(result);
    }
}