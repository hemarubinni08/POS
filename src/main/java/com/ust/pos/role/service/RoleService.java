package com.ust.pos.role.service;

import com.ust.pos.dto.RoleDto;

import java.util.List;

public interface RoleService {
    RoleDto save(RoleDto roleDto);

    RoleDto update(RoleDto roleDto);

    boolean delete(String identifier);

    List<RoleDto> findAll();

    RoleDto findByIdentifier(String identifier);

    List<RoleDto> findAllActive();

    RoleDto changeStatus(String identifier, boolean status);
}
