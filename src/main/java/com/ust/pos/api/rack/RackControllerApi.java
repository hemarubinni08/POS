package com.ust.pos.api.rack;

import com.ust.pos.dto.RackDto;
import com.ust.pos.rack.service.RackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rack")
public class RackControllerApi {

    public static final String REDIRECT_ROLE_LIST = "redirect:/rack/list";
    @Autowired
    private RackService rackService;

    @GetMapping("/list")
    public List<RackDto> list() {
        return rackService.findAll();
    }

    @PostMapping("/add")
    public RackDto addPost(@RequestBody RackDto rackDto) {
        return rackService.save(rackDto);
    }

    @GetMapping("/get")
    public RackDto updatePage(@RequestParam String identifier) {
        return rackService.findByIdentifier(identifier);
    }

    @PostMapping("/update")
    public RackDto updatePost(@RequestBody RackDto rackDto) {
        return rackService.update(rackDto);
    }

    @GetMapping("/delete")
    public Boolean delete(@RequestParam String identifier) {

        try {
            rackService.delete(identifier);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    @GetMapping("/activeracks")
    public List<RackDto> getActiveRacks() {
        return rackService.getActiveRacks();
    }

    @PostMapping("/toggle")
    public RackDto toggleStatus(@RequestBody RackDto rackDto) {
        return rackService.toggleStatus(rackDto.getIdentifier());
    }


}