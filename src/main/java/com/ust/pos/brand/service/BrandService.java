package com.ust.pos.brand.service;

import com.ust.pos.dto.BrandDto;

import java.util.List;

public interface BrandService {

    BrandDto save(BrandDto brandDto);
    BrandDto update(BrandDto brandDto);
    void delete(String identifier);
    List<BrandDto> findAll();
    BrandDto findByIdentifier(String identifier);
    BrandDto toggleStatus(String identifier);
    List<BrandDto> findActiveBrands();
}