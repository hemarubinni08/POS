package com.ust.pos.stock.service;

import com.ust.pos.dto.StockDto;
import com.ust.pos.dto.WsDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface StockService {
    StockDto save(StockDto userDto);

    StockDto update(StockDto userDto);

    void delete(String username);

    WsDto<StockDto> findAll(Pageable pageable);

    StockDto findByIdentifier(String identifier);

    void toggleStatus(String identifier);
}
