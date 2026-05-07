package com.ust.pos.stock.service.impl;

import com.ust.pos.dto.StockDto;
import com.ust.pos.model.Stock;
import com.ust.pos.model.StockRepository;
import com.ust.pos.stock.service.StockService;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class StockServiceImpl implements StockService {

    @Autowired
    private StockRepository stockRepository;

    @Autowired
    private ModelMapper modelMapper;

    public static final String STOCK_NOT_FOUND = "Stock not found";

    // STATE
    private String calculateState(Stock stock) {

        if (Boolean.FALSE.equals(stock.getStatus())) {
            return "DISCONTINUED";
        }

        if (stock.getAvailableQuantity() == null || stock.getAvailableQuantity() <= 0) {
            return "OUT_OF_STOCK";
        }

        if (stock.getReorderLevel() != null &&
                stock.getAvailableQuantity() <= stock.getReorderLevel()) {
            return "LOW_STOCK";
        }

        return "AVAILABLE";
    }

    // SAVE
    @Override
    public StockDto save(StockDto dto) {

        dto.setIdentifier(dto.getProductIdentifier() + "_" + dto.getWarehouseIdentifier());

        if (stockRepository.findByIdentifier(dto.getIdentifier()) != null) {
            dto.setSuccess(false);
            dto.setMessage("Product already exists in this warehouse");
            return dto;
        }

        Stock stock = modelMapper.map(dto, Stock.class);

        if (stock.getStatus() == null) {
            stock.setStatus(true);
        }

        stockRepository.save(stock);

        dto.setSuccess(true);
        dto.setMessage("Stock saved successfully");
        dto.setStockState(calculateState(stock));

        return dto;
    }

    // UPDATE
    @Override
    public StockDto update(StockDto dto) {

        dto.setIdentifier(dto.getProductIdentifier() + "_" + dto.getWarehouseIdentifier());

        Stock existing = stockRepository.findByIdentifier(dto.getIdentifier());

        if (existing == null) {
            dto.setSuccess(false);
            dto.setMessage(STOCK_NOT_FOUND);
            return dto;
        }

        existing.setAvailableQuantity(dto.getAvailableQuantity());
        existing.setReorderLevel(dto.getReorderLevel());
        existing.setStatus(dto.getStatus());

        stockRepository.save(existing);

        dto.setSuccess(true);
        dto.setMessage("Stock updated successfully");
        dto.setStockState(calculateState(existing));

        return dto;
    }

    // FIND BY ID
    @Override
    public StockDto findByIdentifier(String identifier) {

        Stock stock = stockRepository.findByIdentifier(identifier);

        if (stock == null) {
            StockDto dto = new StockDto();
            dto.setSuccess(false);
            dto.setMessage(STOCK_NOT_FOUND);
            return dto;
        }

        StockDto dto = modelMapper.map(stock, StockDto.class);
        dto.setStockState(calculateState(stock));

        return dto;
    }

    // FIND ALL
    @Override
    public List<StockDto> findAll() {

        List<Stock> list = stockRepository.findAll();
        List<StockDto> result = new ArrayList<>();

        for (Stock s : list) {

            StockDto dto = modelMapper.map(s, StockDto.class);
            dto.setStockState(calculateState(s));

            result.add(dto);
        }

        return result;
    }

    // DELETE
    @Override
    public void delete(String identifier) {
        stockRepository.deleteByIdentifier(identifier);
    }

    // TOGGLE
    @Override
    public StockDto toggleStatus(String identifier) {

        Stock stock = stockRepository.findByIdentifier(identifier);

        if (stock == null) {
            StockDto dto = new StockDto();
            dto.setSuccess(false);
            dto.setMessage(STOCK_NOT_FOUND);
            return dto;
        }

        stock.setStatus(!Boolean.TRUE.equals(stock.getStatus()));
        stockRepository.save(stock);

        StockDto dto = modelMapper.map(stock, StockDto.class);
        dto.setStockState(calculateState(stock));
        dto.setSuccess(true);
        dto.setMessage("Stock status updated successfully");

        return dto;
    }
}