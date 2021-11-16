package com.example.feathers.web;

import com.example.feathers.model.binding.AircraftAddBindingModel;
import com.example.feathers.service.AircraftService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.security.Principal;

@Controller
@RequestMapping("/aircraft")
public class AircraftController {

    private final AircraftService aircraftService;

    public AircraftController(AircraftService aircraftService) {
        this.aircraftService = aircraftService;
    }

    @ModelAttribute
    public AircraftAddBindingModel aircraftAddBindingModel() {
        return new AircraftAddBindingModel();
    }

    @GetMapping("/add")
    public String aircraftAdd(Model model) {
        // TODO Deny access to direct url to an aircraft from unauthorized user

        model.addAttribute("registrationExists",
                model.getAttribute("registrationExists") == null ? false : model.getAttribute("registrationExists"));
        return "aircraft-add";
    }

    @PostMapping("/add")
    public String aircraftAddNew(@Valid AircraftAddBindingModel aircraftAddBindingModel,
                                 BindingResult bindingResult,
                                 RedirectAttributes redirectAttributes,
                                 Principal principal) {

        boolean registrationExists = aircraftService.alreadyExists(aircraftAddBindingModel.getRegistration());

        if (bindingResult.hasErrors() || registrationExists) {
            redirectAttributes.addFlashAttribute("aircraftAddBindingModel", aircraftAddBindingModel)
                    .addFlashAttribute("org.springframework.validation.BindingResult.aircraftAddBindingModel", bindingResult)
                    .addFlashAttribute("registrationExists", registrationExists);
            return "redirect:add";
        }

        // TODO picture upload
        aircraftService.addNewAircraft(aircraftAddBindingModel, principal);
        return "redirect:/profile/log";
    }

}
