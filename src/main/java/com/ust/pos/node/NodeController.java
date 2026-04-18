package com.ust.pos.node;

import com.ust.pos.dto.NodeDto;
import com.ust.pos.node.service.NodeService;
import com.ust.pos.role.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/node")
public class NodeController {

    @Autowired
    private NodeService nodeService;

    @Autowired
    private RoleService roleService;

    @GetMapping("/list")
    public String home(Model model) {
        model.addAttribute("nodes", nodeService.findAll());
        return "node/list";
    }

    @GetMapping("/add")
    public String add(Model model, @ModelAttribute NodeDto userDto) {
        return "node/add";
    }

    @PostMapping("/add")
    public String addPost(Model model, @ModelAttribute NodeDto userDto) {
        NodeDto response = nodeService.save(userDto);
        if (!response.isSuccess()) {
            model.addAttribute("message", response.getMessage());
        }
        return "redirect:/node/list";
    }

    @GetMapping("/get")
    public String update(Model model, @RequestParam String identifier) {
        NodeDto response = nodeService.findByIdentifier(identifier);
        model.addAttribute("node", response);
        model.addAttribute("role", roleService.findAll());
        return "node/node";
    }

    @PostMapping("/update")
    public String updatePost(Model model, @ModelAttribute NodeDto userDto) {
        NodeDto response = nodeService.update(userDto);
        if (!response.isSuccess()) {
            model.addAttribute("message", response.getMessage());
            model.addAttribute("node", userDto);
        }
        return "redirect:/node/list";
    }

    @GetMapping("/delete")
    public String delete(Model model, @RequestParam String identifier) {
        nodeService.delete(identifier);
        return "redirect:/node/list";
    }
}
