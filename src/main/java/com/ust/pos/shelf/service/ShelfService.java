package com.ust.pos.shelf.service;

import com.ust.pos.dto.ShelfDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ShelfService {
    ShelfDto save(ShelfDto userDto);

    ShelfDto update(ShelfDto userDto);

    void delete(String username);

    List<ShelfDto> findAll(Pageable pageable);

    ShelfDto findByIdentifier(String identifier);

    void toggleStatus(String identifier);
}
