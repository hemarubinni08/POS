package com.ust.pos.shelfs.service.impl;

import com.ust.pos.dto.ModelsDto;
import com.ust.pos.dto.ShelfsDto;
import com.ust.pos.model.Models;
import com.ust.pos.model.Shelfs;
import com.ust.pos.model.ShelfsRepository;
import com.ust.pos.shelfs.service.ShelfsService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.util.List;

@Service
public class ShelfsServiceImpl implements ShelfsService {

    @Autowired
    private ShelfsRepository shelfsRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public ShelfsDto save(ShelfsDto shelfsDto) {

        Shelfs existing = shelfsRepository.findByIdentifier(shelfsDto.getIdentifier());

        if (existing != null) {
            shelfsDto.setMessage("Shelf already exists");
            shelfsDto.setSuccess(false);
            return shelfsDto;
        }

        Shelfs shelfs = modelMapper.map(shelfsDto, Shelfs.class);
        shelfsRepository.save(shelfs);

        shelfsDto.setMessage("Shelf created successfully");
        shelfsDto.setSuccess(true);
        return shelfsDto;
    }

    @Override
    public ShelfsDto update(ShelfsDto shelfsDto) {

        Shelfs existing = shelfsRepository.findByIdentifier(shelfsDto.getIdentifier());

        if (existing == null) {
            shelfsDto.setMessage("Shelf not found");
            shelfsDto.setSuccess(false);
            return shelfsDto;
        }

        modelMapper.map(shelfsDto, existing);
        shelfsRepository.save(existing);

        shelfsDto.setMessage("Shelf updated successfully");
        shelfsDto.setSuccess(true);
        return shelfsDto;
    }

    @Override
    public boolean delete(String identifier) {
        shelfsRepository.deleteByIdentifier(identifier);
        return true;
    }

    @Override
    public List<ShelfsDto> findAll() {
        Type listType = new TypeToken<List<ShelfsDto>>() {}.getType();
        return modelMapper.map(shelfsRepository.findAll(), listType);
    }

    @Override
    public ShelfsDto findByIdentifier(String identifier) {
        return modelMapper.map(
                shelfsRepository.findByIdentifier(identifier),
                ShelfsDto.class
        );
    }

    @Override
    public ShelfsDto toggleStatus(String identifier) {
        Shelfs shelfs=shelfsRepository.findByIdentifier(identifier);
        shelfs.setStatus(!shelfs.isStatus());
        Shelfs updated=shelfsRepository.save(shelfs);
        return modelMapper.map(updated, ShelfsDto.class);
    }
}