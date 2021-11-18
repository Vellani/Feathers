package com.example.feathers.web;

import com.example.feathers.model.binding.LogBindingModel;
import com.example.feathers.service.AerodromeService;
import com.example.feathers.service.AircraftService;
import com.example.feathers.service.LogService;
import com.example.feathers.web.exception.NotFoundException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.security.Principal;

@Controller
@RequestMapping("/profile")
public class LogController {

    private final AerodromeService aerodromeService;
    private final AircraftService aircraftService;
    private final LogService logService;

    public LogController(AerodromeService aerodromeService, AircraftService aircraftService, LogService logService) {
        this.aerodromeService = aerodromeService;
        this.aircraftService = aircraftService;
        this.logService = logService;
    }

    @ModelAttribute
    public LogBindingModel logAddBindingModel() {
        return new LogBindingModel();
    }

    // Dual purpose GET for show creation page with blanks or show existing log
    @PreAuthorize("@logServiceImpl.isOwnerOfLog(#id, #principal.name)")
    @GetMapping("/log")
    public String logAdd(@RequestParam(required = false) Long id, Model model, Principal principal) {
        if (id != null) {
            LogBindingModel logModel = logService.findById(id);
            model.addAttribute("logBindingModel", logModel);
        }
        return "log";
    }

    // Dual purpose POST method for Save/Update Log
    @PreAuthorize("@logServiceImpl.isOwnerOfLog(#id, #principal.name)")
    @PostMapping("/log")
    public String logAddNew(@RequestParam(required = false) Long id,
                            @Valid LogBindingModel logBindingModel,
                            BindingResult bindingResult,
                            RedirectAttributes redirectAttributes,
                            Principal principal) {

        // This ID set has the function of switching the method from "Create New" to "Update" log
        // Id does not matter if ID is null when creating a new Log
        logBindingModel.setId(id);

        // Instead of creating custom fields for each error, we can set them in the same *{field} of the original entity
        if (!aerodromeService.existsByName(logBindingModel.getDepartureAerodrome()))
            bindingResult.rejectValue("departureAerodrome", "error.logBindingModel", "Aerodrome not fount.");
        if (!aerodromeService.existsByName(logBindingModel.getArrivalAerodrome()))
            bindingResult.rejectValue("arrivalAerodrome", "error.logBindingModel","Aerodrome not fount.");
        if (!aircraftService.alreadyExists(logBindingModel.getAircraft()))
            bindingResult.rejectValue("aircraft", "error.logBindingModel","Registration not fount.");

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("logBindingModel", logBindingModel)
                    .addFlashAttribute("org.springframework.validation.BindingResult.logBindingModel", bindingResult);

            return "redirect:log";
        }

        logService.createNewLog(logBindingModel, principal.getName());

        return "redirect:logbook";
    }

    @PreAuthorize("@logServiceImpl.isOwnerOfLog(#id, #principal.name)")
    @PostMapping("/log/delete")
    public String logDelete(@RequestParam(value = "id") Long id, Principal principal) {
        logService.deleteById(id);
        return "redirect:/profile/logbook";
    }

}





