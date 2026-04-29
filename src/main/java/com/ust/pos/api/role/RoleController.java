package com.ust.pos.api.role;

import com.ust.pos.dto.RoleDto;
import com.ust.pos.role.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("roleApiController")
@RequestMapping("/api/role")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @GetMapping("/list")
    public List<RoleDto> list() {
        return roleService.findAll();
    }

    @GetMapping("/get")
    public RoleDto get(@RequestParam String identifier) {
        return roleService.findByIdentifier(identifier);
    }

    @PostMapping("/add")
    public RoleDto add(@RequestBody RoleDto roleDto) {
        return roleService.save(roleDto);
    }

    @PostMapping("/update")
    public RoleDto update(@RequestBody RoleDto roleDto) {
        return roleService.update(roleDto);
    }

    @GetMapping("/delete")
    public boolean delete(@RequestParam String identifier) {
        try {
            roleService.delete(identifier);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}