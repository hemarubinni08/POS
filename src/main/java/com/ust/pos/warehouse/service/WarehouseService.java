package com.ust.pos.warehouse.service;

import com.ust.pos.dto.WarehouseDto;

import java.util.List;

public interface WarehouseService {

    WarehouseDto save(WarehouseDto warehouseDto);

    WarehouseDto update(WarehouseDto warehouseDto);

    WarehouseDto findByIdentifier(String identifier);

    List<WarehouseDto> findAll();

    boolean delete(String identifier);

    void toggleStatus(String identifier);

    List<WarehouseDto> findIfTrue();
}