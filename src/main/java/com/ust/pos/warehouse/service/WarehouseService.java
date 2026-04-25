package com.ust.pos.warehouse.service;

import com.ust.pos.dto.WarehouseDto;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface WarehouseService {

    WarehouseDto save(WarehouseDto warehouseDto);

    WarehouseDto update(WarehouseDto warehouseDto);

    boolean delete(String identifier);

    List<WarehouseDto> findAll();

    WarehouseDto findByIdentifier(String identifier);
}