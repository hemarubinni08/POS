package com.ust.pos;

import com.ust.pos.dto.RoleDto;
import com.ust.pos.modell.Role;
import com.ust.pos.modell.RoleRepository;
import com.ust.pos.role.service.impl.RoleServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;

import java.lang.reflect.Type;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RoleServiceTest {

    @InjectMocks
    private RoleServiceImpl service;

    @Mock
    private RoleRepository repository;

    @Mock
    private ModelMapper mapper;

    @Test
    void findByIdentifier_shouldHandleBothCases() {
        Role role = new Role();
        role.setIdentifier("Admin");

        RoleDto dto = new RoleDto();
        dto.setIdentifier("Admin");

        when(repository.findByIdentifier("Admin")).thenReturn(role);
        when(mapper.map(role, RoleDto.class)).thenReturn(dto);

        assertEquals("Admin",
                service.findByIdentifier("Admin").getIdentifier());

        when(repository.findByIdentifier("X")).thenReturn(null);
        when(mapper.map(null, RoleDto.class)).thenReturn(null);

        assertNull(service.findByIdentifier("X"));
    }

    @Test
    void save_shouldHandleAllCases() {
        RoleDto dto = new RoleDto();
        dto.setIdentifier("Admin");

        Role role = new Role();
        role.setIdentifier("Admin");

        when(repository.findByIdentifier("Admin")).thenReturn(null);
        when(mapper.map(dto, Role.class)).thenReturn(role);

        RoleDto result = service.save(dto);

        verify(repository).save(role);
        assertTrue(result.isSuccess() || result.getMessage() == null);

        when(repository.findByIdentifier("Admin")).thenReturn(role);

        RoleDto duplicate = service.save(dto);

        assertFalse(duplicate.isSuccess());
        assertTrue(duplicate.getMessage().contains("already exists"));
    }

    @Test
    void update_shouldHandleBothCases() {
        RoleDto dto = new RoleDto();
        dto.setIdentifier("Admin");

        Role role = new Role();
        role.setIdentifier("Admin");

        when(repository.findByIdentifier("Admin")).thenReturn(role);

        service.update(dto);

        verify(mapper).map(dto, role);
        verify(repository).save(role);

        when(repository.findByIdentifier("X")).thenReturn(null);
        dto.setIdentifier("X");

        RoleDto fail = service.update(dto);

        assertFalse(fail.isSuccess());
        assertTrue(fail.getMessage().contains("not found"));
    }

    @Test
    void delete_shouldCallRepository() {
        service.delete("Admin");
        verify(repository).deleteByIdentifier("Admin");
    }

    @Test
    void findAll_shouldHandleDataAndEmpty() {
        Pageable pageable = PageRequest.of(0, 10);
        Type type = new TypeToken<List<RoleDto>>() {}.getType();

        Role role = new Role();
        role.setIdentifier("Admin");

        RoleDto dto = new RoleDto();
        dto.setIdentifier("Admin");

        Page<Role> page =
                new PageImpl<>(List.of(role), pageable, 1);

        when(repository.findAll(pageable)).thenReturn(page);
        when(mapper.map(any(), eq(type))).thenReturn(List.of(dto));

        assertEquals(1, service.findAll(pageable).size());

        Page<Role> empty =
                new PageImpl<>(List.of(), pageable, 0);

        when(repository.findAll(pageable)).thenReturn(empty);
        when(mapper.map(eq(List.of()), eq(type))).thenReturn(List.of());

        assertTrue(service.findAll(pageable).isEmpty());
    }
}
