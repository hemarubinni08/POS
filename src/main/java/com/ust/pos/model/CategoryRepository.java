package com.ust.pos.model;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    boolean existsByIdentifierAndCategory(String identifier, String category);

    Optional<Category> findByIdentifier(String identifier);

    List<Category> findAll();
}
