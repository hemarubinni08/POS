package com.ust.pos.price.service;

import com.ust.pos.dto.PriceDto;

import java.util.List;

public interface PriceService {

    PriceDto save(PriceDto priceDto);
    PriceDto update(PriceDto priceDto);
    PriceDto findByIdentifier(String identifier);
    List<PriceDto> findAll();
    void delete(String identifier);
}