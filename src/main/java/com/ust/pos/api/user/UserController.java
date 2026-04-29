package com.ust.pos.api.user;

import com.ust.pos.dto.UserDto;
import com.ust.pos.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("userApiController")
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;


    @GetMapping("/list")
    public List<UserDto> list() {

        return userService.findAll();
    }

    @GetMapping("/get")
    public UserDto get(@RequestParam String username) {

        return userService.findByUserName(username);
    }

    @PostMapping("/update")
    public UserDto update(@RequestBody UserDto userDto) {

        return userService.update(userDto);
    }

    @GetMapping("/delete")
    public boolean delete(@RequestParam String username) {
        try {
            userService.delete(username);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}