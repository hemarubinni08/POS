package com.ust.pos.price.service;

import com.ust.pos.dto.PaginationResponseDto;
import com.ust.pos.dto.PriceDto;
import org.springframework.data.domain.Pageable;

public interface PriceService {
    PriceDto save(PriceDto userDto);

    PriceDto update(PriceDto userDto);

    void delete(String username);

    PaginationResponseDto<PriceDto> findAll(Pageable pageable);

    PriceDto findByIdentifier(String identifier);

    PriceDto findByProductAndPriceType(String product, String priceType);
}