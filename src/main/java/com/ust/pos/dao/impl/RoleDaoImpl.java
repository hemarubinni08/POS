package com.ust.pos.dao.impl;

import com.ust.pos.dao.RoleDao;
import com.ust.pos.dto.RoleDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RoleDaoImpl implements RoleDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public RoleDto save(RoleDto roleDto) {
        String sql = "INSERT INTO ROLE (identifier, description, status) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, roleDto.getIdentifier(), roleDto.getDescription(), roleDto.isStatus());
        return findByIdentifier(roleDto.getIdentifier());
    }

    @Override
    public RoleDto update(RoleDto roleDto) {
        String sql = "UPDATE ROLE SET description = ?, status = ? WHERE identifier = ?";
        jdbcTemplate.update(sql, roleDto.getDescription(), roleDto.isStatus(), roleDto.getIdentifier());
        return findByIdentifier(roleDto.getIdentifier());
    }

    @Override
    public boolean delete(String identifier) {
        String sql = "DELETE FROM ROLE WHERE identifier = ?";
        int rows = jdbcTemplate.update(sql, identifier);
        return rows > 0;
    }

    @Override
    public RoleDto findByIdentifier(String identifier) {
        String sql = "SELECT * FROM ROLE WHERE identifier = ?";
        List<RoleDto> roleList = jdbcTemplate.query(sql, new Object[]{identifier}, new BeanPropertyRowMapper<>(RoleDto.class));
        return roleList.isEmpty() ? null : roleList.get(0);
    }

    @Override
    public List<RoleDto> findAll(Pageable pageable) {
        String sql = "SELECT * FROM ROLE LIMIT ? OFFSET ?";
        return jdbcTemplate.query(sql, new Object[]{pageable.getPageSize(), pageable.getOffset()}, new BeanPropertyRowMapper<>(RoleDto.class));
    }
}