package com.ust.pos.api.racksapi;

import com.ust.pos.dto.RacksDto;
import com.ust.pos.racks.service.RacksService;
import com.ust.pos.shelfs.service.ShelfsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/racks")
public class RacksApiController {
    @Autowired
    private RacksService racksService;
    @Autowired
    private ShelfsService shelfsService;
    @GetMapping("/list")
    public List<RacksDto> home(Model model) {
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
    public RacksDto updatePost( @RequestBody RacksDto racksDto) {
        return racksService.update(racksDto);
    }

    @GetMapping("/delete")
    public boolean delete(@RequestParam String identifier) {
        try{ racksService.delete(identifier);}
        catch(Exception e){
            return false;
        }
        return true;
    }

    @PostMapping("/toggle-status")
    public RacksDto toggle(@RequestParam String identifier){
        return racksService.toggleStatus(identifier);
    }


    @GetMapping("/findByStatus")
    public List<RacksDto> findByStatus() {

        return racksService.findIfTrue();
    }
}