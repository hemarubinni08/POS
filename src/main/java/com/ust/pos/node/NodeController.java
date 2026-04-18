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
    public String list(Model model) {
        model.addAttribute("nodes", nodeService.getNodesForRoles());
        return "node/list";
    }

    @GetMapping("/add")
    public String add(Model model, @ModelAttribute NodeDto nodeDto) {
        model.addAttribute("roles", roleService.findAll());
        return "node/add";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute NodeDto nodeDto) {
        nodeService.save(nodeDto);
        return "redirect:/node/list";
    }

    @GetMapping("/get")
    public String update(@RequestParam String identifier, Model model, @ModelAttribute NodeDto nodeDto) {
        model.addAttribute("roles", roleService.findAll());
        model.addAttribute("node", nodeService.findByIdentifier(identifier));
        return "node/node";
    }

    @PostMapping("/update")
    public String updatePost(@ModelAttribute NodeDto nodeDto) {
        nodeService.update(nodeDto);
        return "redirect:/node/list";
    }

    @GetMapping("/delete")
    public String delete(@RequestParam String identifier) {
        nodeService.delete(identifier);
        return "redirect:/node/list";
    }
}
