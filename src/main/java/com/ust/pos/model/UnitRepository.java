package com.ust.pos.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UnitRepository extends JpaRepository<Unit, Long> {
    void deleteByIdentifier(String identifier);

    Unit findByIdentifier(String identifier);

    boolean existsByIdentifier(String identifier);

    List<Unit> findByStatus(boolean status);
}
