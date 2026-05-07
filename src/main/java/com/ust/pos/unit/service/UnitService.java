package com.ust.pos.unit.service;

import com.ust.pos.dto.UnitDto;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface UnitService {

    UnitDto save(UnitDto unitDto);

    UnitDto update(UnitDto unitDto);

    boolean delete(String identifier);

    List<UnitDto> findAll();

    UnitDto findByIdentifier(String identifier);

    UnitDto toggleStatus(String identifier);

    List<UnitDto> findIfTrue();
}