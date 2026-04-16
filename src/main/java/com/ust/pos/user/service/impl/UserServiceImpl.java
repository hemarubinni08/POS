package com.ust.pos.user.service.impl;

import com.ust.pos.dto.UserDto;
import com.ust.pos.model.User;
import com.ust.pos.model.UserRepository;
import com.ust.pos.user.service.UserService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public UserDto findByUserName(String username) {
        return modelMapper.map(userRepository.findByUsername(username), UserDto.class);
    }

    @Override
    public UserDto save(UserDto userDto) {
        String username = userDto.getUsername();
        User existingUser = userRepository.findByUsername(username);
        if (existingUser != null) {
            userDto.setMessage("User with username/email - " + userDto.getUsername() + " already exists");
            userDto.setSuccess(false);
            return userDto;
        }
        User user = modelMapper.map(userDto, User.class);
        userRepository.save(user);
        return userDto;
    }

    @Override
    public UserDto update(UserDto userDto) {
        String username = userDto.getUsername();
        User existingUser = userRepository.findByUsername(username);
        if (existingUser == null) {
            userDto.setMessage("User with username/email - " + userDto.getUsername() + " not found");
            userDto.setSuccess(false);
            return userDto;
        }
        modelMapper.map(userDto, existingUser);
        userRepository.save(existingUser);
        return userDto;
    }

    @Override
    public boolean delete(String username) {
        return userRepository.deleteByUsername(username);
    }

    @Override
    public List<UserDto> findAll() {
        Type listType = new TypeToken<List<UserDto>>() {}.getType();
        return modelMapper.map(userRepository.findAll(), listType);
    }
}
