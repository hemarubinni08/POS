package com.ust.pos.api.userApi;

import com.ust.pos.dto.UserDto;
import com.ust.pos.role.service.RoleService;
import com.ust.pos.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserApiController {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @GetMapping("/list")
    public List<UserDto> home(Model model) {
        return  userService.findAll();
    }

    @GetMapping("/get")
    public UserDto update( @RequestParam String username) {
        return userService.findByUserName(username);

    }

    @PostMapping("/update")
    public UserDto updatePost( @RequestBody UserDto userDto) {
        return userService.update(userDto);
    }

    @GetMapping("/delete")
    public boolean delete( @RequestParam String username) {
        try{ userService.delete(username);}
        catch(Exception e){
            return false;}
        return true;
    }

    @PostMapping("/toggle-status")
    public UserDto toggle(@RequestParam String identifier){

        return userService.toggleStatus(identifier);
    }


    @GetMapping("/findByStatus")
    public List<UserDto> findByStatus() {

        return userService.findIfTrue();
    }
}