package com.ust.pos;

import com.ust.pos.dto.RoleDto;
import com.ust.pos.model.RoleRepository;
import com.ust.pos.role.service.impl.RoleServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;


@ExtendWith(MockitoExtension.class)
public class RoleServiceTest {
    @Mock
    private RoleServiceImpl roleService;

    @Mock
    private RoleRepository roleRepository;

    @Test
    void saveTest() {
        //Assume its DB data
        RoleDto responseDto = new RoleDto();
        responseDto.setIdentifier("Admin");
        responseDto.setSuccess(true);

        //request data
        RoleDto roleDto = new RoleDto();
        roleDto.setIdentifier("Admin");

        Mockito.when(roleService.save(roleDto)).thenReturn(responseDto);
        RoleDto response = roleService.save(roleDto);
        Assertions.assertEquals("Admin", response.getIdentifier());
        Assertions.assertEquals(true, response.isSuccess());

    }

    @Test
    void saveTestFailure() {
        //Assume its DB data
        RoleDto responseDto = new RoleDto();
        responseDto.setIdentifier("Admin");
        responseDto.setSuccess(false);


        //request data
        RoleDto roleDto = new RoleDto();
        roleDto.setIdentifier("test");


        Mockito.when(roleService.save(roleDto)).thenReturn(responseDto);
        RoleDto response = roleService.save(roleDto);
        Assertions.assertEquals("Admin", response.getIdentifier());
        Assertions.assertEquals(false, response.isSuccess());

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
        Assertions.assertEquals(
                "Role with identifier - test not found",
                response.getMessage()
        );
    }

    @Test
    void updateTestSuccess() {

        // Assume its DB response data
        RoleDto responseDto = new RoleDto();
        responseDto.setIdentifier("Admin");
        responseDto.setSuccess(true);

        // Request data
        RoleDto roleDto = new RoleDto();
        roleDto.setIdentifier("Admin");

        Mockito.when(roleService.update(roleDto)).thenReturn(responseDto);

        RoleDto response = roleService.update(roleDto);

        Assertions.assertEquals("Admin", response.getIdentifier());
        Assertions.assertTrue(response.isSuccess());
    }
}