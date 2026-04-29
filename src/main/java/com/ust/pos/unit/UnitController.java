package com.ust.pos.unit;

import com.ust.pos.dto.UnitDto;
import com.ust.pos.unit.service.UnitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/unit")
public class UnitController {

    @Autowired
    private UnitService unitService;

    @GetMapping("/list")
    public String home(Model unit)
    {
        unit.addAttribute("units", unitService.findAll());
        return "unit/list";
    }

    @GetMapping("/add")
    public String add(Model unit, @ModelAttribute UnitDto unitDto)
    {
        unit.addAttribute("units", unitService.findAll());
        return "unit/add";
    }

    @PostMapping("/add")
    public String addModel(Model unit, @ModelAttribute UnitDto unitDto)
    {
        UnitDto unitDto1 = unitService.save(unitDto);
        if(!unitDto.isSuccess())
        {
            unit.addAttribute("message", unitDto1.getMessage());
            unit.addAttribute("units", unitService.findAll());
            return "unit/add";
        }
        return "redirect:/unit/list";
    }

    @GetMapping("/get")
    public String update(Model unit, @RequestParam String identifier)
    {
        UnitDto unitDto = unitService.findByIdentifier(identifier);
        unit.addAttribute("unit", unitDto);
        unit.addAttribute("units", unitService.findAll());
        return "unit/unit";
    }

    @PostMapping("/update")
    public String updatePost(Model unit, @ModelAttribute UnitDto unitDto)
    {
        UnitDto unitDto1 = unitService.update(unitDto);
        if(!unitDto1.isSuccess())
        {
            unit.addAttribute("message", unitDto1.getMessage());
            unit.addAttribute("units", unitService.findAll());
            return "unit/update";
        }
        return "redirect:/unit/list";
    }

    @GetMapping("/delete")
    public String delete(Model unit, @RequestParam String identifier)
    {
        unitService.delete(identifier);
        return "redirect:/unit/list";
    }
    @PostMapping("/toggleStatus")
    public String toggleStatus(@RequestParam String identifier) {
        unitService.toggleStatus(identifier);
        return "redirect:/unit/list";
    }
}