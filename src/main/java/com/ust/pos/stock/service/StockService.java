package com.ust.pos.stock.service;

import com.ust.pos.dto.StockDto;

import java.util.List;

public interface StockService {

    StockDto createStock(StockDto stockDto);

    StockDto updateStockQuantity(Long stockId, Integer quantity);

    StockDto getStock(Long productId, Long warehouseId);

    List<StockDto> getAllStocks();

    boolean deleteStock(Long stockId);

}