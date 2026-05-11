package com.ust.pos.rack.service;

import com.ust.pos.dto.RackDto;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface RackService {

    RackDto save(RackDto rackDto);

    RackDto update(RackDto rackDto);

    boolean delete(String identifier);

    List<RackDto> findAll(Pageable pageable);

    RackDto findByIdentifier(String identifier);

    RackDto toggleStatus(String identifier);


}