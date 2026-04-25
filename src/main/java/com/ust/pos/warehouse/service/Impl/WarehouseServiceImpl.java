package com.ust.pos.warehouse.service.Impl;

import com.ust.pos.dto.WarehouseDto;
import com.ust.pos.model.Warehouse;
import com.ust.pos.model.WarehouseRepository;
import com.ust.pos.warehouse.service.WarehouseService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.util.List;

@Service
public class WarehouseServiceImpl implements WarehouseService {

    @Autowired
    private WarehouseRepository warehouseRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public WarehouseDto findByIdentifier(String identifier) {
        return modelMapper.map(
                warehouseRepository.findByIdentifier(identifier),
                WarehouseDto.class
        );
    }

    @Override
    public WarehouseDto save(WarehouseDto warehouseDto) {

        String identifier = warehouseDto.getIdentifier();
        Warehouse existingWarehouse = warehouseRepository.findByIdentifier(identifier);

        if (existingWarehouse != null) {
            warehouseDto.setMessage("Warehouse with identifier - " + identifier + " already exists");
            warehouseDto.setSuccess(false);
            return warehouseDto;
        }

        Warehouse warehouse = modelMapper.map(warehouseDto, Warehouse.class);
        warehouseRepository.save(warehouse);

        warehouseDto.setMessage("Warehouse created successfully");
        warehouseDto.setSuccess(true);
        return warehouseDto;
    }

    @Override
    public WarehouseDto update(WarehouseDto warehouseDto) {

        String identifier = warehouseDto.getIdentifier();
        Warehouse existingWarehouse = warehouseRepository.findByIdentifier(identifier);

        if (existingWarehouse == null) {
            warehouseDto.setMessage("Warehouse with identifier - " + identifier + " not found");
            warehouseDto.setSuccess(false);
            return warehouseDto;
        }

        modelMapper.map(warehouseDto, existingWarehouse);
        warehouseRepository.save(existingWarehouse);

        warehouseDto.setMessage("Warehouse updated successfully");
        warehouseDto.setSuccess(true);
        return warehouseDto;
    }

    @Override
    public boolean delete(String identifier) {
        warehouseRepository.deleteByIdentifier(identifier);
        return true;
    }

    @Override
    public List<WarehouseDto> findAll() {

        Type listType = new TypeToken<List<WarehouseDto>>() {}.getType();

        return modelMapper.map(
                warehouseRepository.findAll(),
                listType
        );
    }
}
