package com.ust.pos.models.service;

import com.ust.pos.dto.ModelsDto;

import java.util.List;

public interface ModelsService {
    ModelsDto save(ModelsDto modelsDto);

    List<ModelsDto> findAll();

    boolean delete(String identifier);

    ModelsDto findByIdentifier(String identifier);

    ModelsDto update(ModelsDto modelsDto);

    List<ModelsDto> findAllActive();

    ModelsDto updateStatus(String identifier, boolean status);
}
