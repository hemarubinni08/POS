package com.ust.pos;

import com.ust.pos.dto.UserDto;
import com.ust.pos.modell.User;
import com.ust.pos.modell.UserRepository;
import com.ust.pos.user.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;


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
    void findByUserNameTest() {
        User user = new User();
        user.setUsername("admin");

        UserDto userDto = new UserDto();
        userDto.setUsername("admin");

        Mockito.when(userRepository.findByUsername("admin"))
                .thenReturn(user);
        Mockito.when(modelMapper.map(user, UserDto.class))
                .thenReturn(userDto);

        UserDto response = userService.findByUserName("admin");

        Assertions.assertNotNull(response);
        Assertions.assertEquals("admin", response.getUsername());
    }

    @Test
    void findByUserNameNotFoundTest() {
        Mockito.when(userRepository.findByUsername("admin"))
                .thenReturn(null);

        UserDto response = userService.findByUserName("admin");

        Assertions.assertNull(response);
    }

    @Test
    void saveUserTest() {
        UserDto userDto = new UserDto();
        userDto.setUsername("admin");
        userDto.setPassword("password");

        User user = new User();
        user.setUsername("admin");

        Mockito.when(userRepository.findByUsername("admin"))
                .thenReturn(null);
        Mockito.when(modelMapper.map(userDto, User.class))
                .thenReturn(user);
        Mockito.when(passwordEncoder.encode("password"))
                .thenReturn("encodedPwd");

        UserDto response = userService.save(userDto);

        Assertions.assertTrue(response.isSuccess());
        Assertions.assertEquals("User created successfully", response.getMessage());

        Mockito.verify(userRepository).save(user);
    }

    @Test
    void saveUserFailureDuplicateUsernameTest() {
        UserDto userDto = new UserDto();
        userDto.setUsername("admin");

        User existingUser = new User();
        existingUser.setUsername("admin");

        Mockito.when(userRepository.findByUsername("admin"))
                .thenReturn(existingUser);

        UserDto response = userService.save(userDto);

        Assertions.assertFalse(response.isSuccess());
        Assertions.assertNotNull(response.getMessage());
    }

    @Test
    void updateUserNotFoundTest() {
        UserDto userDto = new UserDto();
        userDto.setUsername("admin");

        Mockito.when(userRepository.findByUsername("oldadmin"))
                .thenReturn(null);

        UserDto response = userService.update("oldadmin", userDto);

        Assertions.assertFalse(response.isSuccess());
        Assertions.assertNotNull(response.getMessage());
    }

    @Test
    void updateUserDuplicateUsernameTest() {
        UserDto userDto = new UserDto();
        userDto.setUsername("newadmin");

        User existingUser = new User();
        User duplicateUser = new User();

        Mockito.when(userRepository.findByUsername("oldadmin"))
                .thenReturn(existingUser);
        Mockito.when(userRepository.findByUsername("newadmin"))
                .thenReturn(duplicateUser);

        UserDto response = userService.update("oldadmin", userDto);

        Assertions.assertFalse(response.isSuccess());
        Assertions.assertNotNull(response.getMessage());
    }

    @Test
    void deleteUserTest() {
        Mockito.doNothing()
                .when(userRepository)
                .deleteByUsername("admin");

        userService.delete("admin");

        Mockito.verify(userRepository)
                .deleteByUsername("admin");
    }

    @Test
    void findAllUsersTest() {
        User user = new User();
        user.setUsername("admin");

        UserDto userDto = new UserDto();
        userDto.setUsername("admin");

        List<User> users = List.of(user);
        List<UserDto> userDtos = List.of(userDto);

        Pageable pageable = PageRequest.of(0, 20, Sort.by(new ArrayList<>()));
        Page<User> userPage =
                new PageImpl<>(users, pageable, users.size());

        Mockito.when(userRepository.findAll(pageable))
                .thenReturn(userPage);

        Mockito.when(modelMapper.map(
                Mockito.eq(users),
                Mockito.any(java.lang.reflect.Type.class)
        )).thenReturn(userDtos);

        List<UserDto> response = userService.findAll(pageable);

        Assertions.assertEquals(1, response.size());
    }

    @Test
    void updateUserSuccessSameUsernameTest() {
        UserDto userDto = new UserDto();
        userDto.setUsername("admin");
        userDto.setName("Admin User");
        userDto.setPhoneNo("12345");
        userDto.setRoles(List.of("ROLE_ADMIN"));

        User existingUser = new User();
        existingUser.setUsername("admin");

        Mockito.when(userRepository.findByUsername("admin"))
                .thenReturn(existingUser);

        UserDto response = userService.update("admin", userDto);

        Assertions.assertTrue(response.isSuccess());
        Assertions.assertEquals("User updated successfully", response.getMessage());

        Mockito.verify(userRepository).save(existingUser);
    }

    @Test
    void updateUserSuccessDifferentUsernameTest() {
        UserDto userDto = new UserDto();
        userDto.setUsername("newadmin");
        userDto.setName("New Admin");

        User existingUser = new User();
        existingUser.setUsername("oldadmin");

        Mockito.when(userRepository.findByUsername("oldadmin"))
                .thenReturn(existingUser);
        Mockito.when(userRepository.findByUsername("newadmin"))
                .thenReturn(null);

        UserDto response = userService.update("oldadmin", userDto);

        Assertions.assertTrue(response.isSuccess());
        Assertions.assertEquals("User updated successfully", response.getMessage());

        Mockito.verify(userRepository).save(existingUser);
    }

    @Test
    void findAllUsersEmptyTest() {
        Pageable pageable = PageRequest.of(0, 20, Sort.by(new ArrayList<>()));

        Page<User> emptyPage =
                new PageImpl<>(List.of(), pageable, 0);

        Mockito.when(userRepository.findAll(pageable))
                .thenReturn(emptyPage);

        Mockito.when(modelMapper.map(
                Mockito.eq(List.of()),
                Mockito.any(java.lang.reflect.Type.class)
        )).thenReturn(List.of());

        List<UserDto> response = userService.findAll(pageable);

        Assertions.assertTrue(response.isEmpty());
    }

    @Test
    void findByUserNameNull_DoesNotInvokeMapper() {

        Mockito.when(userRepository.findByUsername("admin"))
                .thenReturn(null);

        UserDto response = userService.findByUserName("admin");

        Assertions.assertNull(response);
        Mockito.verify(modelMapper, Mockito.never())
                .map(Mockito.any(), Mockito.eq(UserDto.class));
    }

    @Test
    void updateUserSameUsernameDifferentCaseTest() {

        UserDto userDto = new UserDto();
        userDto.setUsername("ADMIN");

        User existingUser = new User();
        existingUser.setUsername("admin");

        Mockito.when(userRepository.findByUsername("admin"))
                .thenReturn(existingUser);

        UserDto response = userService.update("admin", userDto);

        Assertions.assertTrue(response.isSuccess());
        Mockito.verify(userRepository).save(existingUser);
    }

    @Test
    void saveUserPasswordIsEncodedTest() {

        UserDto userDto = new UserDto();
        userDto.setUsername("admin");
        userDto.setPassword("raw");

        User user = new User();

        Mockito.when(userRepository.findByUsername("admin"))
                .thenReturn(null);
        Mockito.when(modelMapper.map(userDto, User.class))
                .thenReturn(user);
        Mockito.when(passwordEncoder.encode("raw"))
                .thenReturn("encoded");

        userService.save(userDto);

        Assertions.assertEquals("encoded", user.getPassword());
    }
}
