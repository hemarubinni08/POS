package com.ust.pos.shelf;

import com.ust.pos.dto.ShelfDto;
import com.ust.pos.shelf.service.ShelfService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/shelf")
public class ShelfController {

    public static final String REDIRECT_SHELF_LIST = "redirect:/shelf/list";

    @Autowired
    private ShelfService shelfService;

    @GetMapping("/list")
    public String list(Model model) {
        model.addAttribute("shelves", shelfService.getAllShelves());
        return "shelf/list";
    }

    @GetMapping("/add")
    public String add(Model model) {
        model.addAttribute("shelfDto", new ShelfDto());
        return "shelf/add";
    }

    @PostMapping("/add")
    public String addPost(@ModelAttribute ShelfDto shelfDto, Model model) {

        ShelfDto response = shelfService.createShelf(shelfDto);

        if (!response.isSuccess()) {
            model.addAttribute("message", response.getMessage());
            model.addAttribute("messageType", "error");
            return "shelf/add";
        }

        return REDIRECT_SHELF_LIST;
    }

    @GetMapping("/get")
    public String update(@RequestParam Long id, Model model) {

        ShelfDto response = shelfService.getShelf(id);

        if (!response.isSuccess()) {
            return REDIRECT_SHELF_LIST;
        }

        model.addAttribute("shelf", response);
        return "shelf/shelf";
    }

    @PostMapping("/update")
    public String updatePost(@ModelAttribute ShelfDto shelfDto) {
        shelfService.updateShelf(shelfDto);
        return REDIRECT_SHELF_LIST;
    }

    @GetMapping("/delete")
    public String delete(@RequestParam Long id) {
        shelfService.deleteShelf(id);
        return REDIRECT_SHELF_LIST;
    }

    @PostMapping("/toggle-status")
    @ResponseBody
    public ShelfDto toggleStatus(@RequestParam Long id) {
        return shelfService.toggleStatus(id);
    }
}