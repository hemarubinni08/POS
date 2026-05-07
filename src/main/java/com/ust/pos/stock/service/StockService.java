package com.ust.pos.stock.service;

import com.ust.pos.dto.StockDto;

import java.util.List;

public interface StockService {
    StockDto save(StockDto stockDto);

    List<StockDto> findAll();

    boolean delete(String identifier);

    StockDto findByIdentifier(String identifier);

    StockDto update(StockDto stockDto);

    List<StockDto> findAllActive();

    StockDto changeStockStatus(String identifier, boolean status);
}
