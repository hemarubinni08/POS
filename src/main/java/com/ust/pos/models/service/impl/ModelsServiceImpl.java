package com.ust.pos.models.service.impl;

import com.ust.pos.dto.ModelsDto;
import com.ust.pos.model.Models;
import com.ust.pos.model.ModelsRepository;
import com.ust.pos.models.service.ModelsService;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.lang.reflect.Type;
import java.util.List;

@Service
@Transactional
public class ModelsServiceImpl implements ModelsService {

    @Autowired
    private ModelsRepository modelsRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Override
    public ModelsDto findByIdentifier(String identifier) {
        return modelMapper.map(
                modelsRepository.findByIdentifier(identifier),
                ModelsDto.class);
    }

    @Override
    public ModelsDto save(ModelsDto modelsDto) {
        String identifier = modelsDto.getIdentifier();
        Models existingModel = modelsRepository.findByIdentifier(identifier);
        if (existingModel != null) {
            modelsDto.setMessage("Model with identifier - " + identifier + " already exists");
            modelsDto.setSuccess(false);
            return modelsDto;
        }
        Models model = modelMapper.map(modelsDto, Models.class);
        modelsRepository.save(model);
        modelsDto.setMessage("Model created successfully");
        modelsDto.setSuccess(true);
        return modelsDto;
    }

    @Override
    public ModelsDto update(ModelsDto modelsDto) {
        String identifier = modelsDto.getIdentifier();
        Models existingModel = modelsRepository.findByIdentifier(identifier);
        if (existingModel == null) {
            modelsDto.setMessage("Model with identifier - " + identifier + " not found");
            modelsDto.setSuccess(false);
            return modelsDto;
        }
        modelMapper.map(modelsDto, existingModel);
        modelsRepository.save(existingModel);
        modelsDto.setMessage("Model updated successfully");
        modelsDto.setSuccess(true);
        return modelsDto;
    }

    @Override
    public boolean delete(String identifier) {
        modelsRepository.deleteByIdentifier(identifier);
        return true;
    }

    @Override
    public List<ModelsDto> findAll(Pageable pageable) {
        Type listType = new TypeToken<List<ModelsDto>>() {}.getType();
        Page<Models> modelsPage = modelsRepository.findAll(pageable);
        return modelMapper.map(modelsPage.getContent(), listType);
    }

    @Override
    public ModelsDto toggleStatus(String identifier) {
        Models models = modelsRepository.findByIdentifier(identifier);
        models.setStatus(!models.isStatus());
        Models updated = modelsRepository.save(models);
        return modelMapper.map(updated, ModelsDto.class);
    }

}