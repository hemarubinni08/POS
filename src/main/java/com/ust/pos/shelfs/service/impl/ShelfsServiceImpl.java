package com.ust.pos.shelfs.service.impl;

import com.ust.pos.dto.ShelfsDto;
import com.ust.pos.model.Shelfs;
import com.ust.pos.model.ShelfsRepository;
import com.ust.pos.shelfs.service.ShelfsService;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.util.List;

@Service
public class ShelfsServiceImpl implements ShelfsService {
    @Autowired
    ShelfsRepository shelfsRepository;
    @Autowired
    ModelMapper modelMapper;

    @Override
    public ShelfsDto save(ShelfsDto shelfsDto) {
        shelfsDto.setIdentifier(shelfsDto.getIdentifier().trim());
        String identifier = shelfsDto.getIdentifier();
        if (shelfsRepository.existsByIdentifier(identifier)) {
            shelfsDto.setMessage("Already exists");
            shelfsDto.setSuccess(false);
            return shelfsDto;
        }
        Shelfs shelfs = modelMapper.map(shelfsDto, Shelfs.class);
        shelfsRepository.save(shelfs);
        return shelfsDto;
    }

    @Override
    public List<ShelfsDto> findAll() {
        Type listType = new TypeToken<List<ShelfsDto>>() {
        }.getType();
        return modelMapper.map(shelfsRepository.findAll(), listType);
    }

    @Override
    @Transactional
    public boolean delete(String identifier) {
        shelfsRepository.deleteByIdentifier(identifier);
        return true;
    }

    @Override
    public ShelfsDto findByIdentifier(String identifier) {
        Shelfs shelfs = shelfsRepository.findByIdentifier(identifier);
        return modelMapper.map(shelfs, ShelfsDto.class);
    }

    @Override
    public ShelfsDto update(ShelfsDto shelfsDto) {
        Shelfs shelfs = shelfsRepository.findByIdentifier(shelfsDto.getIdentifier());
        modelMapper.map(shelfsDto, shelfs);
        shelfs.setStatus(shelfsDto.isStatus());
        shelfsRepository.save(shelfs);
        return shelfsDto;
    }

    @Override
    public ShelfsDto statusUpdate(String identifier, boolean status) {
        Shelfs shelfs = shelfsRepository.findByIdentifier(identifier);
        shelfs.setStatus(status);
        shelfsRepository.save(shelfs);
        return modelMapper.map(shelfs, ShelfsDto.class);
    }

    @Override
    public List<ShelfsDto> findAllActive() {
        Type listType = new TypeToken<List<ShelfsDto>>() {
        }.getType();
        return modelMapper.map(shelfsRepository.findByStatus(true), listType);
    }
}
