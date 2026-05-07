package com.ust.pos.api.shelf;

import com.ust.pos.dto.ShelfDto;
import com.ust.pos.shelf.service.ShelfService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/shelf")
public class ShelfControllerApi {

    @Autowired
    private ShelfService shelfService;

    //  LIST 
    @GetMapping("/list")
    public List<ShelfDto> list() {
        return shelfService.findAll();
    }

    //  ADD 
    @PostMapping("/add")
    public ShelfDto add(@RequestBody ShelfDto shelfDto) {
        return shelfService.save(shelfDto);
    }

    //  GET 
    @GetMapping("/get")
    public ShelfDto get(@RequestParam String identifier) {
        return shelfService.findByIdentifier(identifier);
    }

    //  UPDATE 
    @PostMapping("/update")
    public ShelfDto update(@RequestBody ShelfDto shelfDto) {
        return shelfService.update(shelfDto);
    }

    //  DELETE 
    @GetMapping("/delete")
    public Boolean delete(@RequestParam String identifier) {
        try {
            shelfService.delete(identifier);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    //  ACTIVE LIST 
    @GetMapping("/active")
    public List<ShelfDto> activeShelves() {
        return shelfService.getActiveShelves();
    }

    //  TOGGLE (DTO RETURN LIKE MODELS) 
    @GetMapping("/toggle")
    public ShelfDto toggle(@RequestParam String identifier) {
        return shelfService.toggleStatus(identifier);
    }
}