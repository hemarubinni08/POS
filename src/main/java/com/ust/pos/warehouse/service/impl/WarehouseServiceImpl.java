package com.ust.pos.warehouse.service.impl;

import com.ust.pos.dto.WarehouseDto;
import com.ust.pos.model.Warehouse;
import com.ust.pos.model.WarehouseRepository;
import com.ust.pos.warehouse.service.WarehouseService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class WarehouseServiceImpl implements WarehouseService {

    private final WarehouseRepository warehouseRepository;

    public WarehouseServiceImpl(WarehouseRepository warehouseRepository) {
        this.warehouseRepository = warehouseRepository;
    }

    @Override
    public WarehouseDto createWarehouse(WarehouseDto warehouseDto) {

        if (warehouseRepository.existsByName(warehouseDto.getName())) {
            throw new RuntimeException("Warehouse with this name already exists");
        }

        Warehouse warehouse = new Warehouse();
        warehouse.setIdentifier(warehouseDto.getIdentifier());
        warehouse.setName(warehouseDto.getName());
        warehouse.setLocation(warehouseDto.getLocation());
        warehouse.setStatus("ACTIVE");

        Warehouse saved = warehouseRepository.save(warehouse);
        return mapToDto(saved);
    }

    @Override
    public WarehouseDto updateWarehouse(Long id, WarehouseDto warehouseDto) {

        Warehouse warehouse = warehouseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Warehouse not found"));

        warehouse.setName(warehouseDto.getName());
        warehouse.setLocation(warehouseDto.getLocation());
        warehouse.setStatus(warehouseDto.getStatus());

        return mapToDto(warehouse);
    }

    @Override
    public WarehouseDto getWarehouseById(Long id) {

        Warehouse warehouse = warehouseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Warehouse not found"));

        return mapToDto(warehouse);
    }

    @Override
    public List<WarehouseDto> getAllWarehouses() {

        return warehouseRepository.findAll()
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public void deactivateWarehouse(Long id) {

        Warehouse warehouse = warehouseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Warehouse not found"));

        warehouse.setStatus("INACTIVE");
    }


    private WarehouseDto mapToDto(Warehouse warehouse) {

        WarehouseDto dto = new WarehouseDto();
        dto.setId(warehouse.getId());
        dto.setIdentifier(warehouse.getIdentifier());
        dto.setName(warehouse.getName());
        dto.setLocation(warehouse.getLocation());
        dto.setStatus(warehouse.getStatus());

        return dto;
    }
}