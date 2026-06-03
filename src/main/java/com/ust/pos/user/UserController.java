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

    private static final String USER_LIST = "user/list";
    private static final String USER_VIEW = "user/user";
    private static final String REDIRECT_USER_LIST = "redirect:/user/list";

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @GetMapping("/list")
    public String home(Model model) {
        model.addAttribute("users", userService.findAll());
        return USER_LIST;
    }

    @GetMapping("/get")
    public String update(@ModelAttribute UserDto userDto, Model model, @RequestParam String username) {
        UserDto response = userService.findByUserName(username);
        model.addAttribute("userDto", response);
        model.addAttribute("roles", roleService.findAll());
        return USER_VIEW;
    }

    @PostMapping("/update")
    public String updatePost(Model model, @ModelAttribute UserDto userDto) {
        UserDto response = userService.update(userDto);
        if (!response.isSuccess()) {
            model.addAttribute("message", response.getMessage());
        }
        return REDIRECT_USER_LIST;
    }

    @GetMapping("/delete")
    public String delete(@RequestParam String username) {
        userService.delete(username);
        return REDIRECT_USER_LIST;
    }
}
