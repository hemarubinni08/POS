package com.ust.pos.api.user;

import com.ust.pos.dto.UserDto;
import com.ust.pos.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserControllerApi {

    public static final String REDIRECT_ROLE_LIST = "redirect:/user/list";

    @Autowired
    private UserService userService;

    @GetMapping("/list")
    public List<UserDto> home() {
        return userService.findAll();
    }

    @PostMapping("/add")
    public UserDto addPost(@RequestBody UserDto userDto) {
        return userService.save(userDto);
    }

    @GetMapping("/get")
    public UserDto update(@RequestParam String username) {
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

    @GetMapping("/active")
    public List<UserDto> findAllActive() {
        return userService.findAllActive();
    }

    @PostMapping("/changestatus")
    public UserDto changeStatus(@RequestBody UserDto userDto) {
        return userService.updateStatus(userDto.getUsername(), userDto.isStatus());
    }
}
