package com.ust.pos.api.nodeApi;

import com.ust.pos.dto.NodeDto;
import com.ust.pos.node.service.NodeService;
import com.ust.pos.role.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/node")
public class NodeApiController {

    @Autowired
    private NodeService nodeService;

    @Autowired
    private RoleService roleService;


    @GetMapping("/list")
    public List<NodeDto> list() {

        return nodeService.findAll();
    }


    @PostMapping("/add")
    public NodeDto add(@RequestBody NodeDto nodeDto) {

        return nodeService.save(nodeDto);
    }

    @GetMapping("/get")
    public NodeDto get(@RequestParam String identifier) {

        return nodeService.findByIdentifier(identifier);
    }

    @PostMapping("/update")
    public NodeDto update(@RequestBody NodeDto nodeDto) {

        return nodeService.update(nodeDto);
    }

    @GetMapping("/delete")
    public boolean delete(@RequestParam String identifier) {
        try {
            nodeService.delete(identifier);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @GetMapping("/roles")
    public Object roles() {
        return roleService.findAll();
    }
}
