package com.ust.pos.brand.service;

import com.ust.pos.dto.BrandDto;
import com.ust.pos.dto.ShelfDto;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface BrandService {
    BrandDto save(BrandDto brandDto);

    BrandDto update(BrandDto brandDto);

    boolean delete(String identifier);

    List<BrandDto> findAll();

    BrandDto findByIdentifier(String identifier);

    BrandDto toggleStatus(String identifier);

    List<BrandDto> findIfTrue();
}
