package com.ust.pos.rack.service;

import com.ust.pos.dto.RackDto;
import java.util.List;

public interface RackService {

    RackDto save(RackDto rackDto);
    RackDto update(RackDto rackDto);
    RackDto findByIdentifier(String identifier);
    List<RackDto> findAll();
    List<RackDto> getActiveRacks();
    void delete(String identifier);
    RackDto toggleStatus(String identifier);
}