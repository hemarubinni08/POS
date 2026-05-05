package com.ust.pos;

import com.ust.pos.dto.RoleDto;
import com.ust.pos.role.service.RoleService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

@ExtendWith(MockitoExtension.class)
public class RoleServiceTest {

    @Mock
    private RoleService roleService;

    @Test
    void saveTest() {
        RoleDto responseDto = new RoleDto();
        responseDto.setIdentifier("Admin");
        responseDto.setSuccess(true);

        RoleDto roleDto = new RoleDto();
        roleDto.setIdentifier("Admin");

        Mockito.when(roleService.save(roleDto)).thenReturn(responseDto);

        RoleDto response = roleService.save(roleDto);

        Assertions.assertEquals("Admin", response.getIdentifier());
        Assertions.assertTrue(response.isSuccess());
    }

    @Test
    void saveTestFailure() {
        RoleDto responseDto = new RoleDto();
        responseDto.setIdentifier("Admin");
        responseDto.setSuccess(false);

        RoleDto roleDto = new RoleDto();
        roleDto.setIdentifier("Admin");

        Mockito.when(roleService.save(roleDto)).thenReturn(responseDto);

        RoleDto response = roleService.save(roleDto);

        Assertions.assertEquals("Admin", response.getIdentifier());
        Assertions.assertFalse(response.isSuccess());
    }

    @Test
    void updateTest() {
        RoleDto responseDto = new RoleDto();
        responseDto.setIdentifier("Admin");
        responseDto.setSuccess(true);

        RoleDto roleDto = new RoleDto();
        roleDto.setIdentifier("Admin");

        Mockito.when(roleService.update(roleDto)).thenReturn(responseDto);

        RoleDto response = roleService.update(roleDto);

        Assertions.assertTrue(response.isSuccess());
    }

    @Test
    void deleteTest() {
        Mockito.when(roleService.delete("Admin")).thenReturn(true);

        boolean result = roleService.delete("Admin");

        Assertions.assertTrue(result);
    }

    @Test
    void findByIdentifierTest() {
        RoleDto dto = new RoleDto();
        dto.setIdentifier("Admin");

        Mockito.when(roleService.findByIdentifier("Admin")).thenReturn(dto);

        RoleDto response = roleService.findByIdentifier("Admin");

        Assertions.assertEquals("Admin", response.getIdentifier());
    }

    @Test
    void findAllTest() {
        RoleDto dto1 = new RoleDto();
        dto1.setIdentifier("Admin");

        RoleDto dto2 = new RoleDto();
        dto2.setIdentifier("User");

        List<RoleDto> roleList = List.of(dto1, dto2);

        Mockito.when(roleService.findAll()).thenReturn(roleList);

        List<RoleDto> response = roleService.findAll();

        Assertions.assertEquals(2, response.size());
        Assertions.assertEquals("Admin", response.get(0).getIdentifier());
        Assertions.assertEquals("User", response.get(1).getIdentifier());
    }
}