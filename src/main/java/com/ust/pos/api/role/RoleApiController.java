package com.ust.pos.api.role;

import com.ust.pos.dto.RoleDto;
import com.ust.pos.role.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/role")
public class RoleApiController {

    @Autowired
    private RoleService roleService;

    @GetMapping("/list")
    public List<RoleDto> home() {
        return roleService.findAll();
    }

    @PostMapping("/add")
    public RoleDto addPost(@RequestBody RoleDto roleDto) {
        return roleService.save(roleDto);
    }

    @GetMapping("/get")
    public RoleDto update(@RequestParam String identifier) {
        return roleService.findByIdentifier(identifier);
    }

    @PostMapping("/update")
    public RoleDto updatePost(@RequestBody RoleDto roleDto) {
        return roleService.update(roleDto);
    }

    @GetMapping("/delete")
    public boolean delete(@RequestParam String identifier) {
        try {
            roleService.delete(identifier);
        } catch (Exception e) {
            return false;
        }
        return true;
    }
}