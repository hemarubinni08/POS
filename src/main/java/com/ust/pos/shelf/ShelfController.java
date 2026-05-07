package com.ust.pos.shelf;

import com.ust.pos.dto.ShelfDto;
import com.ust.pos.shelf.service.ShelfService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/shelf")
public class ShelfController {

    private static final String REDIRECT_LIST = "redirect:/shelf/list";
    public static final String MESSAGE = "message";
    public static final String SHELF_DTO = "shelfDto";

    @Autowired
    private ShelfService shelfService;

    // LIST
    @GetMapping("/list")
    public String list(Model model) {
        List<ShelfDto> shelfList = shelfService.findAll();
        model.addAttribute("shelves", shelfList);
        return "shelf/list";
    }

    // ADD PAGE
    @GetMapping("/add")
    public String addPage(Model model) {
        model.addAttribute(SHELF_DTO, new ShelfDto());
        return "shelf/add";
    }

    // SAVE
    @PostMapping("/add")
    public String save(@ModelAttribute(SHELF_DTO) ShelfDto shelfDto,
                       Model model) {

        ShelfDto response = shelfService.save(shelfDto);

        if (!response.isSuccess()) {
            model.addAttribute(MESSAGE, response.getMessage());
            model.addAttribute(SHELF_DTO, shelfDto);
            return "shelf/add";
        }

        return REDIRECT_LIST;
    }

    // EDIT (FIXED)
    @GetMapping("/edit")
    public String edit(@RequestParam String identifier, Model model) {

        ShelfDto shelfDto = shelfService.findByIdentifier(identifier);

        if (shelfDto == null || !shelfDto.isSuccess()) {
            model.addAttribute(MESSAGE, "Shelf not found");
            return REDIRECT_LIST;
        }

        // 🔴 MUST MATCH JSP modelAttribute
        model.addAttribute(SHELF_DTO, shelfDto);

        return "shelf/shelf";
    }

    // UPDATE
    @PostMapping("/update")
    public String update(@ModelAttribute(SHELF_DTO) ShelfDto shelfDto,
                         Model model) {

        ShelfDto response = shelfService.update(shelfDto);

        if (!response.isSuccess()) {
            model.addAttribute(MESSAGE, response.getMessage());
            model.addAttribute(SHELF_DTO, shelfDto);
            return "shelf/shelf";
        }

        return REDIRECT_LIST;
    }

    // DELETE (REQUEST PARAM)
    @GetMapping("/delete")
    public String delete(@RequestParam String identifier) {
        shelfService.delete(identifier);
        return REDIRECT_LIST;
    }

    // TOGGLE
    @GetMapping("/toggle")
    public String toggleStatus(@RequestParam String identifier) {
        shelfService.toggleStatus(identifier);
        return REDIRECT_LIST;
    }
}