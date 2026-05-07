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

    @GetMapping
    public List<ShelfDto> getAll() {
        return shelfService.getAllShelves();
    }

    @GetMapping("/{id}")
    public ShelfDto getById(@PathVariable Long id) {
        return shelfService.getShelf(id);
    }

    @PostMapping("/save")
    public ShelfDto save(@RequestBody ShelfDto shelfDto) {
        return shelfService.createShelf(shelfDto);
    }

    @PostMapping("/update/{id}")
    public ShelfDto update(@PathVariable Long id,
                           @RequestBody ShelfDto shelfDto) {
        shelfDto.setId(id);
        return shelfService.updateShelf(shelfDto);
    }

    @PostMapping("/delete/{id}")
    public boolean delete(@PathVariable Long id) {
        try {
            shelfService.deleteShelf(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @PostMapping("/toggle/{id}")
    public ShelfDto toggleStatus(@PathVariable Long id) {
        return shelfService.toggleStatus(id);
    }
}