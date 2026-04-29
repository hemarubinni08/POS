package com.ust.pos.api.rackApi;

import com.ust.pos.dto.RackDto;
import com.ust.pos.rack.service.RackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rack")
public class RackApiController {

    @Autowired
    private RackService rackService;

    @GetMapping("/list")
    public List<RackDto> list() {
        return rackService.findAll();
    }

    @PostMapping("/add")
    public RackDto add(@RequestBody RackDto rackDto) {
        return rackService.save(rackDto);
    }


    @GetMapping("/get")
    public RackDto get(@RequestParam String identifier) {
        return rackService.findByIdentifier(identifier);
    }


    @PostMapping("/update")
    public RackDto update(@RequestBody RackDto rackDto) {
        return rackService.update(rackDto);
    }


    @GetMapping("/delete")
    public boolean delete(@RequestParam String identifier) {
        try {
            rackService.delete(identifier);
            return true;
        } catch (Exception e) {
            return false;
        }
    }


}