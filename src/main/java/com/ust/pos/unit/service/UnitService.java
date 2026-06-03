package com.ust.pos.unit.service;

import com.ust.pos.dto.PaginationResponseDto;
import com.ust.pos.dto.UnitDto;
import org.springframework.data.domain.Pageable;


public interface UnitService {
    UnitDto save(UnitDto userDto);

    UnitDto update(UnitDto userDto);

    void delete(String username);

    PaginationResponseDto<UnitDto> findAll(Pageable pageable);

    UnitDto findByIdentifier(String identifier);

    void toggleStatus(String identifier);

}