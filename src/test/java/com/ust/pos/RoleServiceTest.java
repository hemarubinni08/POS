package com.ust.pos;

import com.ust.pos.dto.RoleDto;
import com.ust.pos.model.Role;
import com.ust.pos.model.RoleRepository;
import com.ust.pos.role.service.impl.RoleServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

@ExtendWith(MockitoExtension.class)
class RoleServiceTest {

    @InjectMocks
    private RoleServiceImpl roleService;
    @Mock
    private RoleRepository roleRepository;
    @Mock
    private ModelMapper modelMapper;

    @Test
    void saveTest() {

        //request data
        RoleDto roleDto = new RoleDto();
        roleDto.setIdentifier("Admin");

        Mockito.when(roleRepository.findByIdentifier("Admin")).thenReturn(null);
        Role role = new Role();

        Mockito.when(modelMapper.map(roleDto, Role.class)).thenReturn(role);
        Mockito.when(roleRepository.save(role)).thenReturn(role);
        RoleDto response = roleService.save(roleDto);
        Assertions.assertEquals("Admin", response.getIdentifier());
        Assertions.assertEquals(true, response.isSuccess());
    }

    @Test
    void saveTestFailure() {
        //request data
        RoleDto roleDto = new RoleDto();
        roleDto.setIdentifier("Admin");
        Role role = new Role();
        Mockito.when(roleRepository.findByIdentifier("Admin")).thenReturn(role);
        RoleDto response = roleService.save(roleDto);
        Assertions.assertEquals("Admin", response.getIdentifier());
        Assertions.assertNotNull(response.getMessage(), "Message cannot be null");
        Assertions.assertEquals(false, response.isSuccess());

    }

    @Test
    void findByIdentifierTest() {
        Role role = new Role();
        role.setIdentifier("Admin");
        RoleDto roleDto = new RoleDto();
        roleDto.setIdentifier("Admin");
        Mockito.when(roleRepository.findByIdentifier("Admin")).thenReturn(role);
        Mockito.when(modelMapper.map(role, RoleDto.class)).thenReturn(roleDto);
        RoleDto response = roleService.findByIdentifier("Admin");
        Assertions.assertEquals("Admin", response.getIdentifier());
    }

    @Test
    void updateTest() {
        RoleDto roleDto = new RoleDto();
        roleDto.setIdentifier("Admin");
        Role existingRole = new Role();
        existingRole.setIdentifier("Admin");
        Mockito.when(roleRepository.findByIdentifier("Admin"))
                .thenReturn(existingRole);
        Mockito.when(roleRepository.save(existingRole))
                .thenReturn(existingRole);
        RoleDto response = roleService.update(roleDto);
        Assertions.assertTrue(response.isSuccess());
    }

    @Test
    void updateTestFailure() {
        RoleDto roleDto = new RoleDto();
        roleDto.setIdentifier("Admin");
        Mockito.when(roleRepository.findByIdentifier("Admin"))
                .thenReturn(null);
        RoleDto response = roleService.update(roleDto);
        Assertions.assertFalse(response.isSuccess());
    }

    @Test
    void deleteTest() {
        Mockito.doNothing().when(roleRepository)
                .deleteByIdentifier("Admin");
        roleService.delete("Admin");
        Mockito.verify(roleRepository).deleteByIdentifier("Admin");
    }

    @Test
    void findAllTest() {

        // ARRANGE
        Role role = new Role();
        role.setIdentifier("Admin");

        RoleDto roleDto = new RoleDto();
        roleDto.setIdentifier("Admin");

        List<Role> roles = List.of(role);
        List<RoleDto> roleDtos = List.of(roleDto);

        Pageable pageable = PageRequest.of(0, 10);
        Page<Role> rolePage =
                new PageImpl<>(roles, pageable, roles.size());

        Mockito.when(roleRepository.findAll(pageable))
                .thenReturn(rolePage);

        Mockito.when(modelMapper.map(
                Mockito.eq(roles),
                Mockito.any(java.lang.reflect.Type.class)
        )).thenReturn(roleDtos);

        // ACT
        List<RoleDto> response = roleService.findAll(pageable);

        // ASSERT
        Assertions.assertNotNull(response);
        Assertions.assertEquals(1, response.size());
        Assertions.assertEquals("Admin", response.get(0).getIdentifier());
    }

    @Test
    void toggleStatusSuccessTest() {

        // ARRANGE
        Role role = new Role();
        role.setIdentifier("Admin");
        role.setStatus(false); // currently inactive
        RoleDto roleDto = new RoleDto();
        roleDto.setIdentifier("Admin");
        roleDto.setStatus(true); // after toggle should be active
        // MOCK
        // role exists in DB
        Mockito.when(roleRepository.findByIdentifier("Admin")).thenReturn(role);
        // after save, role status is updated
        Mockito.when(roleRepository.save(role)).thenReturn(role);
        // mapper returns roleDto
        Mockito.when(modelMapper.map(role,RoleDto.class)).thenReturn(roleDto);
        // ACT
        RoleDto response = roleService.toggleStatus("Admin", true);
        // ASSERT
        Assertions.assertEquals("Admin", response.getIdentifier());
        Assertions.assertTrue(response.isStatus()); // status should be true now
    }

    @Test
    void changeRoleStatusFailureTest() {
        // ARRANGE - role does NOT exist in DB
        RoleDto roleDto = new RoleDto();
        roleDto.setIdentifier("Admin");
        // MOCK
        // role not found → returns null
        Mockito.when(roleRepository.findByIdentifier("Admin")).thenReturn(null);
        // ACT
        RoleDto response = roleService.toggleStatus("Admin", true);
        // ASSERT
        // since role is null, modelMapper.map(null,RoleDto.class) returns null
        Assertions.assertNull(response);
        // verify save was NEVER called because role was null
        Mockito.verify(roleRepository, Mockito.never()).save(Mockito.any());
    }
    @Test
    void findActiveRoleTest() {

        // ARRANGE
        Role role = new Role();
        role.setIdentifier("RACK_01");
        role.setStatus(true);

        RoleDto roleDto = new RoleDto();
        roleDto.setIdentifier("RACK_01");
        roleDto.setStatus(true);

        List<Role> activeRole = List.of(role);
        List<RoleDto> activeRoleDtos = List.of(roleDto);

        Mockito.when(roleRepository.findByStatusTrue()).thenReturn(activeRole);
        Mockito.when(modelMapper.map(
                Mockito.eq(activeRole),
                Mockito.any(java.lang.reflect.Type.class)
        )).thenReturn(activeRoleDtos);

        // ACT
        List<RoleDto> response = roleService.findActiveRole();

        // ASSERT
        Assertions.assertNotNull(response);
        Assertions.assertEquals(1, response.size());
        Assertions.assertEquals("RACK_01", response.get(0).getIdentifier());
        Assertions.assertTrue(response.get(0).isStatus());
    }
}