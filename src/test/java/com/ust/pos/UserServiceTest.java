package com.ust.pos;

import com.ust.pos.dto.UserDto;
import com.ust.pos.model.User;
import com.ust.pos.model.UserRepository;
import com.ust.pos.user.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private PasswordEncoder passwordEncoder;

    // ================= SAVE =================
    @Test
    void saveTest() {

        UserDto dto = new UserDto();
        dto.setUsername("admin");
        dto.setPassword("123");

        Mockito.when(userRepository.findByUsername("admin"))
                .thenReturn(null);

        User user = new User();
        user.setUsername("admin");

        Mockito.when(modelMapper.map(dto, User.class))
                .thenReturn(user);

        Mockito.when(passwordEncoder.encode("123"))
                .thenReturn("encoded123");

        Mockito.when(userRepository.save(user))
                .thenReturn(user);

        UserDto response = userService.save(dto);

        Assertions.assertTrue(response.isSuccess());
    }

    @Test
    void saveTestFailure_UserExists() {

        UserDto dto = new UserDto();
        dto.setUsername("admin");

        Mockito.when(userRepository.findByUsername("admin"))
                .thenReturn(new User());

        UserDto response = userService.save(dto);

        Assertions.assertFalse(response.isSuccess());
        Assertions.assertTrue(response.getMessage().contains("User already exists"));
    }

    // ================= FIND BY USERNAME =================
    @Test
    void findByUserNameTest() {

        User user = new User();
        user.setUsername("admin");

        UserDto mappedDto = new UserDto();
        mappedDto.setUsername("admin");

        Mockito.when(userRepository.findByUsername("admin"))
                .thenReturn(user);

        Mockito.when(modelMapper.map(user, UserDto.class))
                .thenReturn(mappedDto);

        UserDto response = userService.findByUserName("admin");

        Assertions.assertEquals("admin", response.getUsername());
    }

    @Test
    void findByUserName_NotFound() {

        Mockito.when(userRepository.findByUsername("admin"))
                .thenReturn(null);

        UserDto response = userService.findByUserName("admin");

        Assertions.assertNull(response);
    }

    // ================= UPDATE =================
    @Test
    void updateTest() {

        UserDto dto = new UserDto();
        dto.setId(1L);
        dto.setUsername("admin");

        User existing = new User();
        existing.setId(1L);
        existing.setUsername("admin");

        Mockito.when(userRepository.findById(1L))
                .thenReturn(Optional.of(existing));

        Mockito.when(userRepository.save(existing))
                .thenReturn(existing);

        UserDto response = userService.update(dto);

        Assertions.assertTrue(response.isSuccess());
    }

    @Test
    void updateTest_UserNotFound() {

        UserDto dto = new UserDto();
        dto.setId(1L);

        Mockito.when(userRepository.findById(1L))
                .thenReturn(Optional.empty());

        UserDto response = userService.update(dto);

        Assertions.assertFalse(response.isSuccess());
        Assertions.assertEquals("User not found", response.getMessage());
    }

    @Test
    void updateTest_UsernameConflict() {

        UserDto dto = new UserDto();
        dto.setId(1L);
        dto.setUsername("newUser");

        User existing = new User();
        existing.setId(1L);
        existing.setUsername("oldUser");

        Mockito.when(userRepository.findById(1L))
                .thenReturn(Optional.of(existing));

        Mockito.when(userRepository.findByUsername("newUser"))
                .thenReturn(new User());

        UserDto response = userService.update(dto);

        Assertions.assertFalse(response.isSuccess());
        Assertions.assertTrue(response.getMessage().contains("Username already exists"));
    }

    // ================= DELETE =================
    @Test
    void deleteTest() {

        Mockito.doNothing()
                .when(userRepository)
                .deleteByUsername("admin");

        userService.delete("admin");

        Mockito.verify(userRepository)
                .deleteByUsername("admin");
    }

    // ================= FIND ALL =================
    @Test
    void findAllTest() {

        User user = new User();
        user.setUsername("admin");

        UserDto dto = new UserDto();
        dto.setUsername("admin");

        List<User> list = List.of(user);
        List<UserDto> dtoList = List.of(dto);

        Mockito.when(userRepository.findAll()).thenReturn(list);

        Mockito.when(modelMapper.map(
                Mockito.eq(list),
                Mockito.any(java.lang.reflect.Type.class)
        )).thenReturn(dtoList);

        List<UserDto> response = userService.findAll();

        Assertions.assertEquals(1, response.size());
    }
}