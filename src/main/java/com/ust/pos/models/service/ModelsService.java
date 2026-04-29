package com.ust.pos.models.service;

import com.ust.pos.dto.ModelsDto;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface ModelsService {

    ModelsDto save(ModelsDto modelsDto);

    ModelsDto update(ModelsDto modelsDto);

    boolean delete(String identifier);

    List<ModelsDto> findAll();

    ModelsDto findByIdentifier(String identifier);
    void toggleStatus(String identifier);
}