package com.ust.pos.rack.service.impl;

import com.ust.pos.dto.RackDto;
import com.ust.pos.model.Rack;
import com.ust.pos.model.RackRepository;
import com.ust.pos.rack.service.RackService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RackServiceImpl implements RackService {

    @Autowired
    private RackRepository rackRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public RackDto createRack(RackDto rackDto) {

        if (rackRepository.existsByIdentifier(rackDto.getIdentifier())) {
            rackDto.setSuccess(false);
            rackDto.setMessage("Rack already exists");
            return rackDto;
        }

        Rack rack = modelMapper.map(rackDto, Rack.class);
        rackRepository.save(rack);
        return rackDto;
    }

    @Override
    public RackDto updateRack(RackDto rackDto) {
        Rack rack = modelMapper.map(rackDto, Rack.class);
        rackRepository.save(rack);
        return rackDto;
    }

    @Override
    public RackDto getRack(Long id) {

        RackDto dto = new RackDto();

        rackRepository.findById(id).ifPresentOrElse(rack -> {
            modelMapper.map(rack, dto);
            dto.setSuccess(true);
        }, () -> {
            dto.setSuccess(false);
            dto.setMessage("Rack not found");
        });

        return dto;
    }

    @Override
    public List<RackDto> getAllRacks() {

        return rackRepository.findAll().stream().map(rack -> modelMapper.map(rack, RackDto.class)).toList();
    }

    @Override
    public boolean deleteRack(Long id) {
        rackRepository.deleteById(id);
        return true;
    }
}