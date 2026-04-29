package com.ust.pos.api.rack;

import com.ust.pos.dto.RackDto;
import com.ust.pos.dto.ShelfDto;
import com.ust.pos.rack.service.RackService;
import com.ust.pos.shelf.service.ShelfService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("rackApiController")
@RequestMapping("/api/rack")
public class RackController {

    @Autowired
    private RackService rackService;

    @Autowired
    private ShelfService shelfService;

    @GetMapping("/list")
    public List<RackDto> list() {
        return rackService.getAllRacks();
    }

    @GetMapping("/get")
    public RackDto get(@RequestParam Long id) {
        return rackService.getRack(id);
    }

    @PostMapping("/add")
    public RackDto add(@RequestBody RackDto rackDto) {
        return rackService.createRack(rackDto);
    }

    @PostMapping("/update")
    public RackDto update(@RequestBody RackDto rackDto) {
        return rackService.updateRack(rackDto);
    }

    @GetMapping("/delete")
    public boolean delete(@RequestParam Long id) {
        try {
            rackService.deleteRack(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @GetMapping("/shelves")
    public List<ShelfDto> shelves() {
        return shelfService.getAllShelves();
    }
}