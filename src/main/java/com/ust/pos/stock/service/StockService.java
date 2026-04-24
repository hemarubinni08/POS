package com.ust.pos.stock.service;
import com.ust.pos.dto.StockDto;

import java.util.List;

public interface StockService {
    StockDto save(StockDto userDto);

    StockDto update(StockDto userDto);

    void delete(String identifier);

    List<StockDto> findAll();

    StockDto findByIdentifier(String identifier);
}
