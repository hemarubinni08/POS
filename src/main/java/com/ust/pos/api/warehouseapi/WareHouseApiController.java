package com.ust.pos.api.warehouseapi;
import com.ust.pos.dto.WareHouseDto;
import com.ust.pos.warehouse.service.WareHouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/wareHouse")
public class WareHouseApiController {
    @Autowired
    private WareHouseService wareHouseService;

    @GetMapping("/list")
    public List<WareHouseDto> home(Model model) {
        return wareHouseService.findAll();
    }

    @PostMapping("/add")
    public WareHouseDto addPost(@RequestBody WareHouseDto wareHouseDto) {
        return wareHouseService.save(wareHouseDto);
    }

    @GetMapping("/get")
    public WareHouseDto update(@RequestParam String identifier) {
        return wareHouseService.findByIdentifier(identifier);
    }

    @PostMapping("/update")
    public WareHouseDto updatePost(@RequestBody WareHouseDto wareHouseDto) {
        return wareHouseService.update(wareHouseDto);
    }

    @GetMapping("/delete")
    public boolean delete(@RequestParam String identifier) {
        try{ wareHouseService.delete(identifier);}
        catch (Exception e){
            return false;
        }
        return true;
    }

    @PostMapping("/toggle-status")
    public WareHouseDto toggle(@RequestParam String identifier){

        return wareHouseService.toggleStatus(identifier);
    }

    @GetMapping("/findByStatus")
    public List<WareHouseDto> findByStatus() {

        return wareHouseService.findIfTrue();
    }
}