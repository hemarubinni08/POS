package com.ust.pos.shelfs;

import com.ust.pos.dto.ShelfsDto;
import com.ust.pos.shelfs.service.ShelfsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/shelfs")
public class ShelfsController {

    private static final String REDIRECT_LIST = "redirect:/shelfs/list";

    @Autowired
    private ShelfsService shelfsService;

    @GetMapping("/list")
    public String list(Model model) {
        model.addAttribute("shelfs", shelfsService.findAll());
        return "shelfs/list";
    }

    @GetMapping("/add")
    public String add(@ModelAttribute ShelfsDto shelfsDto) {
        return "shelfs/add";
    }

    @PostMapping("/add")
    public String addPost(Model model, @ModelAttribute ShelfsDto shelfsDto) {

        ShelfsDto response = shelfsService.save(shelfsDto);

        if (!response.isSuccess()) {
            model.addAttribute("message", response.getMessage());
            return "shelfs/add";
        }

        return REDIRECT_LIST;
    }

    @GetMapping("/get")
    public String edit(Model model, @RequestParam String identifier) {
        model.addAttribute("shelf", shelfsService.findByIdentifier(identifier));
        return "shelfs/shelfs";
    }

    @PostMapping("/update")
    public String update(Model model, @ModelAttribute ShelfsDto shelfsDto) {

        ShelfsDto response = shelfsService.update(shelfsDto);

        if (!response.isSuccess()) {
            model.addAttribute("message", response.getMessage());
            model.addAttribute(
                    "shelf",
                    shelfsService.findByIdentifier(shelfsDto.getIdentifier())
            );
            return "shelfs/shelfs";
        }

        return REDIRECT_LIST;
    }

    @GetMapping("/delete")
    public String delete(@RequestParam String identifier) {
        shelfsService.delete(identifier);
        return REDIRECT_LIST;
    }
}