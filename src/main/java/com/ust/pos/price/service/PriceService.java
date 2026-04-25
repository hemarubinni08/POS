package com.ust.pos.price.service;

import com.ust.pos.dto.PriceDto;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface PriceService {

    PriceDto save(PriceDto priceDto);

    PriceDto update(PriceDto priceDto);

    boolean delete(String identifier);

    List<PriceDto> findAll();

    PriceDto findByIdentifier(String identifier);
}