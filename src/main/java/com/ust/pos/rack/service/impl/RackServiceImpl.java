package com.ust.pos.rack.service.impl;

import com.ust.pos.dto.RackDto;
import com.ust.pos.model.Rack;
import com.ust.pos.model.RackRepository;
import com.ust.pos.model.ShelfRepository;
import com.ust.pos.rack.service.RackService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.lang.reflect.Type;
import java.util.List;

@Service
public class RackServiceImpl implements RackService {

    @Autowired
    private RackRepository rackRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private ShelfRepository shelfRepository;
    @Override
    public RackDto findByIdentifier(String identifier) {
        return modelMapper.map(rackRepository.findByIdentifier(identifier), RackDto.class);
    }

    @Override
    public RackDto save(RackDto rackDto) {
        String identifier = rackDto.getIdentifier();
        Rack existingRack = rackRepository.findByIdentifier(identifier);
        if (existingRack != null) {
            rackDto.setMessage("Rack with identifier - " + identifier + " already exists");
            rackDto.setSuccess(false);
            return rackDto;
        }
        Rack rack = modelMapper.map(rackDto, Rack.class);
        rackRepository.save(rack);
        return rackDto;
    }

    @Override
    public RackDto update(RackDto rackDto) {
        String identifier = rackDto.getIdentifier();
        Rack existingRack = rackRepository.findByIdentifier(identifier);
        if (existingRack == null) {
            rackDto.setMessage("Rack with identifier - " + identifier + " not found");
            rackDto.setSuccess(false);
            return rackDto;
        }
        modelMapper.map(rackDto, existingRack);
        rackRepository.save(existingRack);
        return rackDto;
    }

    @Override
    public boolean delete(String identifier) {
        rackRepository.deleteByIdentifier(identifier);
        return true;
    }

    @Override
    public List<RackDto> findAll(Pageable pageable) {
        Type listType = new TypeToken<List<RackDto>>() {}.getType();
        Page<Rack> rackPage = rackRepository.findAll(pageable);
        return modelMapper.map(rackPage.getContent(), listType);
    }

    @Override
    public RackDto toggleStatus(String identifier) {
        Rack rack = rackRepository.findByIdentifier(identifier);
        rack.setStatus(!rack.isStatus());
        Rack updated = rackRepository.save(rack);
        return modelMapper.map(updated, RackDto.class);
    }

}