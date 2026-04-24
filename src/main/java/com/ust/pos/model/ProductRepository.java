package com.ust.pos.model;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {

    Optional<Product> findByIdentifier(String identifier);

    void deleteByIdentifier(String identifier);
}