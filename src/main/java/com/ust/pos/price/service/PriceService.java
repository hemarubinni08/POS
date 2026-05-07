package com.ust.pos.price.service;

import com.ust.pos.dto.PriceDto;

import java.util.List;

public interface PriceService {
    PriceDto save(PriceDto priceDto);

    List<PriceDto> findAll();

    boolean delete(String identifier);

    PriceDto findByIdentifier(String identifier);

    PriceDto update(PriceDto priceDto);

    List<PriceDto> findAllActive();

    PriceDto updateStatus(String identifier, boolean status);
}
