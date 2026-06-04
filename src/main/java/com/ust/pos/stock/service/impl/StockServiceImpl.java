package com.ust.pos.stock.service.impl;

import com.ust.pos.dto.PriceDto;
import com.ust.pos.dto.StockDto;
import com.ust.pos.model.*;
import com.ust.pos.stock.service.StockService;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class StockServiceImpl implements StockService {
    @Autowired
    private StockRepository stockRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ModelMapper modelMapper;

    public List<StockDto> findAll(Pageable pageable) {
        Type listType = new TypeToken<List<StockDto>>() {
        }.getType();
        if (pageable == null) {
            return modelMapper.map(stockRepository.findAll(), listType);
        }
        Page<Stock> stockPage = stockRepository.findAll(pageable);
        return modelMapper.map(stockPage.getContent(), listType);
    }

    public StockDto save(StockDto stockDto) {
        boolean exists = productRepository.existsByIdentifier(stockDto.getProduct());

        if (!exists) {
            StockDto response = new StockDto();
            response.setMessage("Product not found");
            response.setSuccess(false);
            return response;
        }

        stockDto.setIdentifier(stockDto.getProduct() + stockDto.getWarehouse());
        Stock existingStock = stockRepository.findByIdentifier(stockDto.getIdentifier());
        if (existingStock != null) {
            StockDto response = new StockDto();
            response.setMessage("Already exists");
            response.setSuccess(false);
            return response;
        }

        Stock stock = modelMapper.map(stockDto, Stock.class);
        Stock savedStock = stockRepository.save(stock);

        StockDto response = modelMapper.map(savedStock, StockDto.class);
        response.setMessage("Successfully added the stock");
        response.setSuccess(true);

        return response;
    }

    public StockDto update(StockDto stockDto) {
        Optional<Stock> stockOptional = stockRepository.findById(stockDto.getId());

        if (stockOptional.isEmpty()) {
            stockDto.setMessage("Email - " + stockDto.getIdentifier() + " not found");
            stockDto.setSuccess(false);
            return stockDto;
        }

        Stock existingStock = stockOptional.get();
        String stockName = stockDto.getIdentifier();

        boolean isStocknameChanged = !stockName.equalsIgnoreCase(existingStock.getIdentifier());

        if (isStocknameChanged && productRepository.findByIdentifier(stockName) != null) {
            stockDto.setMessage("Product " + stockName + " already exists");
            stockDto.setSuccess(false);
            return stockDto;
        }

        stockDto.setMessage("Stock successfully edited");
        stockDto.setSuccess(true);
        modelMapper.map(stockDto, existingStock);
        stockRepository.save(existingStock);

        return stockDto;
    }

    public StockDto findById(long id) {
        return modelMapper.map(stockRepository.findById(id), StockDto.class);
    }

    public StockDto findByIdentifier(String identifier) {
        return modelMapper.map(stockRepository.findByIdentifier(identifier), StockDto.class);
    }

    @Override
    public void delete(String identifier) {
        stockRepository.deleteByIdentifier(identifier);
    }
}
