package com.ust.pos.models.service;

import com.ust.pos.dto.ModelsDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ModelsService {
    ModelsDto save(ModelsDto modelsDto);

    List<ModelsDto> findAll(Pageable pageable);

    boolean delete(String identifier);

    ModelsDto findByIdentifier(String identifier);

    ModelsDto update(ModelsDto modelsDto);

    List<ModelsDto> findAllActive();

    ModelsDto updateStatus(String identifier, boolean status);
}
