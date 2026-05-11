package com.ust.pos.stock.service.impl;

import com.ust.pos.dto.StockDto;
import com.ust.pos.model.Stock;
import com.ust.pos.model.StockRepository;
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

@Service
@Transactional
public class StockServiceImpl implements StockService {
    @Autowired
    private StockRepository stockRepository;

    @Autowired
    private StockRepository productRepository;

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

        Stock stock = modelMapper.map(stockDto, Stock.class);
        Stock savedStock = stockRepository.save(stock);

        StockDto response = modelMapper.map(savedStock, StockDto.class);
        response.setMessage("Successfully added the stock");
        response.setSuccess(true);

        return response;
    }

    public StockDto update(StockDto stockDto) {
        stockRepository.save(modelMapper.map(stockDto, Stock.class));
        stockDto.setMessage("Stock updated successfully");
        stockDto.setSuccess(true);

        return stockDto;
    }

    public StockDto findById(long id) {
        return modelMapper.map(stockRepository.findById(id), StockDto.class);
    }

    @Override
    public void delete(long id) {
        stockRepository.deleteById(id);
    }
}
