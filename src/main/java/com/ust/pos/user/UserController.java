package com.ust.pos.user;

import com.ust.pos.dto.UserDto;
import com.ust.pos.role.service.RoleService;
import com.ust.pos.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    //  List all users
    @GetMapping("/list")
    public String list(Model model) {
        model.addAttribute("users", userService.findAll());
        return "user/list";
    }

    //  Get user for update
    @GetMapping("/get")
    public String getUser(Model model, @RequestParam String username) {

        UserDto userDto = userService.findByUserName(username);

        if (userDto == null) {
            model.addAttribute("message", "User not found");
            return "user/user";
        }

        model.addAttribute("userDto", userDto);
        model.addAttribute("roles", roleService.findAll());

        return "user/user";
    }

    //  Update user
    @PostMapping("/update")
    public String updateUser(Model model,
                             @ModelAttribute("userDto") UserDto userDto) {

        UserDto response = userService.update(userDto);

        if (!response.isSuccess()) {
            model.addAttribute("roles", roleService.findAll());
            model.addAttribute("message", response.getMessage());
            return "user/user";
        }

        return "redirect:/user/list";
    }

    //  Delete user
    @GetMapping("/delete")
    public String delete(@RequestParam String username) {
        userService.delete(username);
        return "redirect:/user/list";
    }
}