package com.ust.pos.stock.service.impl;

import com.ust.pos.dto.StockDto;
import com.ust.pos.model.Stock;
import com.ust.pos.model.StockRepository;
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
    private ModelMapper modelMapper;

    @Override
    public StockDto findByIdentifier(String identifier) {
        return modelMapper.map(
                stockRepository.findByIdentifier(identifier),
                StockDto.class
        );
    }

    @Override
    public StockDto save(StockDto stockDto) {

        String identifier = stockDto.getIdentifier();
        Stock existingStock = stockRepository.findByIdentifier(identifier);

        if (existingStock != null) {
            stockDto.setMessage("Stock with identifier - " + identifier + " already exists");
            stockDto.setSuccess(false);
            return stockDto;
        }

        Stock stock = modelMapper.map(stockDto, Stock.class);
        stockRepository.save(stock);

        stockDto.setMessage("Stock created successfully");
        stockDto.setSuccess(true);
        return stockDto;
    }

    @Override
    public StockDto update(StockDto stockDto) {

        String identifier = stockDto.getIdentifier();
        Stock existingStock = stockRepository.findByIdentifier(identifier);

        if (existingStock == null) {
            stockDto.setMessage("Stock with identifier - " + identifier + " not found");
            stockDto.setSuccess(false);
            return stockDto;
        }

        modelMapper.map(stockDto, existingStock);
        stockRepository.save(existingStock);

        stockDto.setMessage("Stock updated successfully");
        stockDto.setSuccess(true);
        return stockDto;
    }

    @Override
    public boolean delete(String identifier) {
        stockRepository.deleteByIdentifier(identifier);
        return true;
    }

    @Override
    public List<StockDto> findAll() {

        Type listType = new TypeToken<List<StockDto>>() {}.getType();

        return modelMapper.map(
                stockRepository.findAll(),
                listType);
    }
}