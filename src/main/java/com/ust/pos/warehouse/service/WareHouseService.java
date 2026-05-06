package com.ust.pos.warehouse.service;

import com.ust.pos.dto.WareHouseDto;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface WareHouseService {
    WareHouseDto save(WareHouseDto wareHouseDto);

    WareHouseDto update(WareHouseDto wareHouseDto);

    boolean delete(String identifier);

    List<WareHouseDto> findAll();

    WareHouseDto findByIdentifier(String identifier);

    List<WareHouseDto> findIfTrue();

    WareHouseDto toggleStatus(String identifier);
}
