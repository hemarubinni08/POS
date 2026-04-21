package com.ust.pos.stock.service;

import com.ust.pos.dto.StockDto;

import java.util.List;

public interface StockService {

    StockDto findByIdentifier(String identifier);

    StockDto save(StockDto stockDto);

    void delete(String identifier);

    StockDto update(StockDto stockDto);

    List<StockDto> findAll();
}
