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

    @Autowired
    private ShelfService shelfService;

    @GetMapping("/list")
    public String home(Model model) {
        model.addAttribute("shelfs", shelfService.findAll());
        return "shelf/list";
    }

    @GetMapping("/add")
    public String add(Model model, @ModelAttribute ShelfDto shelfDto) {
        model.addAttribute("shelfs", shelfService.findAll());
        return "shelf/add";
    }

    @PostMapping("/add")
    public String doadd(Model model, @ModelAttribute ShelfDto shelfDto) {
        ShelfDto shelfDto1 = shelfService.save(shelfDto);
        if (!shelfDto1.isSuccess()) {
            model.addAttribute("message", shelfDto1.getMessage());
            model.addAttribute("shelfs", shelfService.findAll());
            return "shelf/add";
        }
        return "redirect:/shelf/list";
    }

    @GetMapping("/get")
    public String update(Model model, @RequestParam String identifier) {
        ShelfDto shelfDto = shelfService.findByIdentifier(identifier);
        model.addAttribute("shelfs", shelfDto);
        return "shelf/shelf";
    }

    @PostMapping("/update")
    public String doupdate(Model model, @ModelAttribute ShelfDto shelfDto) {
        ShelfDto shelfDto1 = shelfService.update(shelfDto);
        if (!shelfDto1.isSuccess()) {
            model.addAttribute("messsage", shelfDto1.getMessage());
            model.addAttribute("shelfs", shelfService.findAll());
            return "shelf/shelf";
        }
        return "redirect:/shelf/list";
    }

    @PostMapping("status")
    public String updateStatus(Model model, @RequestParam String identifier, boolean status) {
        shelfService.updateStatusOnly(identifier, status);
        return "redirect:/shelf/list";
    }

    @GetMapping("/delete")
    public String delete(Model model, @RequestParam String identifier) {
        shelfService.delete(identifier);
        return "redirect:/shelf/list";
    }
}
