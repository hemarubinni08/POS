package com.ust.pos.warehouse.service.impl;

import com.ust.pos.dto.WarehouseDto;
import com.ust.pos.model.Warehouse;
import com.ust.pos.model.WarehouseRepository;
import com.ust.pos.warehouse.service.WarehouseService;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class WarehouseServiceImpl implements WarehouseService {

    @Autowired
    private WarehouseRepository warehouseRepository;

    @Autowired
    private ModelMapper modelMapper;

    //  SAVE 
    @Override
    public WarehouseDto save(WarehouseDto warehouseDto) {

        if (warehouseDto.getIdentifier() == null || warehouseDto.getIdentifier().isEmpty()) {
            warehouseDto.setSuccess(false);
            warehouseDto.setMessage("Identifier required");
            return warehouseDto;
        }

        Warehouse existing = warehouseRepository.findByIdentifier(warehouseDto.getIdentifier());

        if (existing != null) {
            warehouseDto.setSuccess(false);
            warehouseDto.setMessage("Warehouse already exists");
            return warehouseDto;
        }

        Warehouse warehouse = modelMapper.map(warehouseDto, Warehouse.class);

        warehouseRepository.save(warehouse);

        warehouseDto.setSuccess(true);
        return warehouseDto;
    }

    //  UPDATE 
    @Override
    public WarehouseDto update(WarehouseDto warehouseDto) {

        Warehouse existing = warehouseRepository.findByIdentifier(warehouseDto.getIdentifier());

        if (existing == null) {
            warehouseDto.setSuccess(false);
            warehouseDto.setMessage("Warehouse not found");
            return warehouseDto;
        }

        modelMapper.map(warehouseDto, existing);

        warehouseRepository.save(existing);

        warehouseDto.setSuccess(true);
        return warehouseDto;
    }

    //  FIND BY ID 
    @Override
    public WarehouseDto findByIdentifier(String identifier) {

        Warehouse warehouse = warehouseRepository.findByIdentifier(identifier);

        if (warehouse == null) {
            WarehouseDto dto = new WarehouseDto();
            dto.setSuccess(false);
            dto.setMessage("Warehouse not found");
            return dto;
        }

        WarehouseDto dto = modelMapper.map(warehouse, WarehouseDto.class);

        dto.setSuccess(true);
        return dto;
    }

    //  FIND ALL 
    @Override
    public List<WarehouseDto> findAll() {

        List<Warehouse> warehouses = warehouseRepository.findAll();
        List<WarehouseDto> result = new ArrayList<>();

        for (Warehouse warehouse : warehouses) {

            WarehouseDto dto = modelMapper.map(warehouse, WarehouseDto.class);

            result.add(dto);
        }

        return result;
    }

    //  DELETE 
    @Override
    @Transactional
    public void delete(String identifier) {
        warehouseRepository.deleteByIdentifier(identifier);
    }
}