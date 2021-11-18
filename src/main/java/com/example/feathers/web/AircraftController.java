package com.example.feathers.web;

import com.example.feathers.model.binding.AircraftBindingModel;
import com.example.feathers.service.AircraftService;
import com.example.feathers.service.LogService;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.security.Principal;

@Controller
@RequestMapping("/profile/aircraft")
public class AircraftController {

    private final AircraftService aircraftService;
    private final LogService logService;

    public AircraftController(AircraftService aircraftService,
                              @Lazy LogService logService) {
        this.aircraftService = aircraftService;
        this.logService = logService;
    }

    @ModelAttribute
    public AircraftBindingModel aircraftBindingModel() {
        return new AircraftBindingModel();
    }

    @PreAuthorize("@aircraftServiceImpl.isOwnerOfAircraft(#id, #principal.name)")
    @GetMapping("")
    public String aircraftAdd(@RequestParam(required = false) Long id, Model model, Principal principal) {
        model.addAttribute("aircraftBindingModel",
                id != null
                ? aircraftService.findById(id)
                : aircraftBindingModel());
        return "aircraft-add";
    }

    @PreAuthorize("@aircraftServiceImpl.isOwnerOfAircraft(#id, #principal.name)")
    @PostMapping("")
    public String aircraftAddNew(@RequestParam(required = false) Long id,
                                 @Valid AircraftBindingModel aircraftBindingModel,
                                 BindingResult bindingResult,
                                 RedirectAttributes redirectAttributes,
                                 Principal principal) {

        // This ID set has the function of switching the method from "Create New" to "Update" aircraft
        // Id does not matter if ID is null when creating a new aircraft
        aircraftBindingModel.setId(id);

        // If id == null then we this method is "Create" then we need a check for unique Username-Registrations
        if(id == null && aircraftService.existByUsernameAndRegistration(principal.getName(), aircraftBindingModel.getRegistration())) {
            bindingResult.rejectValue("registration", "error.aircraftBindingModel", "Registration already exists.");
        }

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("aircraftBindingModel", aircraftBindingModel)
                    .addFlashAttribute("org.springframework.validation.BindingResult.aircraftBindingModel", bindingResult);
            return "redirect:";
        }

        aircraftService.addNewAircraft(aircraftBindingModel, principal);

        return "redirect:/profile/dashboard";
    }

    @PreAuthorize("@aircraftServiceImpl.isOwnerOfAircraft(#id, #principal.name)")
    @PostMapping("/delete")
    public String deleteAircraft(@RequestParam(value = "id") Long id, Principal principal, RedirectAttributes redirectAttributes) {

        Integer countOfLogsWithAircraft = logService.countAllFlightsWithAircraft(aircraftService.findAircraftEntityById(id));

        if (countOfLogsWithAircraft > 0) {
            redirectAttributes.addFlashAttribute("cannotdelete", true);
            return "redirect:/profile/dashboard";
        }

        aircraftService.deleteById(id);
        return "redirect:/profile/dashboard";
    }
}
