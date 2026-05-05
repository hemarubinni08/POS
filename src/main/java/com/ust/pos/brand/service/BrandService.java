package com.ust.pos.brand.service;

import com.ust.pos.dto.BrandDto;

import java.util.List;

public interface BrandService {
    BrandDto save(BrandDto brandDto);

    void delete(String identifier);

    BrandDto update(BrandDto brandDto);

    List<BrandDto> findAll();

    BrandDto findByIdentifier(String identifier);

    void toggleStatus(String identifier);

}
