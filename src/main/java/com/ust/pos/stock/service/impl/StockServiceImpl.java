package com.ust.pos.stock.service.impl;

import com.ust.pos.dto.UserDto;
import com.ust.pos.model.*;
import com.ust.pos.dto.StockDto;
import com.ust.pos.stock.service.StockService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
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

        if (!productRepository.existsById(stockDto.getProductId())) {
            stockDto.setSuccess(false);
            stockDto.setMessage("Product not found");
            return stockDto;
        }

        if (!warehouseRepository.existsById(stockDto.getWarehouseId())) {
            stockDto.setSuccess(false);
            stockDto.setMessage("Warehouse not found");
            return stockDto;
        }

        boolean exists = stockRepository.existsByProductIdAndWarehouseId(stockDto.getProductId(), stockDto.getWarehouseId());

        if (exists) {
            stockDto.setSuccess(false);
            stockDto.setMessage("Stock already exists");
            return stockDto;
        }

        Stock stock = modelMapper.map(stockDto, Stock.class);
        stockRepository.save(stock);

        return stockDto;
    }

    @Override
    public StockDto updateStockQuantity(Long stockId, Integer quantity) {

        StockDto dto = new StockDto();

        stockRepository.findById(stockId).ifPresentOrElse(stock -> {
            stock.setQuantity(quantity);
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

        stockRepository.findByProductIdAndWarehouseId(productId, warehouseId)
                .ifPresentOrElse(stock -> {

                    modelMapper.map(stock, dto);

                    productRepository.findById(stock.getProductId())
                            .ifPresent(p -> dto.setProductName(p.getName()));

                    warehouseRepository.findById(stock.getWarehouseId())
                            .ifPresent(w -> dto.setWarehouseName(w.getName()));

                }, () -> {
                    dto.setSuccess(false);
                    dto.setMessage("Stock not found");
                });

        return dto;
    }

    @Override
    public List<StockDto> getAllStocks() {

        return stockRepository.findAll().stream().map(stock -> {

            StockDto dto = modelMapper.map(stock, StockDto.class);

            productRepository.findById(stock.getProductId())
                    .ifPresent(product -> dto.setProductName(product.getName()));

            warehouseRepository.findById(stock.getWarehouseId())
                    .ifPresent(warehouse -> dto.setWarehouseName(warehouse.getName()));

            return dto;

        }).toList();
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
