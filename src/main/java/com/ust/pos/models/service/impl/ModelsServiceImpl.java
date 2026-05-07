package com.ust.pos.models.service.impl;

import com.ust.pos.dto.ModelsDto;
import com.ust.pos.model.Models;
import com.ust.pos.model.ModelsRepository;
import com.ust.pos.models.service.ModelsService;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Optional;

@Service
public class ModelsServiceImpl implements ModelsService {
    @Autowired
    ModelsRepository modelsRepository;
    @Autowired
    ModelMapper modelMapper;

    @Override
    public ModelsDto save(ModelsDto modelsDto) {
        modelsDto.setIdentifier(modelsDto.getIdentifier().trim());
        String identifier = modelsDto.getIdentifier();
        if (modelsRepository.existsByIdentifier(identifier)) {
            modelsDto.setMessage("Already exists");
            modelsDto.setSuccess(false);
            return modelsDto;
        }
        Models models = modelMapper.map(modelsDto, Models.class);
        modelsRepository.save(models);
        return modelsDto;
    }

    @Override
    public List<ModelsDto> findAll() {
        Type listType = new TypeToken<List<ModelsDto>>() {
        }.getType();
        return modelMapper.map(modelsRepository.findAll(), listType);
    }

    @Override
    @Transactional
    public boolean delete(String identifier) {
        modelsRepository.deleteByIdentifier(identifier);
        return true;
    }

    @Override
    public ModelsDto findByIdentifier(String identifier) {
        Models models = modelsRepository.findByIdentifier(identifier);
        return modelMapper.map(models, ModelsDto.class);
    }

    @Override
    public ModelsDto update(ModelsDto modelsDto) {
        Optional<Models> models = modelsRepository.findById(modelsDto.getId());
        if (models.isPresent()) {
            Models newmodels = models.get();
            modelMapper.map(modelsDto, newmodels);
            modelsRepository.save(newmodels);
        }
        return modelsDto;
    }

    @Override
    public List<ModelsDto> findAllActive() {
        Type listType = new TypeToken<List<ModelsDto>>() {
        }.getType();
        return modelMapper.map(modelsRepository.findByStatus(true), listType);
    }

    @Override
    public ModelsDto updateStatus(String identifier, boolean status) {
        Models models = modelsRepository.findByIdentifier(identifier);
        models.setStatus(status);
        modelsRepository.save(models);
        return modelMapper.map(models, ModelsDto.class);
    }
}
