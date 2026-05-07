package com.ust.pos.api.node;

import com.ust.pos.dto.NodeDto;
import com.ust.pos.node.service.NodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/node")
public class NodeControllerApi {

    @Autowired
    private NodeService nodeService;

    @GetMapping("/list")
    public List<NodeDto> home() {
        return nodeService.findAll();
    }

    @PostMapping("/add")
    public NodeDto addPost(@RequestBody NodeDto nodeDto) {
        return nodeService.save(nodeDto);
    }

    @GetMapping("/get")
    public NodeDto update(@RequestParam String identifier) {
        return nodeService.findByIdentifier(identifier);
    }

    @PostMapping("/update")
    public NodeDto updatePost(@RequestBody NodeDto nodeDto) {
        return nodeService.update(nodeDto);
    }

    @GetMapping("/delete")
    public boolean delete(@RequestParam String identifier) {
        try {
            nodeService.delete(identifier);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    @GetMapping("/active")
    public List<NodeDto> findAllActive() {
        return nodeService.findAllActive();
    }

    @PostMapping("/changestatus")
    public NodeDto changeStatus(@RequestBody NodeDto nodeDto) {
        return nodeService.updateStatus(nodeDto.getIdentifier(), nodeDto.isStatus());
    }
}
