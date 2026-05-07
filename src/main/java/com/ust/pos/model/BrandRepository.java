package com.ust.pos.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BrandRepository extends JpaRepository<Brand, Long> {
    void deleteByIdentifier(String identifier);

    Brand findByIdentifier(String identifier);

    boolean existsByIdentifier(String identifier);

    List<Brand> findByStatus(boolean status);
}
