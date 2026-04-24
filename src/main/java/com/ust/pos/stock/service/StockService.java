package com.ust.pos.stock.service;

import com.ust.pos.dto.StockDto;

import java.util.List;

public interface StockService {
    StockDto findByIdentifier(String identifier);
    StockDto save(StockDto stockDto);
    StockDto update(StockDto stockDto);
    void delete(String identifier);
    List<StockDto> findAll();
}