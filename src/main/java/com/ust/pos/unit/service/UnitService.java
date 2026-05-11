package com.ust.pos.unit.service;

import com.ust.pos.dto.UnitDto;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Pageable;

import java.util.List;

@Transactional
public interface UnitService {

    UnitDto save(UnitDto unitDto);

    UnitDto update(UnitDto unitDto);

    boolean delete(String identifier);

    List<UnitDto> findAll(Pageable pageable);

    UnitDto findByIdentifier(String identifier);
}