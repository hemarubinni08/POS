package com.ust.pos.user;

import com.ust.pos.dto.UserDto;
import com.ust.pos.role.service.RoleService;
import com.ust.pos.user.service.UserService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/user")
public class UserController {

    public static final String MESSAGE = "message";
    public static final String ROLES = "roles";
    public static final String USER_USER = "user/user";
    @Autowired
    private UserService userService;

    @Autowired
    public RoleService roleService;


    @GetMapping("/list")
    public String home(Model model) {
        model.addAttribute("users", userService.findAll());
        model.addAttribute(ROLES,roleService.findAll());
        return "user/list";
    }

    @GetMapping("/get")
    public String update(Model model, @RequestParam String username) {

        UserDto response = userService.findByUserName(username);

        model.addAttribute("user", response);
        model.addAttribute(ROLES, roleService.findAll());

        return USER_USER;
    }


    @PostMapping("/update")
    public String updatePost(Model model,
                             @ModelAttribute UserDto userDto,
                             @RequestParam String oldUsername) {

        // ✅ 1. Fetch existing user using OLD username
        UserDto existingUser = userService.findByUserName(oldUsername);

        // Safety check
        if (existingUser == null) {
            model.addAttribute(MESSAGE, "User not found.");
            model.addAttribute(ROLES, roleService.findAll());
            return USER_USER;
        }

        // ✅ 2. If email changed, check uniqueness
        if (!oldUsername.equalsIgnoreCase(userDto.getUsername())) {

            UserDto emailCheck =
                    userService.findByUserName(userDto.getUsername());

            if (emailCheck != null) {
                model.addAttribute(MESSAGE,
                        "❌ Email already exists. Please use a new email.");
                model.addAttribute("user", userDto);
                model.addAttribute(ROLES, roleService.findAll());
                return USER_USER;
            }
        }

        // ✅ 3. Update user using OLD username
        UserDto response =
                userService.update(oldUsername, userDto);

        // ✅ 4. Handle update failure
        if (!response.isSuccess()) {
            model.addAttribute(MESSAGE, response.getMessage());
            model.addAttribute("user", userDto);
            model.addAttribute(ROLES, roleService.findAll());
            return USER_USER;
        }

        // ✅ 5. Success
        return "redirect:/user/list";
    }

    @GetMapping("/delete")
    public String delete(Model model, @RequestParam String username) {
        userService.delete(username);
        return "redirect:/user/list";
    }
}
