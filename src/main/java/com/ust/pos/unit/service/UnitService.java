package com.ust.pos.unit.service;

import com.ust.pos.dto.UnitDto;

import java.util.List;

public interface UnitService {
    UnitDto save(UnitDto unitDto);

    List<UnitDto> findAll();

    boolean delete(String identifier);

    UnitDto findByIdentifier(String identifier);

    UnitDto update(UnitDto unitDto);

    UnitDto updateStatus(String identifier, boolean status);

    List<UnitDto> findAllActive();
}
