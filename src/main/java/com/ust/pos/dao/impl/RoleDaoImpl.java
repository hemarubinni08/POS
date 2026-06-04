package com.ust.pos.dao.impl;

import com.ust.pos.dao.RoleDao;
import com.ust.pos.dto.RoleDto;
import com.ust.pos.model.Role;
import com.ust.pos.model.RoleRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.lang.reflect.Type;
import java.util.List;

@Repository
public class RoleDaoImpl implements RoleDao {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public RoleDto findByIdentifier(String identifier) {
        Role role = roleRepository.findByIdentifier(identifier);
        if (role == null) return null;
        return modelMapper.map(role, RoleDto.class);
    }

    @Override
    public RoleDto save(RoleDto roleDto) {
        Role existing = roleRepository.findByIdentifier(roleDto.getIdentifier());
        if (existing != null) {
            roleDto.setSuccess(false);
            roleDto.setMessage("Role '" + roleDto.getIdentifier() + "' already exists");
            return roleDto;
        }
        Role role = modelMapper.map(roleDto, Role.class);
        roleRepository.save(role);
        roleDto.setSuccess(true);
        roleDto.setMessage("Role saved successfully");
        return roleDto;
    }

    @Override
    public RoleDto update(RoleDto roleDto) {
        String identifier = roleDto.getIdentifier();
        Role existingRole = roleRepository.findByIdentifier(identifier);
        if (existingRole == null) {
            roleDto.setMessage("Role with identifier - " + identifier + " not found");
            roleDto.setSuccess(false);
            return roleDto;
        }
        modelMapper.map(roleDto, existingRole);
        roleRepository.save(existingRole);
        roleDto.setSuccess(true);
        roleDto.setMessage("Role updated successfully");
        return roleDto;
    }

    @Override
    public boolean delete(String identifier) {
        Role existingRole = roleRepository.findByIdentifier(identifier);
        if (existingRole == null) return false;
        roleRepository.deleteByIdentifier(identifier);
        return true;
    }

    @Override
    public List<RoleDto> findAll(Pageable pageable) {
        Type listType = new TypeToken<List<RoleDto>>() {
        }.getType();
        Page<Role> rolePage = roleRepository.findAll(pageable);
        return modelMapper.map(rolePage.getContent(), listType);
    }
}