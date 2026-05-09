package com.ust.pos.stock.service.impl;

import com.ust.pos.model.*;
import com.ust.pos.dto.StockDto;
import com.ust.pos.stock.service.StockService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.util.List;


@Service
public class StockServiceImpl implements StockService {

    @Autowired
    private StockRepository stockRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private WarehouseRepository warehouseRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public StockDto createStock(StockDto stockDto) {

        var product = productRepository.findById(stockDto.getProductId()).orElseThrow(() -> new RuntimeException("Product not found"));

        var warehouse = warehouseRepository.findById(stockDto.getWarehouseId()).orElseThrow(() -> new RuntimeException("Warehouse not found"));

        boolean exists = stockRepository.existsByProductIdAndWarehouseId(stockDto.getProductId(), stockDto.getWarehouseId());

        if (exists) {
            stockDto.setSuccess(false);
            stockDto.setMessage("Stock already exists");
            return stockDto;
        }

        stockDto.setProductName(product.getIdentifier());
        stockDto.setWarehouseName(warehouse.getName());

        Stock stock = modelMapper.map(stockDto, Stock.class);

        stockRepository.save(stock);

        return stockDto;
    }

    @Override
    public StockDto updateStockQuantity(Long stockId, Integer quantity) {

        StockDto dto = new StockDto();

        stockRepository.findById(stockId).ifPresentOrElse(stock -> {
            stock.setQuantity(quantity);
            productRepository.findById(stock.getProductId()).ifPresent(product -> stock.setProductName(product.getIdentifier()));
            warehouseRepository.findById(stock.getWarehouseId()).ifPresent(warehouse -> stock.setWarehouseName(warehouse.getName()));
            stockRepository.save(stock);
            modelMapper.map(stock, dto);
        }, () -> {
            dto.setSuccess(false);
            dto.setMessage("Stock not found");
        });

        return dto;
    }

    @Override
    public StockDto getStock(Long productId, Long warehouseId) {

        StockDto dto = new StockDto();

        stockRepository.findByProductIdAndWarehouseId(productId, warehouseId).ifPresentOrElse(stock -> {

            modelMapper.map(stock, dto);

            productRepository.findById(stock.getProductId()).ifPresent(p -> dto.setProductName(p.getIdentifier()));

            warehouseRepository.findById(stock.getWarehouseId()).ifPresent(w -> dto.setWarehouseName(w.getName()));

        }, () -> {
            dto.setSuccess(false);
            dto.setMessage("Stock not found");
        });

        return dto;
    }

    @Override
    public List<StockDto> findAll(Pageable pageable) {
        Type listType = new TypeToken<List<StockDto>>() {
        }.getType();
        Page<Stock> stockPage = stockRepository.findAll(pageable);
        return modelMapper.map(stockPage.getContent(), listType);
    }

    @Override
    public boolean deleteStock(Long stockId) {
        if (!stockRepository.existsById(stockId)) {
            return false;
        }
        stockRepository.deleteById(stockId);
        return true;
    }
}
