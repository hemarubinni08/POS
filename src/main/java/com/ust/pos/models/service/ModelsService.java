package com.ust.pos.models.service;

import com.ust.pos.dto.ModelsDto;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Pageable;

import java.util.List;

@Transactional
public interface ModelsService {

    ModelsDto save(ModelsDto modelDto);

    ModelsDto update(ModelsDto modelDto);

    boolean delete(String identifier);

    List<ModelsDto> findAll(Pageable pageable);

    ModelsDto findByIdentifier(String identifier);

    ModelsDto toggleStatus(String identifier);

}