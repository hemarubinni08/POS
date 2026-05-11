package com.ust.pos.unit.service;

import com.ust.pos.dto.UnitDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UnitService {
    UnitDto save(UnitDto userDto);

    UnitDto update(UnitDto userDto);

    void delete(String username);

    List<UnitDto> findAll(Pageable pageable);

    UnitDto findByIdentifier(String identifier);

    void toggleStatus(String identifier);

}