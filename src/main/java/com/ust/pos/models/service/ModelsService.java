package com.ust.pos.models.service;

import com.ust.pos.dto.ModelsDto;
import com.ust.pos.dto.WsDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ModelsService {
    ModelsDto save(ModelsDto userDto);

    ModelsDto update(ModelsDto userDto);

    void delete(String username);

    WsDto<ModelsDto> findAll(Pageable pageable);

    ModelsDto findByIdentifier(String identifier);

    void toggleStatus(String identifier);
}
