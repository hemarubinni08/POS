package com.ust.pos.model;

import jdk.jfr.Registered;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order,Long> {

    Order findByIdentifier(String identifier);

    boolean existsByIdentifier(String identifier);

    void deleteByIdentifier(String identifier);

}
