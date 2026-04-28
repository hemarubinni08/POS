package com.ust.pos.address.service;

import com.ust.pos.dto.AddressDto;

import java.util.List;

public interface AddressService {
    AddressDto save(AddressDto addressDto);

    AddressDto update(AddressDto addressDto);

    boolean delete(String phoneNo);

    List<AddressDto> findAll();

    AddressDto findByIdentifier(String identifier);

    List<AddressDto> findByPhoneNumber(String phoneNo);
}