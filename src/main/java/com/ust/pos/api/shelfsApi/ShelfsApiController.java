package com.ust.pos.api.shelfsApi;

import com.ust.pos.dto.ShelfsDto;
import com.ust.pos.shelfs.service.ShelfsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/shelfs")
public class ShelfsApiController {

    @Autowired
    private ShelfsService shelfsService;

    @GetMapping("/list")
    public List<ShelfsDto> list() {
        return shelfsService.findAll();
    }

    @PostMapping("/add")
    public ShelfsDto add(@RequestBody ShelfsDto shelfsDto) {

        return shelfsService.save(shelfsDto);
    }


    @GetMapping("/get")
    public ShelfsDto get(@RequestParam String identifier) {

        return shelfsService.findByIdentifier(identifier);
    }


    @PostMapping("/update")
    public ShelfsDto update(@RequestBody ShelfsDto shelfsDto) {
        return shelfsService.update(shelfsDto);
    }


    @GetMapping("/delete")
    public boolean delete(@RequestParam String identifier) {
        try {
            shelfsService.delete(identifier);
            return true;
        } catch (Exception e) {
            return false;
        }
    }



}