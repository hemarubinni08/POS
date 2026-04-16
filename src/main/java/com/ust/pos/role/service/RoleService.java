package com.ust.pos.role.service;

import com.ust.pos.dto.RoleDto;

import java.util.List;

public interface RoleService {
    RoleDto save(RoleDto userDto);
    RoleDto update(RoleDto userDto);
    boolean delete(String username);
    List<RoleDto> findAll();
    RoleDto findByIdentifier(String identifier);
}
