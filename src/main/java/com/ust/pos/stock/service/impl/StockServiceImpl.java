package com.ust.pos.stock.service.impl;

import com.ust.pos.dto.StockDto;
import com.ust.pos.model.Product;
import com.ust.pos.model.ProductRepository;
import com.ust.pos.model.Stock;
import com.ust.pos.model.StockRepository;
import com.ust.pos.model.Warehouse;
import com.ust.pos.model.WarehouseRepository;
import com.ust.pos.stock.service.StockService;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Optional;

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
    public StockDto findByIdentifier(String identifier) {

        Stock stock = stockRepository.findByIdentifier(identifier);

        if (stock == null) {
            StockDto dto = new StockDto();
            dto.setMessage("Stock not found - " + identifier);
            dto.setSuccess(false);
            return dto;
        }

        return modelMapper.map(stock, StockDto.class);
    }

    @Override
    public StockDto save(StockDto stockDto) {

        String productId = stockDto.getProductIdentifier();
        String warehouseId = stockDto.getWarehouseIdentifier();

        Optional<Product> product = productRepository.findByIdentifier(productId);
        if (product.isEmpty()) {
            stockDto.setMessage("Invalid Product: " + productId);
            stockDto.setSuccess(false);
            return stockDto;
        }

        Optional<Warehouse> warehouse = warehouseRepository.findByIdentifier(warehouseId);
        if (warehouse.isEmpty()) {
            stockDto.setMessage("Invalid Warehouse: " + warehouseId);
            stockDto.setSuccess(false);
            return stockDto;
        }

        Stock existing = stockRepository.findByProductIdentifierAndWarehouseIdentifier(productId, warehouseId);

        if (existing != null) {
            stockDto.setMessage("Stock already exists for this Product and Warehouse");
            stockDto.setSuccess(false);
            return stockDto;
        }

        Stock stock = modelMapper.map(stockDto, Stock.class);
        stockRepository.save(stock);

        return stockDto;
    }

    @Override
    public StockDto update(StockDto stockDto) {

        String identifier = stockDto.getIdentifier();

        Stock existing = stockRepository.findByIdentifier(identifier);

        if (existing == null) {
            stockDto.setMessage("Stock not found - " + identifier);
            stockDto.setSuccess(false);
            return stockDto;
        }

        modelMapper.map(stockDto, existing);
        stockRepository.save(existing);

        return stockDto;
    }

    @Override
    @Transactional
    public void delete(String identifier) {
        stockRepository.deleteByIdentifier(identifier);
    }

    @Override
    public List<StockDto> findAll() {
        Type listType = new TypeToken<List<StockDto>>() {}.getType();
        return modelMapper.map(stockRepository.findAll(), listType);
    }
}