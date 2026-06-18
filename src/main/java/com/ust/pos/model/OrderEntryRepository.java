package com.ust.pos.model;

import com.ust.pos.model.OrderEntry;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderEntryRepository extends JpaRepository<OrderEntry, Long> {

    List<OrderEntry> findByOrderIdentifier(String orderIdentifier);

    void deleteByOrderIdentifier(String orderIdentifier);
}
