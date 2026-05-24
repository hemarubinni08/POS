package com.ust.pos.model;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PriceRepository extends JpaRepository<Price, Long> {

    Price findByProductId(Long productId);

    void deleteByProductId(String productId);

    void deleteByIdentifier(String identifier);

    Price findByIdentifier(String identifier);
}