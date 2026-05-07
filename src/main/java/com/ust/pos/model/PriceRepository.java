package com.ust.pos.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PriceRepository extends JpaRepository<Price, Long> {
    void deleteByIdentifier(String identifier);

    Price findByIdentifier(String identifier);

    boolean existsByIdentifier(String identifier);

    List<Price> findByStatus(boolean status);
}
