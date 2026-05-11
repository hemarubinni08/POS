package com.ust.pos.models.service;

import com.ust.pos.dto.ModelsDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ModelsService {

    // Save new model
    ModelsDto save(ModelsDto modelsDto);

    // Update existing model
    ModelsDto update(ModelsDto modelsDto);

    // Find by identifier
    ModelsDto findByIdentifier(String identifier);

    // Get all models
    List<ModelsDto> findAll(Pageable pageable);

    // Delete model
    void delete(String identifier);

    ModelsDto toggleStatus(String identifier);

    List<ModelsDto> findActiveModels();
}