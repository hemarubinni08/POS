package com.ust.pos.model;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long> {
    Address findByPhoneNoAndAddressType(Long phoneNo, String addressType);
    void deleteByPhoneNo(Long phoneNo);
}