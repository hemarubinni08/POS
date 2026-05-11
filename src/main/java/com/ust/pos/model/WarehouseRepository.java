package com.ust.pos.model;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

public interface WarehouseRepository extends JpaRepository<Warehouse, Long> {
    Warehouse findByIdentifier(String identifier);


    @Modifying
    @Transactional
    void deleteByIdentifier(String identifier);
}
