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
import java.util.UUID;

@Service
public class WarehouseServiceImpl implements WarehouseService {
    @Autowired
    WarehouseRepository warehouseRepository;
    @Autowired
    ModelMapper modelMapper;

    @Override
    public WarehouseDto save(WarehouseDto warehouseDto) {
        String identifier = warehouseDto.getIdentifier();
        if (warehouseRepository.existsByIdentifier(identifier)) {
            warehouseDto.setMessage("Already exists");
            warehouseDto.setSuccess(false);
            return warehouseDto;
        }
        Warehouse warehouse = modelMapper.map(warehouseDto, Warehouse.class);
        warehouse.setIdentifier("WH-" + warehouse.getName() + "-" + UUID.randomUUID().toString().substring(0, 8));
        warehouseRepository.save(warehouse);
        return warehouseDto;
    }

    @Override
    public List<WarehouseDto> findAll() {
        Type listType = new TypeToken<List<WarehouseDto>>() {
        }.getType();
        return modelMapper.map(warehouseRepository.findAll(), listType);
    }

    @Override
    @Transactional
    public boolean delete(String identifier) {
        warehouseRepository.deleteByIdentifier(identifier);
        return true;
    }

    @Override
    public WarehouseDto findByIdentifier(String identifier) {
        Warehouse warehouse = warehouseRepository.findByIdentifier(identifier);
        return modelMapper.map(warehouse, WarehouseDto.class);
    }

    @Override
    public WarehouseDto update(WarehouseDto warehouseDto) {
        Warehouse warehouse = warehouseRepository.findByIdentifier(warehouseDto.getIdentifier());
        modelMapper.map(warehouseDto, warehouse);
        warehouse.setStatus(warehouseDto.isStatus());
        warehouseRepository.save(warehouse);
        return warehouseDto;
    }

    @Override
    public List<WarehouseDto> findAllActive() {
        Type listType = new TypeToken<List<WarehouseDto>>() {
        }.getType();
        return modelMapper.map(warehouseRepository.findByStatus(true), listType);
    }

    @Override
    public WarehouseDto updateStatus(String identifier, boolean status) {
        Warehouse warehouse = warehouseRepository.findByIdentifier(identifier);
        warehouse.setStatus(status);
        warehouseRepository.save(warehouse);
        return modelMapper.map(warehouse, WarehouseDto.class);
    }
}
