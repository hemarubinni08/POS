package com.ust.pos.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    Product findByIdentifier(String identifier);

    void deleteByIdentifier(String identifier);

    List<Product> findByStatusTrue();

    //List<Product> findByProductNameContainingIgnoreCaseOrIdentifierContainingIgnoreCase(String productName,String identifier);

    @Query("""
    SELECT p
    FROM Product p
    WHERE p.status = true
    AND (
        LOWER(p.productName) LIKE LOWER(CONCAT('%', :query, '%'))
        OR LOWER(p.identifier) LIKE LOWER(CONCAT('%', :query, '%'))
    )
    """)
    List<Product> searchActiveProducts(@Param("query") String query);
}