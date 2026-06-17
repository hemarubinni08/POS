package com.ust.pos.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PriceRepository extends JpaRepository<Price, Long> {

    void deleteByProductId(String productId);

    void deleteByIdentifier(String identifier);

    Price findByIdentifier(String identifier);

    @Query("""
    SELECT COUNT(DISTINCT p.priceType)
    FROM Price p
    WHERE p.productId = :productId
    AND p.status = true
    AND p.priceType IN ('Selling Price', 'Cost Price', 'MRP')
    """)
    long countActivePriceTypes(@Param("productId") String productId);
}