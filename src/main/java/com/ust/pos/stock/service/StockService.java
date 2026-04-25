package com.ust.pos.stock.service;

import com.ust.pos.dto.StockDto;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface StockService {

    StockDto save(StockDto stockDto);

    StockDto update(StockDto stockDto);

    boolean delete(String identifier);

    List<StockDto> findAll();

    StockDto findByIdentifier(String identifier);
}