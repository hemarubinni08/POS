package com.ust.pos.unit.service.impl;

import com.ust.pos.dto.UnitDto;
import com.ust.pos.model.Unit;
import com.ust.pos.model.UnitRepository;
import com.ust.pos.unit.service.UnitService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.util.List;

@Service
public class UnitServiceImpl implements UnitService {

    @Autowired
    private UnitRepository unitRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public UnitDto findByIdentifier(String identifier) {
        return modelMapper.map(
                unitRepository.findByIdentifier(identifier),
                UnitDto.class
        );
    }

    @Override
    public UnitDto save(UnitDto unitDto) {
        String identifier = unitDto.getIdentifier();
        Unit existing = unitRepository.findByIdentifier(identifier);

        if (existing != null) {
            unitDto.setMessage("Unit already exists");
            unitDto.setSuccess(false);
            return unitDto;
        }

        Unit unit = modelMapper.map(unitDto, Unit.class);
        unitRepository.save(unit);

        unitDto.setMessage("Unit created successfully");
        unitDto.setSuccess(true);
        return unitDto;
    }

    @Override
    public UnitDto update(UnitDto unitDto) {
        Unit existing = unitRepository.findByIdentifier(unitDto.getIdentifier());

        if (existing == null) {
            unitDto.setMessage("Unit not found");
            unitDto.setSuccess(false);
            return unitDto;
        }

        modelMapper.map(unitDto, existing);
        unitRepository.save(existing);

        unitDto.setMessage("Unit updated successfully");
        unitDto.setSuccess(true);
        return unitDto;
    }

    @Override
    public boolean delete(String identifier) {
        unitRepository.deleteByIdentifier(identifier);
        return true;
    }

    @Override
    public List<UnitDto> findAll() {
        Type listType = new TypeToken<List<UnitDto>>() {}.getType();
        return modelMapper.map(unitRepository.findAll(), listType);
    }
}