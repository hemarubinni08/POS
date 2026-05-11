package com.ust.pos.warehouse.service;

import com.ust.pos.dto.WarehouseDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface WarehouseService {
    WarehouseDto save(WarehouseDto warehouseDto);

    List<WarehouseDto> findAll(Pageable pageable);

    boolean delete(String identifier);

    WarehouseDto findByIdentifier(String identifier);

    WarehouseDto update(WarehouseDto warehouseDto);

    List<WarehouseDto> findAllActive();

    WarehouseDto updateStatus(String identifier, boolean status);
}
