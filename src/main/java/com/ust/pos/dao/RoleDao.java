package com.ust.pos.dao;

import com.ust.pos.dto.RoleDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface RoleDao {

    RoleDto findByIdentifier(String identifier);

    RoleDto save(RoleDto roleDto);

    RoleDto update(RoleDto roleDto);

    boolean delete(String identifier);

    List<RoleDto> findAll(Pageable pageable);
}