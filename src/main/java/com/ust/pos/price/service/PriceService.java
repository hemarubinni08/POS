package com.ust.pos.price.service;

import com.ust.pos.dto.PriceDto;
import com.ust.pos.dto.ProductDto;
import com.ust.pos.dto.WsDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PriceService {
    PriceDto save(PriceDto priceDto);

    PriceDto update(PriceDto priceDto);

    void delete(String identifier);

    List<PriceDto> findAll();

    WsDto<PriceDto> findAll(Pageable pageable);

    PriceDto findByIdentifier(String identifier);
}
