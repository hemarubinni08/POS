package com.ust.pos.models;

import com.ust.pos.dto.ModelsDto;
import com.ust.pos.models.service.ModelsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/models")
public class ModelsController {

    public static final String REDIRECT_MODEL_LIST = "redirect:/models/list";

    @Autowired
    private ModelsService modelsService;


    @GetMapping("/list")
    public String home(Model model) {
        model.addAttribute("models", modelsService.findAll());
        return "models/list";
    }


    @GetMapping("/add")
    public String add(Model model, @ModelAttribute ModelsDto modelsDto) {
        return "models/add";
    }


    @PostMapping("/add")
    public String addPost(Model model, @ModelAttribute ModelsDto modelsDto) {

        ModelsDto response = modelsService.save(modelsDto);

        if (!response.isSuccess()) {
            model.addAttribute("message", response.getMessage());
            return "models/add";
        }

        return REDIRECT_MODEL_LIST;
    }

    // ✅ EDIT MODEL PAGE
    @GetMapping("/get")
    public String update(Model model, @RequestParam String identifier) {

        ModelsDto response = modelsService.findByIdentifier(identifier);
        model.addAttribute("model", response);

        return "models/models";
    }

    // ✅ UPDATE MODEL
    @PostMapping("/update")
    public String updatePost(Model model, @ModelAttribute ModelsDto modelsDto) {

        ModelsDto response = modelsService.update(modelsDto);

        if (!response.isSuccess()) {
            model.addAttribute("message", response.getMessage());
            model.addAttribute(
                    "model",
                    modelsService.findByIdentifier(modelsDto.getIdentifier())
            );
            return "models/models";
        }

        return REDIRECT_MODEL_LIST;
    }

    // ✅ DELETE MODEL
    @GetMapping("/delete")
    public String delete(@RequestParam String identifier) {

        modelsService.delete(identifier);
        return REDIRECT_MODEL_LIST;
    }
    @PostMapping("/toggle-status")
    @ResponseBody
    public void toggle(Model model,@RequestParam String identifier){
        modelsService.toggleStatus(identifier);
    }
}