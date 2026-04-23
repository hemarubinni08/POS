package com.ust.pos.price.service;

import com.ust.pos.dto.PriceDto;

import java.util.List;

public interface PriceService {

    PriceDto createPrice(PriceDto priceDto);

    PriceDto updatePrice(PriceDto priceDto);

    List<PriceDto> getAllPrices();

    PriceDto getPriceById(Long id);

    boolean deletePrice(Long id);
}