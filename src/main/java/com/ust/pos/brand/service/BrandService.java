package com.ust.pos.brand.service;

import com.ust.pos.dto.BrandDto;
import com.ust.pos.dto.PaginationResponseDto;
import org.springframework.data.domain.Pageable;


public interface BrandService {
    BrandDto save(BrandDto userDto);

    BrandDto update(BrandDto userDto);

    void delete(String username);

    PaginationResponseDto<BrandDto> findAll(Pageable pageable);

    BrandDto findByIdentifier(String identifier);

    void toggleStatus(String identifier);

}