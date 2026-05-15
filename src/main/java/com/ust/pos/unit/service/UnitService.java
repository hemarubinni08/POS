package com.ust.pos.unit.service;

import com.ust.pos.dto.UnitDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UnitService {
    UnitDto save(UnitDto brandDto);

    UnitDto update(UnitDto brandDto);

    void delete(String identifier);

    List<UnitDto> findAll(Pageable pageable);

    UnitDto findByIdentifier(String identifier);

    void updateStatus(String identifier, boolean status);

    List<UnitDto> findAllActive();
}
