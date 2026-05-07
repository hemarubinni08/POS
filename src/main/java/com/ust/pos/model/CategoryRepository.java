package com.ust.pos.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    void deleteByIdentifier(String identifier);

    Category findByIdentifier(String identifier);

    boolean existsByIdentifier(String identifier);

    List<Category> findBySuperCategoryIsNot(String empty);

    List<Category> findByStatus(boolean status);

    List<Category> findByStatusTrueAndSuperCategoryIsNot(String empty);
}
