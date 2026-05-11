package com.ust.pos;

import com.ust.pos.dto.UserDto;
import com.ust.pos.model.User;
import com.ust.pos.model.UserRepository;
import com.ust.pos.user.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @InjectMocks
    private UserServiceImpl userService;
    @Mock
    private UserRepository userRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private ModelMapper modelMapper;
    @Test
    void saveTest() {
        UserDto userDto = new UserDto();
        userDto.setUsername("john");
        userDto.setPassword("plain123");
        Mockito.when(userRepository.findByUsername("john")).thenReturn(null);
        User user = new User();
        Mockito.when(modelMapper.map(userDto, User.class)).thenReturn(user);
        Mockito.when(passwordEncoder.encode("plain123")).thenReturn("encoded123");
        Mockito.when(userRepository.save(user)).thenReturn(user);
        UserDto response = userService.save(userDto);
        ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class);
        Mockito.verify(userRepository).save(captor.capture());
        Assertions.assertEquals("encoded123", captor.getValue().getPassword());
        Assertions.assertEquals("john", response.getUsername());
    }

    @Test
    void saveTestFailure() {
        UserDto userDto = new UserDto();
        userDto.setUsername("john");
        User existingUser = new User();
        Mockito.when(userRepository.findByUsername("john")).thenReturn(existingUser);
        UserDto response = userService.save(userDto);
        Assertions.assertEquals("john", response.getUsername());
        Assertions.assertFalse(response.isSuccess());
        Assertions.assertNotNull(response.getMessage());
    }

    @Test
    void findByUserNameTest() {
        User user = new User();
        user.setUsername("john");
        UserDto userDto = new UserDto();
        userDto.setUsername("john");
        Mockito.when(userRepository.findByUsername("john")).thenReturn(user);
        Mockito.when(modelMapper.map(user, UserDto.class)).thenReturn(userDto);
        UserDto response = userService.findByUserName("john");
        Assertions.assertEquals("john", response.getUsername());
    }

    @Test
    void updateTest() {
        UserDto userDto = new UserDto();
        userDto.setId(1L);
        userDto.setUsername("john");
        User existingUser = new User();
        existingUser.setId(1L);
        existingUser.setUsername("john");
        Mockito.when(userRepository.findById(1L))
                .thenReturn(Optional.of(existingUser));
        Mockito.when(userRepository.save(existingUser))
                .thenReturn(existingUser);
        UserDto response = userService.update(userDto);
        Assertions.assertEquals("john", response.getUsername());
    }

    @Test
    void updateTestFailure_UserNotFound() {
        UserDto userDto = new UserDto();
        userDto.setId(1L);
        userDto.setUsername("john");
        Mockito.when(userRepository.findById(1L))
                .thenReturn(Optional.empty());
        UserDto response = userService.update(userDto);
        Assertions.assertFalse(response.isSuccess());
        Assertions.assertNotNull(response.getMessage());
    }

    @Test
    void updateTestFailure_UsernameAlreadyExists() {
        UserDto userDto = new UserDto();
        userDto.setId(1L);
        userDto.setUsername("newUser");
        User existingUser = new User();
        existingUser.setId(1L);
        existingUser.setUsername("oldUser");
        User anotherUser = new User();
        Mockito.when(userRepository.findById(1L))
                .thenReturn(Optional.of(existingUser));
        Mockito.when(userRepository.findByUsername("newUser"))
                .thenReturn(anotherUser);
        UserDto response = userService.update(userDto);
        Assertions.assertFalse(response.isSuccess());
        Assertions.assertNotNull(response.getMessage());
    }

    @Test
    void deleteTest() {
        Mockito.doNothing().when(userRepository).deleteByUsername("john");
        boolean result = userService.delete("john");
        Mockito.verify(userRepository).deleteByUsername("john");
        Assertions.assertTrue(result);
    }

    @Test
    void findAllTest() {
        User user = new User();
        user.setUsername("john");
        UserDto userDto = new UserDto();
        userDto.setUsername("john");
        List<User> users = List.of(user);
        List<UserDto> userDtos = List.of(userDto);
        Pageable pageable = PageRequest.of(0, 50, Sort.by(new ArrayList<>()));
        Page<User> userPage =
                new PageImpl<>(users, pageable, users.size());
        Mockito.when(userRepository.findAll(pageable))
                .thenReturn(userPage);
        Mockito.when(
                modelMapper.map(
                        Mockito.eq(users),
                        Mockito.any(java.lang.reflect.Type.class))).thenReturn(userDtos);
        List<UserDto> response = userService.findAll(pageable);
        Assertions.assertEquals(1, response.size());
        Assertions.assertEquals("john", response.get(0).getUsername());
    }

    @Test
    void updateTestSuccess_WithSameUsername() {
        UserDto userDto = new UserDto();
        userDto.setId(1L);
        userDto.setUsername("john");
        User existingUser = new User();
        existingUser.setId(1L);
        existingUser.setUsername("john");
        Mockito.when(userRepository.findById(1L))
                .thenReturn(Optional.of(existingUser));
        Mockito.when(userRepository.save(existingUser))
                .thenReturn(existingUser);
        UserDto response = userService.update(userDto);
        Mockito.verify(modelMapper)
                .map(userDto, existingUser);
        Assertions.assertTrue(response.isSuccess());
        Assertions.assertEquals("john", response.getUsername());
    }

    @Test
    void updateTestSuccess_WithNewUsername_NoConflict() {
        UserDto userDto = new UserDto();
        userDto.setId(1L);
        userDto.setUsername("newUser");
        User existingUser = new User();
        existingUser.setId(1L);
        existingUser.setUsername("oldUser");
        Mockito.when(userRepository.findById(1L))
                .thenReturn(Optional.of(existingUser));
        Mockito.when(userRepository.findByUsername("newUser"))
                .thenReturn(null);
        Mockito.when(userRepository.save(existingUser))
                .thenReturn(existingUser);
        UserDto response = userService.update(userDto);
        Mockito.verify(modelMapper)
                .map(userDto, existingUser);
        Mockito.verify(userRepository)
                .save(existingUser);
        Assertions.assertTrue(response.isSuccess());
    }
}