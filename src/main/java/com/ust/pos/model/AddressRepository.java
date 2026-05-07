package com.ust.pos.model;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long> {

    Address findTopByPhoneNoAndAddressType(String phoneNo, String addressType);
    Address findTopByPhoneNoAndAddressTypeOrderByIdDesc(String phoneNo, String addressType);
    void deleteByPhoneNo(String phoneNo);
}