package com.ust.pos.api.shelf;

import com.ust.pos.dto.ShelfDto;
import com.ust.pos.shelf.service.ShelfService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("shelfApiController")
@RequestMapping("/api/shelves")
public class ShelfController {

    @Autowired
    private ShelfService shelfService;

    // GET ALL SHELVES
    @GetMapping
    public List<ShelfDto> getAll() {
        return shelfService.getAllShelves();
    }

    // GET SHELF BY ID
    @GetMapping("/{id}")
    public ShelfDto getById(@PathVariable Long id) {
        return shelfService.getShelf(id);
    }

    // CREATE SHELF
    @PostMapping
    public ShelfDto create(@RequestBody ShelfDto shelfDto) {
        return shelfService.createShelf(shelfDto);
    }

    // UPDATE SHELF
    @PutMapping("/{id}")
    public ShelfDto update(@PathVariable Long id,
                           @RequestBody ShelfDto shelfDto) {
        shelfDto.setId(id);
        return shelfService.updateShelf(shelfDto);
    }

    // DELETE SHELF
    @DeleteMapping("/{id}")
    public boolean delete(@PathVariable Long id) {
        try {
            shelfService.deleteShelf(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // TOGGLE STATUS
    @PatchMapping("/{id}/toggle")
    public ShelfDto toggleStatus(@PathVariable Long id) {
        return shelfService.toggleStatus(id);
    }
}