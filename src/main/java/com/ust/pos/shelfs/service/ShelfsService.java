package com.ust.pos.shelfs.service;

import com.ust.pos.dto.ShelfsDto;

import java.util.List;

public interface ShelfsService {
    ShelfsDto save(ShelfsDto shelfsDto);

    List<ShelfsDto> findAll();

    boolean delete(String identifier);

    ShelfsDto findByIdentifier(String identifier);

    ShelfsDto update(ShelfsDto shelfsDto);

    ShelfsDto statusUpdate(String identifier, boolean status);

    List<ShelfsDto> findAllActive();
}
