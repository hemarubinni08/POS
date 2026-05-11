package com.ust.pos.models.service.impl;

import com.ust.pos.dto.ModelsDto;
import com.ust.pos.model.Models;
import com.ust.pos.models.service.ModelsService;
import com.ust.pos.model.ModelsRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.util.List;
import java.util.UUID;

@Service
public class ModelsServiceImpl implements ModelsService {
    @Autowired
    ModelsRepository modelsRepository;
    @Autowired
    ModelMapper modelMapper;

    @Override
    public ModelsDto save(ModelsDto modelsDto) {

        Models models = modelMapper.map(modelsDto, Models.class);
        models.setIdentifier("MODELS-" + UUID.randomUUID()
                .toString().substring(0, 4).toUpperCase());
        Models savedModels = modelsRepository.save(models);
        return modelMapper.map(savedModels, ModelsDto.class);

    }

    @Override
    public ModelsDto findById(Long id) {

        Models models = modelsRepository.findById(id).orElseThrow(() -> new RuntimeException("Models not found with id " + id));
        return modelMapper.map(models, ModelsDto.class);

    }

    @Override
    public List<ModelsDto> findAll(Pageable pageable) {

        Type listType = new TypeToken<List<ModelsDto>>() {}.getType();
        Page<Models> modelsPage=modelsRepository.findAll(pageable);
        return modelMapper.map(modelsPage.getContent(), listType);


    }

    @Override
    public void deleteById(Long id) {
        modelsRepository.deleteById(id);
    }

    @Override
    public ModelsDto update(ModelsDto modelsDto) {

        Models models = modelsRepository.findById(modelsDto.getId())
                .orElseThrow(() -> new RuntimeException("Models not found"));

        modelMapper.map(modelsDto, models);
        modelsRepository.save(models);
        return modelsDto;
    }

    @Override
    public ModelsDto changeModelsStatus(String identifier, boolean status) {
        Models models = modelsRepository.findByIdentifier(identifier);
        if (models != null) {
            models.setStatus(status);
            modelsRepository.save(models);
        }
        return modelMapper.map(models, ModelsDto.class);
    }
}