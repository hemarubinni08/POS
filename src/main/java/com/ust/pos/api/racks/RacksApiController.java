package com.ust.pos.api.racks;

import com.ust.pos.dto.RacksDto;
import com.ust.pos.racks.service.RacksService;
import com.ust.pos.shelf.service.ShelfService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/racks")
public class RacksApiController {

    public static final String REDIRECT_RACKS_LIST = "redirect:/racks/list";

    @Autowired
    private RacksService racksService;

    @Autowired
    private ShelfService shelfService;

    @GetMapping("/list")
    public List<RacksDto> home() {
        return racksService.findAll();
    }


    @PostMapping("/add")
    public RacksDto addPost(@RequestBody RacksDto racksDto) {
        return racksService.save(racksDto);
    }

    @GetMapping("/get")
    public RacksDto update(Model model, @RequestParam String identifier) {
        RacksDto racksDto = racksService.findByIdentifier(identifier);
        return racksDto;
    }

    @PostMapping("/update")
    public RacksDto updatePost(@RequestBody RacksDto racksDto) {
        RacksDto response = racksService.update(racksDto);
        return response;
    }

    @GetMapping("/delete")
    public boolean delete(Model model, @RequestParam String identifier) {
        try {
            racksService.delete(identifier);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    @PostMapping("/toggleStatus")
    public boolean toggleStatus(@RequestParam String identifier) {
        try {
            racksService.toggleStatus(identifier);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

}