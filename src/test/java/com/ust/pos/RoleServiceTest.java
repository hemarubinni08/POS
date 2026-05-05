package com.ust.pos;

import com.ust.pos.dto.RoleDto;
import com.ust.pos.role.service.RoleService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class RoleServiceTest {

    @Mock
    private RoleService roleService; // mocked service

    @Test
    void saveTest() {
        RoleDto roleDto = new RoleDto();
        roleDto.setIdentifier("Admin");
        roleDto.setSuccess(true);

        Mockito.when(roleService.save(Mockito.any(RoleDto.class)))
                .thenReturn(roleDto);

        RoleDto response = roleService.save(roleDto);

        Assertions.assertEquals("Admin", response.getIdentifier());
        Assertions.assertTrue(response.isSuccess());
    }

    @Test
    void saveTestFailure() {
        RoleDto roleDto = new RoleDto();
        roleDto.setIdentifier("Admin");
        roleDto.setSuccess(false);
        roleDto.setMessage("Role with identifier - Admin already exists");

        Mockito.when(roleService.save(Mockito.any(RoleDto.class))).thenReturn(roleDto);

        RoleDto response = roleService.save(roleDto);

        Assertions.assertEquals("Admin", response.getIdentifier());
        Assertions.assertFalse(response.isSuccess());
        Assertions.assertEquals(
                "Role with identifier - Admin already exists",
                response.getMessage()
        );
    }

    @Test
    void updateTestSuccess() {

        // Assume its DB response data
        RoleDto responseDto = new RoleDto();
        responseDto.setIdentifier("Admin");

        // Request data
        RoleDto roleDto = new RoleDto();
        roleDto.setIdentifier("Admin");

        Mockito.when(roleService.update(roleDto)).thenReturn(responseDto);

        RoleDto response = roleService.update(roleDto);

        Assertions.assertEquals("Admin", response.getIdentifier());
        Assertions.assertTrue(response.isSuccess());
    }

    @Test
    void updateTestFailure() {

        // Assume its DB response data
        RoleDto responseDto = new RoleDto();
        responseDto.setIdentifier("Admin");
        responseDto.setSuccess(false);
        responseDto.setMessage("Role with identifier - test not found");

        // Request data
        RoleDto roleDto = new RoleDto();
        roleDto.setIdentifier("test");

        Mockito.when(roleService.update(roleDto)).thenReturn(responseDto);

        RoleDto response = roleService.update(roleDto);

        Assertions.assertEquals("Admin", response.getIdentifier());
        Assertions.assertEquals(false, response.isSuccess());
    }
}