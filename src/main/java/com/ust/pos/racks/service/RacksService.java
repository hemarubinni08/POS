package com.ust.pos.racks.service;

import com.ust.pos.dto.RacksDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface RacksService {
    RacksDto save(RacksDto racksDto);

    List<RacksDto> findAll(Pageable pageable);

    boolean delete(String identifier);

    RacksDto findByIdentifier(String identifier);

    RacksDto update(RacksDto racksDto);

    List<RacksDto> findAllActive();

    RacksDto changeStatus(String identifier, boolean status);
}
