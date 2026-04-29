package com.ust.pos.rack.service.impl;

import com.ust.pos.dto.RackDto;
import com.ust.pos.model.Rack;
import com.ust.pos.model.RackRepository;
import com.ust.pos.rack.service.RackService;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.util.List;

@Service
public class RackServiceImpl implements RackService {

    @Autowired
    private RackRepository rackRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public RackDto save(RackDto rackDto) {

        Rack existing = rackRepository.findByIdentifier(rackDto.getIdentifier());

        if (existing != null) {
            rackDto.setSuccess(false);
            rackDto.setMessage("Rack already exists");
            return rackDto;
        }

        Rack rack = modelMapper.map(rackDto, Rack.class);
        rackRepository.save(rack);

        rackDto.setSuccess(true);
        rackDto.setMessage("Rack created successfully");
        return rackDto;
    }

    @Override
    public RackDto update(RackDto rackDto) {

        Rack existing = rackRepository.findByIdentifier(rackDto.getIdentifier());

        if (existing == null) {
            rackDto.setSuccess(false);
            rackDto.setMessage("Rack not found");
            return rackDto;
        }

        modelMapper.map(rackDto, existing);
        rackRepository.save(existing);

        rackDto.setSuccess(true);
        rackDto.setMessage("Rack updated successfully");
        return rackDto;
    }

    @Override
    public RackDto findByIdentifier(String identifier) {
        return modelMapper.map(
                rackRepository.findByIdentifier(identifier),
                RackDto.class
        );
    }

    @Override
    public List<RackDto> findAll() {
        Type listType = new TypeToken<List<RackDto>>(){}.getType();
        return modelMapper.map(rackRepository.findAll(), listType);
    }

    @Override
    public boolean delete(String identifier) {
        rackRepository.deleteByIdentifier(identifier);
        return true;
    }
}