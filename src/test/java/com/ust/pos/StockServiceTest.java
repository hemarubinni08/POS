package com.ust.pos;

import com.ust.pos.dto.StockDto;
import com.ust.pos.model.Product;
import com.ust.pos.model.ProductRepository;
import com.ust.pos.model.Stock;
import com.ust.pos.model.StockRepository;
import com.ust.pos.model.Warehouse;
import com.ust.pos.model.WarehouseRepository;
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

import java.lang.reflect.Type;
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

    @Test
    void createStockSuccessTest() {

        StockDto dto = new StockDto();
        dto.setProductId(1L);
        dto.setWarehouseId(2L);

        Product product = new Product();
        product.setIdentifier("PROD1");

        Warehouse warehouse = new Warehouse();
        warehouse.setName("WH1");

        Stock stock = new Stock();

        Mockito.when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        Mockito.when(warehouseRepository.findById(2L)).thenReturn(Optional.of(warehouse));

        Mockito.when(stockRepository.existsByProductIdAndWarehouseId(1L, 2L)).thenReturn(false);

        Mockito.when(modelMapper.map(dto, Stock.class)).thenReturn(stock);

        StockDto response = stockService.createStock(dto);

        Assertions.assertEquals("PROD1", response.getProductName());
        Assertions.assertEquals("WH1", response.getWarehouseName());

        Mockito.verify(stockRepository).save(stock);
    }

    @Test
    void createStockProductNotFoundTest() {

        StockDto dto = new StockDto();
        dto.setProductId(1L);
        dto.setWarehouseId(2L);

        Mockito.when(productRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException exception = Assertions.assertThrows(RuntimeException.class, () -> stockService.createStock(dto));

        Assertions.assertEquals("Product not found", exception.getMessage());
    }

    @Test
    void createStockWarehouseNotFoundTest() {

        StockDto dto = new StockDto();
        dto.setProductId(1L);
        dto.setWarehouseId(2L);

        Product product = new Product();

        Mockito.when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        Mockito.when(warehouseRepository.findById(2L)).thenReturn(Optional.empty());

        RuntimeException exception = Assertions.assertThrows(RuntimeException.class, () -> stockService.createStock(dto));

        Assertions.assertEquals("Warehouse not found", exception.getMessage());
    }

    @Test
    void createStockAlreadyExistsTest() {

        StockDto dto = new StockDto();
        dto.setProductId(1L);
        dto.setWarehouseId(2L);

        Product product = new Product();
        Warehouse warehouse = new Warehouse();

        Mockito.when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        Mockito.when(warehouseRepository.findById(2L)).thenReturn(Optional.of(warehouse));

        Mockito.when(stockRepository.existsByProductIdAndWarehouseId(1L, 2L)).thenReturn(true);

        StockDto response = stockService.createStock(dto);

        Assertions.assertFalse(response.isSuccess());
        Assertions.assertEquals("Stock already exists", response.getMessage());
    }

    @Test
    void updateStockQuantityTest() {

        Stock stock = new Stock();
        stock.setProductId(1L);
        stock.setWarehouseId(2L);
        stock.setQuantity(5);

        Product product = new Product();
        product.setIdentifier("PROD1");

        Warehouse warehouse = new Warehouse();
        warehouse.setName("WH1");

        Mockito.when(stockRepository.findById(1L)).thenReturn(Optional.of(stock));

        Mockito.when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        Mockito.when(warehouseRepository.findById(2L)).thenReturn(Optional.of(warehouse));

        Mockito.doAnswer(invocation -> {
            Stock source = invocation.getArgument(0);
            StockDto target = invocation.getArgument(1);

            target.setQuantity(source.getQuantity());
            target.setProductName(source.getProductName());
            target.setWarehouseName(source.getWarehouseName());

            return null;
        }).when(modelMapper).map(Mockito.any(Stock.class), Mockito.any(StockDto.class));

        StockDto response = stockService.updateStockQuantity(1L, 10);

        Assertions.assertEquals(10, response.getQuantity());
        Assertions.assertEquals("PROD1", response.getProductName());
        Assertions.assertEquals("WH1", response.getWarehouseName());

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
        product.setIdentifier("PROD1");

        Warehouse warehouse = new Warehouse();
        warehouse.setName("WH1");

        Mockito.when(stockRepository.findByProductIdAndWarehouseId(1L, 2L)).thenReturn(Optional.of(stock));

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

        Assertions.assertEquals("PROD1", response.getProductName());
        Assertions.assertEquals("WH1", response.getWarehouseName());
    }

    @Test
    void getStockNotFoundTest() {

        Mockito.when(stockRepository.findByProductIdAndWarehouseId(1L, 2L)).thenReturn(Optional.empty());

        StockDto response = stockService.getStock(1L, 2L);

        Assertions.assertFalse(response.isSuccess());
        Assertions.assertEquals("Stock not found", response.getMessage());
    }

    @Test
    void findAllTest() {

        Stock stock = new Stock();
        stock.setProductId(1L);

        StockDto dto = new StockDto();
        dto.setProductId(1L);

        List<Stock> stockList = List.of(stock);
        List<StockDto> dtoList = List.of(dto);

        Page<Stock> stockPage = new PageImpl<>(stockList);

        Mockito.when(stockRepository.findAll(Mockito.any(PageRequest.class))).thenReturn(stockPage);

        Mockito.when(modelMapper.map(Mockito.eq(stockList), Mockito.any(Type.class))).thenReturn(dtoList);

        List<StockDto> response = stockService.findAll(PageRequest.of(0, 10));

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