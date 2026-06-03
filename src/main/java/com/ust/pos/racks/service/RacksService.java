package com.ust.pos.racks.service;

import com.ust.pos.dto.PaginationResponseDto;
import com.ust.pos.dto.RacksDto;
import org.springframework.data.domain.Pageable;


public interface RacksService {
    RacksDto save(RacksDto userDto);

    RacksDto update(RacksDto userDto);

    void delete(String username);

    PaginationResponseDto<RacksDto> findAll(Pageable pageable);

    RacksDto findByIdentifier(String identifier);

    void toggleStatus(String identifier);
}
