package com.ust.pos.models.service;

import com.ust.pos.dto.ModelsDto;
import com.ust.pos.dto.PaginationResponseDto;
import org.springframework.data.domain.Pageable;

public interface ModelsService {
    ModelsDto save(ModelsDto userDto);

    ModelsDto update(ModelsDto userDto);

    void delete(String username);

    PaginationResponseDto<ModelsDto> findAll(Pageable pageable);

    ModelsDto findByIdentifier(String identifier);

    void toggleStatus(String identifier);

}
