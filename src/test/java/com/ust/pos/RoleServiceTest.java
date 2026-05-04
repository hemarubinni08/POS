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
public class RoleServiceTest {
    @Mock
    private RoleService roleService;

    @Test
    void saveTest()
    {
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
    void saveTestFailure()
    {
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
        Assertions.assertEquals(false, response.isSuccess());

    }
}