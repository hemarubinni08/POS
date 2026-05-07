package com.ust.pos.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Customer findByIdentifier(String identifier);

    void deleteByIdentifier(String identifier);

    Customer findByPhoneNo(String phoneNo);

    void deleteByPhoneNo(String phoneNo);

    Customer findByUsername(String username);

    List<Customer> findByStatus(boolean status);
}
