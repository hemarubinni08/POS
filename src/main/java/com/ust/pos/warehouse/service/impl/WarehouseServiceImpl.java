package com.ust.pos.warehouse.service.impl;

import com.ust.pos.dto.WarehouseDto;
import com.ust.pos.model.Warehouse;
import com.ust.pos.model.WarehouseRepository;
import com.ust.pos.warehouse.service.WarehouseService;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Optional;

@Service
public class WarehouseServiceImpl implements WarehouseService {

    @Autowired
    private WarehouseRepository warehouseRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public WarehouseDto findByIdentifier(String identifier) {

        Optional<Warehouse> warehouse = warehouseRepository.findByIdentifier(identifier);

        if (warehouse.isEmpty()) {
            WarehouseDto dto = new WarehouseDto();
            dto.setMessage("Warehouse not found for identifier - " + identifier);
            dto.setSuccess(false);
            return dto;
        }

        return modelMapper.map(warehouse.get(), WarehouseDto.class);
    }

    @Override
    public WarehouseDto save(WarehouseDto warehouseDto) {

        String identifier = warehouseDto.getIdentifier();

        if (identifier == null || identifier.isEmpty()) {
            warehouseDto.setMessage("Identifier is required");
            warehouseDto.setSuccess(false);
            return warehouseDto;
        }

        Optional<Warehouse> existing = warehouseRepository.findByIdentifier(identifier);

        if (existing.isPresent()) {
            warehouseDto.setMessage("Warehouse already exists for identifier - " + identifier);
            warehouseDto.setSuccess(false);
            return warehouseDto;
        }

        Warehouse warehouse = modelMapper.map(warehouseDto, Warehouse.class);
        warehouseRepository.save(warehouse);

        return warehouseDto;
    }

    @Override
    public WarehouseDto update(WarehouseDto warehouseDto) {

        String identifier = warehouseDto.getIdentifier();

        if (identifier == null || identifier.isEmpty()) {
            warehouseDto.setMessage("Invalid identifier");
            warehouseDto.setSuccess(false);
            return warehouseDto;
        }

        Optional<Warehouse> existing = warehouseRepository.findByIdentifier(identifier);

        if (existing.isEmpty()) {
            warehouseDto.setMessage("Warehouse not found for identifier - " + identifier);
            warehouseDto.setSuccess(false);
            return warehouseDto;
        }

        Warehouse warehouse = existing.get();
        modelMapper.map(warehouseDto, warehouse);
        warehouseRepository.save(warehouse);

        return warehouseDto;
    }

    @Override
    @Transactional
    public void delete(String identifier) {
        warehouseRepository.deleteByIdentifier(identifier);
    }

    @Override
    public List<WarehouseDto> findAll() {
        Type listType = new TypeToken<List<WarehouseDto>>() {}.getType();
        return modelMapper.map(warehouseRepository.findAll(), listType);
    }
}