package com.ust.pos.racks.service.impl;

import com.ust.pos.dto.RacksDto;
import com.ust.pos.model.Racks;
import com.ust.pos.model.RacksRepository;
import com.ust.pos.racks.service.RacksService;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.util.List;

@Service
public class RacksServiceImpl implements RacksService {
    @Autowired
    RacksRepository racksRepository;
    @Autowired
    ModelMapper modelMapper;

    @Override
    public RacksDto save(RacksDto racksDto) {
        racksDto.setIdentifier(racksDto.getIdentifier().trim());
        String identifier = racksDto.getIdentifier();
        if (racksRepository.existsByIdentifier(identifier)) {
            racksDto.setMessage("Already exists");
            racksDto.setSuccess(false);
            return racksDto;
        }
        Racks racks = modelMapper.map(racksDto, Racks.class);
        racksRepository.save(racks);
        return racksDto;
    }

    @Override
    public List<RacksDto> findAll() {
        Type listType = new TypeToken<List<RacksDto>>() {
        }.getType();
        return modelMapper.map(racksRepository.findAll(), listType);
    }

    @Override
    @Transactional
    public boolean delete(String identifier) {
        racksRepository.deleteByIdentifier(identifier);
        return true;
    }

    @Override
    public RacksDto findByIdentifier(String identifier) {
        Racks racks = racksRepository.findByIdentifier(identifier);
        return modelMapper.map(racks, RacksDto.class);
    }

    @Override
    public RacksDto update(RacksDto racksDto) {
        Racks racks = racksRepository.findByIdentifier(racksDto.getIdentifier());
        modelMapper.map(racksDto, racks);
        racksRepository.save(racks);
        return racksDto;
    }

    @Override
    public List<RacksDto> findAllActive() {
        Type listType = new TypeToken<List<RacksDto>>() {
        }.getType();
        return modelMapper.map(racksRepository.findByStatus(true), listType);
    }

    @Override
    public RacksDto changeStatus(String identifier, boolean status) {
        Racks racks = racksRepository.findByIdentifier(identifier);
        racks.setStatus(status);
        racksRepository.save(racks);
        return modelMapper.map(racks, RacksDto.class);
    }
}
