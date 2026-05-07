package com.ust.pos.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StockRepository extends JpaRepository<Stock, Long> {
    void deleteByIdentifier(String identifier);

    Stock findByIdentifier(String identifier);

    boolean existsByIdentifier(String identifier);

    List<Stock> findByStatus(boolean status);
}
