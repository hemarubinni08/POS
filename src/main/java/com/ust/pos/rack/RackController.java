package com.ust.pos.rack;

import com.ust.pos.dto.RackDto;
import com.ust.pos.rack.service.RackService;
import com.ust.pos.shelfs.service.ShelfsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/rack")
public class RackController {

    public static final String REDIRECT_RACK_LIST = "redirect:/rack/list";

    @Autowired
    private RackService rackService;
    @Autowired
    private ShelfsService shelfsService;

    @GetMapping("/list")
    public String home(Model model) {
        model.addAttribute("racks", rackService.findAll());
        return "rack/list";
    }

    @GetMapping("/add")
    public String add(Model model, @ModelAttribute RackDto rackDto) {
        model.addAttribute("shelfs", shelfsService.findAll());
        return "rack/add";
    }

    @PostMapping("/add")
    public String addPost(Model model, @ModelAttribute RackDto rackDto) {

        RackDto response = rackService.save(rackDto);

        if (!response.isSuccess()) {
            model.addAttribute("message", response.getMessage());
            return "rack/add";
        }

        return REDIRECT_RACK_LIST;
    }

    @GetMapping("/get")
    public String update(Model model, @RequestParam String identifier) {

        RackDto response = rackService.findByIdentifier(identifier);
        model.addAttribute("rack", response);
        model.addAttribute("shelfs", shelfsService.findAll());

        return "rack/rack";
    }

    @PostMapping("/update")
    public String updatePost(Model model, @ModelAttribute RackDto rackDto) {

        RackDto response = rackService.update(rackDto);

        if (!response.isSuccess()) {
            model.addAttribute("message", response.getMessage());
            model.addAttribute(
                    "rack",
                    rackService.findByIdentifier(rackDto.getIdentifier())
            );
            return "rack/rack";
        }

        return REDIRECT_RACK_LIST;
    }

    @GetMapping("/delete")
    public String delete(@RequestParam String identifier) {
        rackService.delete(identifier);
        return REDIRECT_RACK_LIST;
    }

}