package com.ust.pos.user;

import com.ust.pos.dto.UserDto;
import com.ust.pos.role.service.RoleService;
import com.ust.pos.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@Transactional
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @GetMapping("/list")
    public String home(Model model) {
        model.addAttribute("users", userService.findAll());
        model.addAttribute("roles", roleService.findAll());
        return "user/list";
    }

    @GetMapping("/get")
    public String update(Model model, @RequestParam String username, @ModelAttribute UserDto userDto) {
        UserDto user = userService.findByUserName(username);
        model.addAttribute("userDto", user);
        model.addAttribute("roles", roleService.findAll());
        return "user/user";
    }

    @PostMapping("/update")
    public String updatePost(Model model, @ModelAttribute UserDto user) {
        UserDto response = userService.update(user);
        if (!response.isSuccess()) {
            UserDto userDto = userService.findByUserName(user.getUsername());
            model.addAttribute("message", response.getMessage());
            model.addAttribute("userDto", userDto);
            model.addAttribute("roles", roleService.findAll());
            return "user/user";
        }
        return "redirect:/user/list";
    }

    @GetMapping("/delete")
    public String delete(Model model, @RequestParam String username) {
        userService.delete(username);
        return "redirect:/user/list";
    }
}
