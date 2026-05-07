package com.ust.pos.stock.service;

import com.ust.pos.dto.StockDto;

import java.util.List;

public interface StockService {

    // Create new stock entry
    StockDto save(StockDto stockDto);

    // Update existing stock entry
    StockDto update(StockDto stockDto);

    // Get stock by identifier (product_warehouse key)
    StockDto findByIdentifier(String identifier);

    // Get all stocks
    List<StockDto> findAll();

    // Delete stock
    void delete(String identifier);

    // Toggle ACTIVE / INACTIVE status
    StockDto toggleStatus(String identifier);
}