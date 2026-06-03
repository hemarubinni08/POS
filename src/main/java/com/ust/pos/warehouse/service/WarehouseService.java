package com.ust.pos.warehouse.service;

import com.ust.pos.dto.PaginationResponseDto;
import com.ust.pos.dto.WarehouseDto;
import org.springframework.data.domain.Pageable;

public interface WarehouseService {
    WarehouseDto save(WarehouseDto userDto);

    WarehouseDto update(WarehouseDto userDto);

    void delete(String username);

    PaginationResponseDto<WarehouseDto> findAll(Pageable pageable);

    WarehouseDto findByIdentifier(String identifier);
}
