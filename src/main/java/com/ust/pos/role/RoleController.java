package com.ust.pos.role;

import com.ust.pos.dto.RoleDto;
import com.ust.pos.role.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/role")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @GetMapping("/list")
    public String home(Model model) {
        model.addAttribute("roles", roleService.findAll());
        return "role/list";
    }

    @GetMapping("/add")
    public String add(Model model, @ModelAttribute RoleDto userDto) {
        return "role/add";
    }

    @PostMapping("/add")
    public String addPost(Model model, @ModelAttribute RoleDto userDto) {
        RoleDto response = roleService.save(userDto);
        if (!response.isSuccess()) {
            model.addAttribute("message", response.getMessage());
        }
        return "redirect:/role/list";
    }

    @GetMapping("/get")
    public String update(Model model, @RequestParam String identifier) {
        RoleDto roleDto = roleService.findByIdentifier(identifier);
        model.addAttribute("roleDto", roleDto);
        return "role/role";
    }

    @PostMapping("/update")
    public String updatePost(Model model, @ModelAttribute RoleDto roleDto) {
        RoleDto response = roleService.update(roleDto);
        if (!response.isSuccess()) {
            model.addAttribute("message", response.getMessage());
            model.addAttribute("roleDto", roleDto);
            return "role/role";
        }
        return "redirect:/role/list";
    }

    @GetMapping("/delete")
    public String delete(Model model, @RequestParam String identifier) {
        roleService.delete(identifier);
        return "redirect:/role/list";
    }
}
