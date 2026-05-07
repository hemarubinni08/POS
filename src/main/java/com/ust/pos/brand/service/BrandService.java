package com.ust.pos.brand.service;

import com.ust.pos.dto.BrandDto;

import java.util.List;

public interface BrandService {
    BrandDto save(BrandDto brandDto);

    List<BrandDto> findAll();

    boolean delete(String identifier);

    BrandDto findByIdentifier(String identifier);

    BrandDto update(BrandDto brandDto);

    List<BrandDto> findAllActive();

    BrandDto changeBrandStatus(String identifier, boolean status);
}
