package com.ust.pos.model;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ShelfRepository extends JpaRepository<Shelf,Long> {
    Shelf findByIdentifier(String identifier);

    void deleteByIdentifier(String identifier);
}
