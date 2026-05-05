package com.ust.pos.unit.service;

import com.ust.pos.dto.UnitDto;

import java.util.List;

public interface UnitService {
    UnitDto save(UnitDto unitDto);

    UnitDto update(UnitDto unitDto);

    UnitDto findByIdentifier(String identifier);

    List<UnitDto> findAll();

    void delete(String identifier);

    void updateStatusOnly(String identifier, boolean status);
}