package com.ust.pos.model;

import com.ust.pos.model.Price;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PriceRepository extends JpaRepository<Price, Long> {

    Optional<Price> findByProductIdAndStatus(Long productId, String status);

    List<Price> findByProductId(Long productId);
}