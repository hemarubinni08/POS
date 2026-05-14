package com.ust.pos.model;

import com.ust.pos.model.CartEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartEntryRepository extends JpaRepository<CartEntry, Long> {

}