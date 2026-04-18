package com.ust.pos.user.service;

import com.ust.pos.dto.UserDto;

import java.util.List;

public interface UserService {
    UserDto findByUserName(String username);

    UserDto save(UserDto userDto);



    // ✅ FIX 2: USERNAME-BASED UPDATE (supports email change)
    UserDto update(String oldUsername, UserDto userDto);

    void delete(String username);

    List<UserDto> findAll();

}
