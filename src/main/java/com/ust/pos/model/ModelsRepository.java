package com.ust.pos.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ModelsRepository extends JpaRepository<Models, Long> {
    void deleteByIdentifier(String identifier);

    Models findByIdentifier(String identifier);

    boolean existsByIdentifier(String identifier);

    List<Models> findByStatus(boolean status);
}
