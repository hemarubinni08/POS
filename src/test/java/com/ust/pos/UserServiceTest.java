package com.ust.pos;

import com.ust.pos.dto.UserDto;
import com.ust.pos.dto.WsDto;
import com.ust.pos.modell.User;
import com.ust.pos.modell.UserRepository;
import com.ust.pos.user.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @InjectMocks
    private UserServiceImpl service;

    @Mock
    private UserRepository repository;

    @Mock
    private PasswordEncoder encoder;

    @Mock
    private ModelMapper mapper;

    @Test
    void findByUserNameSuccessTest() {

        User user = new User();
        user.setUsername("admin");

        UserDto dto = new UserDto();
        dto.setUsername("admin");

        when(repository.findByUsernameAndDeletedFalse("admin"))
                .thenReturn(user);

        when(mapper.map(user, UserDto.class))
                .thenReturn(dto);

        UserDto result = service.findByUserName("admin");

        assertEquals("admin", result.getUsername());
    }

    @Test
    void findByUserNameNotFoundTest() {

        when(repository.findByUsernameAndDeletedFalse("admin"))
                .thenReturn(null);

        UserDto result = service.findByUserName("admin");

        assertFalse(result.isSuccess());
        assertEquals("User not found", result.getMessage());
    }

    @Test
    void saveSuccessTest() {

        UserDto dto = new UserDto();
        dto.setUsername("admin");
        dto.setPassword("raw");

        User user = new User();

        when(repository.findByUsername("admin"))
                .thenReturn(null);

        when(mapper.map(dto, User.class))
                .thenReturn(user);

        when(encoder.encode("raw"))
                .thenReturn("encoded");

        UserDto result = service.save(dto);

        verify(repository).save(user);

        assertEquals("encoded", user.getPassword());
        assertTrue(result.isSuccess());
        assertEquals(
                "User created successfully",
                result.getMessage()
        );
    }

    @Test
    void saveDuplicateUserTest() {

        UserDto dto = new UserDto();
        dto.setUsername("admin");

        User user = new User();
        user.setDeleted(false);

        when(repository.findByUsername("admin"))
                .thenReturn(user);

        UserDto result = service.save(dto);

        assertFalse(result.isSuccess());

        assertEquals(
                "User with username/email - admin already exists",
                result.getMessage()
        );
    }

    @Test
    void saveSoftDeletedUserTest() {

        UserDto dto = new UserDto();
        dto.setUsername("admin");

        User user = new User();
        user.setDeleted(true);

        when(repository.findByUsername("admin"))
                .thenReturn(user);

        UserDto result = service.save(dto);

        assertFalse(result.isSuccess());

        assertEquals(
                "User with username/email - admin already exists (Soft-Deleted)",
                result.getMessage()
        );
    }

    @Test
    void updateUserNotFoundTest() {

        UserDto dto = new UserDto();
        dto.setUsername("admin");

        when(repository.findByUsernameAndDeletedFalse("oldadmin"))
                .thenReturn(null);

        UserDto result =
                service.update("oldadmin", dto);

        assertFalse(result.isSuccess());
        assertEquals("User not found", result.getMessage());
    }

    @Test
    void updateDuplicateUsernameTest() {

        User existing = new User();
        existing.setUsername("oldadmin");

        UserDto dto = new UserDto();
        dto.setUsername("newadmin");

        when(repository.findByUsernameAndDeletedFalse("oldadmin"))
                .thenReturn(existing);

        when(repository.findByUsername("newadmin"))
                .thenReturn(new User());

        UserDto result =
                service.update("oldadmin", dto);

        assertFalse(result.isSuccess());

        assertEquals(
                "User with username/email - newadmin already exists",
                result.getMessage()
        );
    }

    @Test
    void updateSuccessTest() {

        User existing = new User();
        existing.setUsername("oldadmin");
        existing.setCreatedBy("admin");
        existing.setCreatedOn(LocalDateTime.now());

        UserDto dto = new UserDto();
        dto.setUsername("newadmin");
        dto.setName("Test User");
        dto.setPhoneNo("9999999999");
        dto.setRoles(List.of("ADMIN"));

        when(repository.findByUsernameAndDeletedFalse("oldadmin"))
                .thenReturn(existing);

        when(repository.findByUsername("newadmin"))
                .thenReturn(null);

        UserDto result =
                service.update("oldadmin", dto);

        assertTrue(result.isSuccess());

        assertEquals(
                "User updated successfully",
                result.getMessage()
        );

        verify(repository).save(existing);
    }

    @Test
    void deleteUserExistsTest() {

        User user = new User();

        when(repository.findByUsernameAndDeletedFalse("admin"))
                .thenReturn(user);

        service.delete("admin");

        verify(repository).save(user);
    }

    @Test
    void deleteUserNotFoundTest() {

        when(repository.findByUsernameAndDeletedFalse("admin"))
                .thenReturn(null);

        service.delete("admin");

        verify(repository, never()).save(any());
    }

    @Test
    void findAllTest() {

        Pageable pageable =
                PageRequest.of(0, 10);

        User user = new User();

        UserDto dto = new UserDto();

        Page<User> page =
                new PageImpl<>(
                        List.of(user),
                        pageable,
                        1
                );

        when(repository.findAllByDeletedFalse(pageable))
                .thenReturn(page);

        when(mapper.map(any(), any(Type.class)))
                .thenReturn(List.of(dto));

        WsDto<UserDto> result =
                service.findAll(pageable);

        assertEquals(1, result.getDtoList().size());
        assertEquals(1, result.getTotalRecords());
        assertEquals(1, result.getTotalPage());
    }

    @Test
    void findAllEmptyTest() {

        Pageable pageable =
                PageRequest.of(0, 10);

        Page<User> page =
                new PageImpl<>(
                        Collections.emptyList(),
                        pageable,
                        0
                );

        when(repository.findAllByDeletedFalse(pageable))
                .thenReturn(page);

        when(mapper.map(any(), any(Type.class)))
                .thenReturn(Collections.emptyList());

        WsDto<UserDto> result =
                service.findAll(pageable);

        assertTrue(result.getDtoList().isEmpty());
    }
}


