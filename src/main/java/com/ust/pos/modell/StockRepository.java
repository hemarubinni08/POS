package com.ust.pos.modell;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StockRepository extends JpaRepository<Stock, Long> {

    boolean existsByIdentifier(String identifier);

    Optional<Stock> findByProductIdAndWarehouseId(Long productId, Long warehouseId);

    Optional<Stock> findByIdentifier(String identifier);

    void deleteById(Long id);

}
