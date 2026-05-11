package com.ust.pos.brand.service;

import com.ust.pos.dto.BrandDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BrandService {
    BrandDto save(BrandDto userDto);

    BrandDto update(BrandDto userDto);

    void delete(String username);

    void toggleStatus(String identifier);

    List<BrandDto> findAll(Pageable pageable);

    BrandDto findByIdentifier(String identifier);
}
