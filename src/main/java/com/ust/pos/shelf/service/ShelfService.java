package com.ust.pos.shelf.service;

import com.ust.pos.dto.ShelfDto;

import java.util.List;

public interface ShelfService {
    ShelfDto save(ShelfDto shelfDto);

    ShelfDto update(ShelfDto shelfDto);

    ShelfDto findByIdentifier(String identifier);

    List<ShelfDto> findAll();

    void delete(String identifier);

    List<ShelfDto> findAllByStatus();

    void toggleStatus(String identifier);
}
