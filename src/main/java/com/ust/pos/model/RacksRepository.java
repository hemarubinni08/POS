package com.ust.pos.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RacksRepository extends JpaRepository<Racks, Long> {
    void deleteByIdentifier(String identifier);

    Racks findByIdentifier(String identifier);

    boolean existsByIdentifier(String identifier);

    List<Racks> findByStatus(boolean status);
}
