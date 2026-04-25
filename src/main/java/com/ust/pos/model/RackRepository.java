package com.ust.pos.model;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RackRepository extends JpaRepository<Rack, Long> {

    Optional<Rack> findByIdentifier(String identifier);

    boolean existsByIdentifier(String identifier);
}