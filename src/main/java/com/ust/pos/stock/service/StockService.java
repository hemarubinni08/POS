package com.ust.pos.stock.service;

import com.ust.pos.dto.PaginationResponseDto;
import com.ust.pos.dto.StockDto;
import org.springframework.data.domain.Pageable;

public interface StockService {
    StockDto save(StockDto userDto);

    StockDto update(StockDto userDto);

    void delete(String username);

    PaginationResponseDto<StockDto> findAll(Pageable pageable);

    StockDto findByIdentifier(String identifier);
}
