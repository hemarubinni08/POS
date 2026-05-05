package com.ust.pos.api.shelf;

import com.ust.pos.dto.ShelfDto;
import com.ust.pos.shelf.service.ShelfService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
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

    @PostMapping("status")
    public boolean updateStatus(@RequestParam String identifier, boolean status) {
        try {
            shelfService.updateStatusOnly(identifier, status);
        } catch (Exception e) {
            return false;
        }
        return true;
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
}
