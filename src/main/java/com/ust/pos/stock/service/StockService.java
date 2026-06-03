package com.ust.pos.stock.service;

import com.ust.pos.dto.ShelfDto;
import com.ust.pos.dto.StockDto;
import com.ust.pos.dto.WsDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface StockService {
    StockDto findByIdentifier(String identifier);

    StockDto findById(Long id);

    StockDto save(StockDto stockDto);

    StockDto update(StockDto stockDto);

    void delete(Long id);

    WsDto<StockDto> findAll(Pageable pageable);
}
