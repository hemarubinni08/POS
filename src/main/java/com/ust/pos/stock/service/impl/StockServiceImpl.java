package com.ust.pos.stock.service.impl;

import com.ust.pos.dto.StockDto;
import com.ust.pos.model.Stock;
import com.ust.pos.model.StockRepository;
import com.ust.pos.stock.service.StockService;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class StockServiceImpl implements StockService {
    @Autowired
    ModelMapper modelMapper;

    @Autowired
    StockRepository stockRepository;

    @Override
    public StockDto findByIdentifier(String identifier) {
        return modelMapper.map(stockRepository.findByIdentifier(identifier), StockDto.class);
    }

    @Override
    public StockDto save(StockDto stockDto) {
        if (stockRepository.findByIdentifier(stockDto.getIdentifier()) != null) {
            stockDto.setSuccess(false);
            stockDto.setMessage("Stock with name -> " + stockDto.getIdentifier() + "Already Exists.");
            return stockDto;
        }
        Stock stock = modelMapper.map(stockDto, Stock.class);
        stockRepository.save(stock);
        return stockDto;
    }

    @Override
    public void delete(String identifier) {
        stockRepository.deleteByIdentifier(identifier);
    }

    @Override
    public StockDto update(StockDto stockDto) {
        Stock existingStock = stockRepository.findByIdentifier(stockDto.getIdentifier());
        return null;

    }

    @Override
    public List<StockDto> findAll() {
        return List.of();
    }
}
