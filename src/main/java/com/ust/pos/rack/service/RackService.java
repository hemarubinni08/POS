package com.ust.pos.rack.service;

import com.ust.pos.dto.RackDto;

import java.util.List;

public interface RackService {

    RackDto createRack(RackDto rackDto);

    RackDto updateRack(RackDto rackDto);

    RackDto getRack(Long id);

    List<RackDto> getAllRacks();

    boolean deleteRack(Long id);
}