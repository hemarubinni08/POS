package com.ust.pos.model;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public interface ModelsRepository extends JpaRepository<Models, Long> {

    Models findByIdentifier(String identifier);

    void deleteByIdentifier(String identifier);
}