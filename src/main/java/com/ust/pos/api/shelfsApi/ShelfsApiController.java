package com.ust.pos.api.shelfsApi;

import com.ust.pos.dto.ShelfsDto;
import com.ust.pos.shelfs.service.ShelfsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/shelfs")
public class ShelfsApiController {
    @Autowired
    private ShelfsService shelfsService;

    @GetMapping("/list")
    public List<ShelfsDto> home() {
        return shelfsService.findAll();
    }

    @PostMapping("/add")
    public ShelfsDto addPost(@RequestBody ShelfsDto shelfsDto) {
        return shelfsService.save(shelfsDto);
    }


    @GetMapping("/get")
    public ShelfsDto update(@RequestParam String identifier) {
        return shelfsService.findByIdentifier(identifier);
    }

    @PostMapping("/update")
    public ShelfsDto updatePost(@RequestBody ShelfsDto shelfsDto) {
        return shelfsService.update(shelfsDto);
    }

    @GetMapping("/delete")
    public boolean delete(@RequestParam String identifier) {
        try{ shelfsService.delete(identifier);}
        catch(Exception e){
            return false;
        }
        return true;
    }

    @PostMapping("toggle-status")
    public ShelfsDto toggle(@RequestParam String identifier){
        return  shelfsService.toggleStatus(identifier);
    }

    @GetMapping("/findByStatus")
    public List<ShelfsDto> findByStatus() {

        return shelfsService.findIfTrue();
    }
}
