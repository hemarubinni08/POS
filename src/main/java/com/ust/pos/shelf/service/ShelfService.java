package com.ust.pos.shelf.service;

import com.ust.pos.dto.ShelfDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ShelfService {
    ShelfDto save(ShelfDto brandDto);

    ShelfDto update(ShelfDto brandDto);

    void delete(String identifier);

    List<ShelfDto> findAll(Pageable pageable);

    ShelfDto findByIdentifier(String identifier);

    void updateStatus(String identifier, boolean status);

    List<ShelfDto> findAllActive();
}