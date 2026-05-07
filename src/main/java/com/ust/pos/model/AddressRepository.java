package com.ust.pos.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {

    List<Address> findAllByPhoneNumber(String phoneNumber);

    Address findByPhoneNumber(String phoneNumber);

    Address findByPhoneNumberAndAddressType(String phoneNumber, String addressType);
}
