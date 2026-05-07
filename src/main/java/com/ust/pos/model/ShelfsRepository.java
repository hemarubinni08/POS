package com.ust.pos.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShelfsRepository extends JpaRepository<Shelfs, Long> {
    void deleteByIdentifier(String identifier);

    Shelfs findByIdentifier(String identifier);

    boolean existsByIdentifier(String identifier);

    List<Shelfs> findByStatus(boolean status);
}
