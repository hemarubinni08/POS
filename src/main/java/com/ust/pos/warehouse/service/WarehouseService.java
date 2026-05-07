package com.ust.pos.warehouse.service;

import com.ust.pos.dto.WarehouseDto;

import java.util.List;

public interface WarehouseService {
    WarehouseDto save(WarehouseDto warehouseDto);

    List<WarehouseDto> findAll();

    boolean delete(String identifier);

    WarehouseDto findByIdentifier(String identifier);

    WarehouseDto update(WarehouseDto warehouseDto);

    List<WarehouseDto> findAllActive();

    WarehouseDto updateStatus(String identifier, boolean status);
}
