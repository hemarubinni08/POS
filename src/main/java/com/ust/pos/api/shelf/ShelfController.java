package com.ust.pos.api.shelf;

import com.ust.pos.dto.ShelfDto;
import com.ust.pos.shelf.service.ShelfService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("shelfApiController")
@RequestMapping("/api/shelf")
public class ShelfController {

    @Autowired
    private ShelfService shelfService;

    @GetMapping("/list")
    public List<ShelfDto> list() {
        return shelfService.getAllShelves();
    }

    @GetMapping("/get")
    public ShelfDto get(@RequestParam Long id) {
        return shelfService.getShelf(id);
    }

    @PostMapping("/add")
    public ShelfDto add(@RequestBody ShelfDto shelfDto) {
        return shelfService.createShelf(shelfDto);
    }

    @PostMapping("/update")
    public ShelfDto update(@RequestBody ShelfDto shelfDto) {
        return shelfService.updateShelf(shelfDto);
    }

    @GetMapping("/delete")
    public boolean delete(@RequestParam Long id) {
        try {
            shelfService.deleteShelf(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}