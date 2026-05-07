package com.ust.pos.api.racks;

import com.ust.pos.dto.RacksDto;
import com.ust.pos.racks.service.RacksService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/racks")
public class RacksControllerApi {

    public static final String REDIRECT_ROLE_LIST = "redirect:/racks/list";

    @Autowired
    private RacksService racksService;

    @GetMapping("/list")
    public List<RacksDto> home() {
        return racksService.findAll();
    }

    @PostMapping("/add")
    public RacksDto addPost(@RequestBody RacksDto racksDto) {
        return racksService.save(racksDto);
    }

    @GetMapping("/get")
    public RacksDto update(@RequestParam String identifier) {
        return racksService.findByIdentifier(identifier);
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

    @GetMapping("/active")
    public List<RacksDto> findAllActive() {
        return racksService.findAllActive();
    }

    @PostMapping("/changestatus")
    public RacksDto changeStatus(@RequestBody RacksDto racksDto) {
        return racksService.changeStatus(racksDto.getIdentifier(), racksDto.isStatus());
    }
}
