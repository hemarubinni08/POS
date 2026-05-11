package com.ust.pos.racks.service;

import com.ust.pos.dto.RacksDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface RacksService {
    RacksDto save(RacksDto userDto);

    RacksDto update(RacksDto userDto);

    void delete(String username);

    List<RacksDto> findAll(Pageable pageable);

    RacksDto findByIdentifier(String identifier);

    void toggleStatus(String identifier);
}
