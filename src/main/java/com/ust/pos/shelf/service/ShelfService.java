package com.ust.pos.shelf.service;

import com.ust.pos.dto.ShelfDto;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface ShelfService {

    ShelfDto save(ShelfDto shelfDto);

    ShelfDto update(ShelfDto shelfDto);

    boolean delete(String identifier);

    List<ShelfDto> findAll(Pageable pageable);

    ShelfDto findByIdentifier(String identifier);

    List<ShelfDto> findIfTrue();

    ShelfDto toggleStatus(String identifier);

}