package com.ust.pos.model;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface WarehouseRepository extends JpaRepository<Warehouse, Long> {

    Optional<Warehouse> findByIdentifier(String identifier);

    void deleteByIdentifier(String identifier);
}