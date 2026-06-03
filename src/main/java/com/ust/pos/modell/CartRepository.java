package com.ust.pos.modell;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartRepository extends JpaRepository<Cart,Long> {

    Cart findByIdentifier(String identifier);

    void deleteByIdentifier(String identifier);


}
