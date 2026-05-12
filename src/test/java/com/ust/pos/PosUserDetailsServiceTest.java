package com.ust.pos;

import com.ust.pos.dto.UserDto;
import com.ust.pos.user.service.UserService;
import com.ust.pos.user.service.impl.PosUserDetailsService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Arrays;
import java.util.Collections;

@ExtendWith(MockitoExtension.class)
class PosUserDetailsServiceTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private PosUserDetailsService posUserDetailsService;

    @Test
    void loadUserByUsername_Success_WithRoles() {
        // Arrange
        UserDto userDto = new UserDto();
        userDto.setUsername("admin");
        userDto.setPassword("encoded_pass");
        userDto.setRoles(Arrays.asList("ROLE_ADMIN", "ROLE_USER"));

        Mockito.when(userService.findByUserName("admin")).thenReturn(userDto);

        // Act
        UserDetails result = posUserDetailsService.loadUserByUsername("admin");

        // Assert
        Assertions.assertNotNull(result);
        Assertions.assertEquals("admin", result.getUsername());
        // This will now pass because the service maps the roles
        Assertions.assertFalse(result.getAuthorities().isEmpty());
        Assertions.assertEquals(2, result.getAuthorities().size());
    }

    @Test
    void loadUserByUsername_Success_NoRoles() {
        // Arrange
        UserDto userDto = new UserDto();
        userDto.setUsername("guest");
        userDto.setPassword("pass");
        userDto.setRoles(Collections.emptyList());

        Mockito.when(userService.findByUserName("guest")).thenReturn(userDto);

        // Act
        UserDetails result = posUserDetailsService.loadUserByUsername("guest");

        // Assert
        Assertions.assertTrue(result.getAuthorities().isEmpty());
    }

    @Test
    void loadUserByUsername_UserNotFound_ThrowsException() {
        // Arrange
        Mockito.when(userService.findByUserName("unknown")).thenReturn(null);

        // Act & Assert
        Assertions.assertThrows(UsernameNotFoundException.class, () -> {
            posUserDetailsService.loadUserByUsername("unknown");
        });
    }

    @Test
    void loadUserByUsername_NullRoles_ReturnsEmptyAuthorities() {
        // Arrange
        UserDto userDto = new UserDto();
        userDto.setUsername("nullUser");
        userDto.setPassword("pass");
        userDto.setRoles(null); // Testing the null check in service

        Mockito.when(userService.findByUserName("nullUser")).thenReturn(userDto);

        // Act
        UserDetails result = posUserDetailsService.loadUserByUsername("nullUser");

        // Assert
        Assertions.assertNotNull(result);
        Assertions.assertTrue(result.getAuthorities().isEmpty());
    }
}