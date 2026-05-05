package com.ust.pos.api.user;

import com.ust.pos.dto.UserDto;
import com.ust.pos.role.service.RoleService;
import com.ust.pos.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserApiController {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @GetMapping("/api/list")
    public List<UserDto> home() {
        return userService.findAll();
    }

    @GetMapping("/get")
    public UserDto update(@RequestParam String username, @RequestBody UserDto userDto) {
        return userService.findByUserName(username);
    }

    @PostMapping("/update")
    public UserDto updatePost(@RequestBody UserDto userDto) {
        return userService.update(userDto);

    }

    @GetMapping("/delete")
    public boolean delete(@RequestParam String username) {
        try {
            userService.delete(username);
        } catch (Exception e) {
            return false;
        }
        return true;
    }
}