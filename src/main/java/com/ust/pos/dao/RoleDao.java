package com.ust.pos.dao;

import org.springframework.stereotype.Component;

@Component
public interface RoleDao {
    void deleteByIdentifier(String identifier);
}
