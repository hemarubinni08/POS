package com.ust.pos.warehouse.service;

import com.ust.pos.dto.WarehouseDto;

import java.util.List;

public interface WarehouseService {

    WarehouseDto createWarehouse(WarehouseDto warehouseDto);

    WarehouseDto updateWarehouse(Long id, WarehouseDto warehouseDto);

    WarehouseDto getWarehouseById(Long id);

    List<WarehouseDto> getAllWarehouses();

    void deactivateWarehouse(Long id);
}
