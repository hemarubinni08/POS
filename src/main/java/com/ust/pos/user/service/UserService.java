package com.ust.pos.user.service;

import com.ust.pos.dto.UserDto;

import java.util.List;

public interface UserService {
    UserDto findByUserName(String userName);

    UserDto save(UserDto userDto);

    UserDto update(UserDto userDto);

    boolean delete(String userName);

    List<UserDto> findAll();
}
