package com.ust.pos.model;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import java.util.List;

public interface CartRepository extends JpaRepository<Cart, Long> {
    Cart findByIdentifier(String identifier);

    void deleteByIdentifier(String identifier);

    List<Cart> findAllByStatus(boolean status);
}