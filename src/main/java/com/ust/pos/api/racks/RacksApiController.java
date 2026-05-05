package com.ust.pos.api.racks;

import com.ust.pos.dto.RacksDto;
import com.ust.pos.dto.ShelfDto;
import com.ust.pos.racks.service.RacksService;
import com.ust.pos.shelf.service.ShelfService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
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

    @GetMapping("/add")
    public List<ShelfDto> add(@RequestBody RacksDto racksDto) {
        return shelfService.findAllByStatus();
    }

    @PostMapping("/add")
    public RacksDto addPost(@RequestBody RacksDto racksDto) {
        return racksService.save(racksDto);
    }

    @GetMapping("/get")
    public Map<String, Object> update(@RequestParam String identifier) {
        RacksDto racksDto = racksService.findByIdentifier(identifier);
        Map<String, Object> response = new HashMap<>();
        response.put("racksDto", racksDto);
        response.put("shelf", shelfService.findAllByStatus());
        return response;
    }

    @PostMapping("/update")
    public RacksDto updatePost(@RequestBody RacksDto racksDto) {
        return racksService.update(racksDto);
    }

    @GetMapping("/delete")
    public boolean delete(@RequestParam String identifier) {
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