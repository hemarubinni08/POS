package com.ust.pos.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StockRepository extends JpaRepository<Stock, Long> {
    Stock findById(long id);

    void deleteByIdentifier(String identifier);

    Stock findByIdentifier(String identifier);
}
