package com.ust.pos.dao.impl;

import com.ust.pos.dao.RoleDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class RoleDaoImpl implements RoleDao {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public void deleteByIdentifier(String identifier) {
        String sqlQ = "DELETE FROM role WHERE identifier=?";
        jdbcTemplate.update(sqlQ, identifier);
    }

}
