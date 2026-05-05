package com.ust.pos.api.shelf;

import com.ust.pos.dto.ShelfDto;
import com.ust.pos.shelf.service.ShelfService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/shelf")
public class ShelfApiController {

    @Autowired
    private ShelfService shelfService;

    @GetMapping("/list")
    public List<ShelfDto> home(Model model) {
        return shelfService.findAll();
    }


    @PostMapping("/add")
    public ShelfDto doadd(@RequestBody ShelfDto shelfDto) {
        return shelfService.save(shelfDto);
    }

    @GetMapping("/get")
    public ShelfDto update(@RequestParam String identifier) {
        return shelfService.findByIdentifier(identifier);

    }

    @PostMapping("/update")
    public ShelfDto doupdate(@RequestBody ShelfDto shelfDto) {
        return shelfService.update(shelfDto);
    }

    @GetMapping("/delete")
    public boolean delete(@RequestParam String identifier) {
        try {
            shelfService.delete(identifier);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    @PostMapping("/toggleStatus")
    public boolean toggleStatus(@RequestParam String identifier) {
        try {
            shelfService.toggleStatus(identifier);
        } catch (Exception e) {
            return false;
        }
        return true;
    }
}
