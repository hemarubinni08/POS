package com.ust.pos;

import com.ust.pos.dto.StockDto;
import com.ust.pos.modell.*;
import com.ust.pos.stock.service.impl.StockServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

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
    void findByIdentifierTest() {
        Stock stock = TestData.stock();

        Mockito.when(stockRepository.findByIdentifier("STK-PRD-WH"))
                .thenReturn(Optional.of(stock));

        StockDto response = stockService.findByIdentifier("STK-PRD-WH");

        Assertions.assertEquals("STK-PRD-WH", response.getIdentifier());
    }

    @Test
    void findByIdentifierNotFoundTest() {
        Mockito.when(stockRepository.findByIdentifier("X"))
                .thenReturn(Optional.empty());

        RuntimeException ex = Assertions.assertThrows(
                RuntimeException.class,
                () -> stockService.findByIdentifier("X")
        );

        Assertions.assertEquals("Stock not found", ex.getMessage());
    }

    @Test
    void findByIdTest() {
        Mockito.when(stockRepository.findById(1L))
                .thenReturn(Optional.of(TestData.stock()));

        StockDto response = stockService.findById(1L);

        Assertions.assertEquals(1L, response.getId());
        Assertions.assertEquals("STK-PRD-WH", response.getIdentifier());
    }

    @Test
    void findByIdNotFoundTest() {
        Mockito.when(stockRepository.findById(1L))
                .thenReturn(Optional.empty());

        RuntimeException ex = Assertions.assertThrows(
                RuntimeException.class,
                () -> stockService.findById(1L)
        );

        Assertions.assertEquals("Stock not found", ex.getMessage());
    }

    @Test
    void saveTest() {
        StockDto dto = new StockDto();
        dto.setProductId(1L);
        dto.setWarehouseId(1L);
        dto.setQuantity(20);
        dto.setMinimumStock(10);

        Mockito.when(productRepository.findById(1L))
                .thenReturn(Optional.of(TestData.product()));

        Mockito.when(warehouseRepository.findById(1L))
                .thenReturn(Optional.of(TestData.warehouse()));

        Mockito.when(stockRepository.findByProductIdAndWarehouseId(1L, 1L))
                .thenReturn(Optional.empty());

        Mockito.when(stockRepository.save(Mockito.any()))
                .thenAnswer(i -> i.getArgument(0));

        StockDto response = stockService.save(dto);

        Assertions.assertEquals("STK-PRD-WH", response.getIdentifier());
        Assertions.assertTrue(response.isStatus());
        Assertions.assertEquals(20, response.getQuantity());
    }

    @Test
    void saveDuplicateFailureTest() {
        Mockito.when(productRepository.findById(1L))
                .thenReturn(Optional.of(TestData.product()));
        Mockito.when(warehouseRepository.findById(1L))
                .thenReturn(Optional.of(TestData.warehouse()));
        Mockito.when(stockRepository.findByProductIdAndWarehouseId(1L, 1L))
                .thenReturn(Optional.of(new Stock()));

        StockDto dto = new StockDto();
        dto.setProductId(1L);
        dto.setWarehouseId(1L);

        RuntimeException ex = Assertions.assertThrows(
                RuntimeException.class,
                () -> stockService.save(dto)
        );

        Assertions.assertEquals(
                "Stock already exists for this Product and Warehouse",
                ex.getMessage()
        );
    }

    @Test
    void saveFailureNullProductOrWarehouse() {
        StockDto dto = new StockDto();

        RuntimeException ex = Assertions.assertThrows(
                RuntimeException.class,
                () -> stockService.save(dto)
        );

        Assertions.assertEquals(
                "Product and Warehouse are required",
                ex.getMessage()
        );
    }

    @Test
    void saveProductNotFound() {
        StockDto dto = new StockDto();
        dto.setProductId(1L);
        dto.setWarehouseId(1L);

        Mockito.when(productRepository.findById(1L))
                .thenReturn(Optional.empty());

        RuntimeException ex = Assertions.assertThrows(
                RuntimeException.class,
                () -> stockService.save(dto)
        );

        Assertions.assertEquals("Product not found", ex.getMessage());
    }

    @Test
    void saveWarehouseNotFound() {
        StockDto dto = new StockDto();
        dto.setProductId(1L);
        dto.setWarehouseId(1L);

        Mockito.when(productRepository.findById(1L))
                .thenReturn(Optional.of(TestData.product()));
        Mockito.when(warehouseRepository.findById(1L))
                .thenReturn(Optional.empty());

        RuntimeException ex = Assertions.assertThrows(
                RuntimeException.class,
                () -> stockService.save(dto)
        );

        Assertions.assertEquals("Warehouse not found", ex.getMessage());
    }

    @Test
    void updateTest() {
        Mockito.when(stockRepository.findById(1L))
                .thenReturn(Optional.of(TestData.stock()));
        Mockito.when(stockRepository.save(Mockito.any()))
                .thenAnswer(i -> i.getArgument(0));

        StockDto dto = new StockDto();
        dto.setId(1L);
        dto.setQuantity(5);
        dto.setMinimumStock(10);

        StockDto response = stockService.update(dto);

        Assertions.assertFalse(response.isStatus());
        Assertions.assertEquals(5, response.getQuantity());
    }

    @Test
    void updateStockNotFound() {
        Mockito.when(stockRepository.findById(99L))
                .thenReturn(Optional.empty());

        StockDto dto = new StockDto();
        dto.setId(99L);

        RuntimeException ex = Assertions.assertThrows(
                RuntimeException.class,
                () -> stockService.update(dto)
        );

        Assertions.assertEquals("Stock not found", ex.getMessage());
    }

    @Test
    void deleteTest() {
        Mockito.doNothing().when(stockRepository).deleteById(1L);
        stockService.delete(1L);
        Mockito.verify(stockRepository).deleteById(1L);
    }

    static class TestData {

        static Product product() {
            Product p = new Product();
            p.setId(1L);
            p.setIdentifier("PRD");
            return p;
        }

        static Warehouse warehouse() {
            Warehouse w = new Warehouse();
            w.setId(1L);
            w.setIdentifier("WH");
            return w;
        }

        static Stock stock() {
            Stock s = new Stock();
            s.setId(1L);
            s.setIdentifier("STK-PRD-WH");
            s.setProductId(1L);
            s.setWarehouseId(1L);
            s.setProductIdentifier("PRD");
            s.setWarehouseIdentifier("WH");
            s.setQuantity(20);
            s.setMinimumStock(10);
            s.setStatus(true);
            return s;
        }
    }
}