package com.ust.pos.brand.service;

import com.ust.pos.dto.BrandDto;

import java.util.List;

public interface BrandService {
    BrandDto save(BrandDto userDto);

    BrandDto update(BrandDto userDto);

    void delete(String username);

    List<BrandDto> findAll();

    BrandDto findByIdentifier(String identifier);
}
