package com.ust.pos.unit;

import com.ust.pos.dto.UnitDto;
import com.ust.pos.unit.service.UnitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/unit")
public class UnitController {

    private static final String REDIRECT_LIST = "redirect:/unit/list";
    public static final String UNIT_DTO = "unitDto";
    public static final String MESSAGE = "message";

    @Autowired
    private UnitService unitService;

    // LIST
    @GetMapping("/list")
    public String list(Model model, Pageable pageable) {
        model.addAttribute("units", unitService.findAll(pageable));
        return "unit/list";
    }

    // ADD PAGE
    @GetMapping("/add")
    public String addPage(Model model) {
        model.addAttribute(UNIT_DTO, new UnitDto());
        return "unit/add";
    }

    // SAVE
    @PostMapping("/add")
    public String save(@ModelAttribute(UNIT_DTO) UnitDto unitDto, Model model) {

        UnitDto response = unitService.save(unitDto);

        if (!response.isSuccess()) {
            model.addAttribute(MESSAGE, response.getMessage());
            return "unit/add";
        }

        return REDIRECT_LIST;
    }

    // EDIT PAGE
    @GetMapping("/get")
    public String edit(@RequestParam String identifier, Model model) {

        UnitDto unitDto = unitService.findByIdentifier(identifier);

        if (unitDto == null || !unitDto.isSuccess()) {
            model.addAttribute(MESSAGE, "Unit not found");
            return REDIRECT_LIST;
        }

        model.addAttribute(UNIT_DTO, unitDto);
        return "unit/unit";
    }

    // UPDATE
    @PostMapping("/update")
    public String update(@ModelAttribute(UNIT_DTO) UnitDto unitDto, Model model) {

        UnitDto response = unitService.update(unitDto);

        if (!response.isSuccess()) {
            model.addAttribute(MESSAGE, response.getMessage());
            model.addAttribute(UNIT_DTO, unitDto);
            return "unit/unit";
        }

        return REDIRECT_LIST;
    }

    // DELETE
    @GetMapping("/delete")
    public String delete(@RequestParam String identifier) {
        unitService.delete(identifier);
        return REDIRECT_LIST;
    }

    // TOGGLE STATUS
    @GetMapping("/toggleStatus")
    public String toggleStatus(@RequestParam String identifier) {

        unitService.toggleStatus(identifier);

        return REDIRECT_LIST;
    }
}