package com.ust.pos.warehouse.service;

import com.ust.pos.dto.WarehouseDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface WarehouseService {
    WarehouseDto save(WarehouseDto userDto);

    WarehouseDto update(WarehouseDto userDto);

    void delete(String username);

    List<WarehouseDto> findAll(Pageable pageable);

    WarehouseDto findByIdentifier(String identifier);
}
