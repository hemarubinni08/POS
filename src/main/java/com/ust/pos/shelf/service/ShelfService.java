package com.ust.pos.shelf.service;

import com.ust.pos.dto.ShelfDto;

import java.util.List;

public interface ShelfService {
    ShelfDto save(ShelfDto brandDto);
    ShelfDto update(ShelfDto brandDto);
    void delete(String identifier);
    List<ShelfDto> findAll();
    ShelfDto findByIdentifier(String identifier);
    List<ShelfDto> findAllByStatus();
    void toggleStatus(String identifier);
}
