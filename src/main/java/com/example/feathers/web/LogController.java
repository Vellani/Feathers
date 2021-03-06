package com.example.feathers.web;

import com.example.feathers.database.model.binding.LogBindingModel;
import com.example.feathers.database.service.AerodromeService;
import com.example.feathers.database.service.AircraftService;
import com.example.feathers.database.service.LogService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.security.Principal;

@Controller
@RequestMapping("/profile/log")
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
    public LogBindingModel logBindingModel(Long id) {
        return id != null
                ? logService.findById(id)
                : new LogBindingModel();
    }

    // Dual purpose GET for show creation page with blanks or show existing log
    @PreAuthorize("@logServiceImpl.isOwnerOfLog(#id, #principal.name)")
    @GetMapping("")
    public String logAdd(@RequestParam(required = false) Long id, Model model, Principal principal) {
        if (id != null) model.addAttribute("logBindingModel", logBindingModel(id));
        return "log";
    }

    @PreAuthorize("@logServiceImpl.isOwnerOfLog(#id, #principal.name)")
    @PostMapping("")
    public String logAddNew(@RequestParam(required = false) Long id,
                            @Valid LogBindingModel logBindingModel,
                            BindingResult bindingResult,
                            RedirectAttributes redirectAttributes,
                            Principal principal) {

        logBindingModel.setId(id);

        // Instead of creating custom fields for each error, we can set them in the same *{field} of the original entity
        if (!aerodromeService.existsByName(logBindingModel.getDepartureAerodrome()))
            bindingResult.rejectValue("departureAerodrome", "error.logBindingModel", "Aerodrome not found.");
        if (!aerodromeService.existsByName(logBindingModel.getArrivalAerodrome()))
            bindingResult.rejectValue("arrivalAerodrome", "error.logBindingModel","Aerodrome not found.");
        if (!aircraftService.alreadyExists(logBindingModel.getAircraft()))
            bindingResult.rejectValue("aircraft", "error.logBindingModel","Registration not found.");

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("logBindingModel", logBindingModel)
                    .addFlashAttribute("org.springframework.validation.BindingResult.logBindingModel", bindingResult);

            return "redirect:log";
        }

        if (id == null) logService.createNewLog(logBindingModel, principal.getName());
        else logService.updateLog(logBindingModel);

        return "redirect:logbook";
    }

    @PreAuthorize("@logServiceImpl.isOwnerOfLog(#id, #principal.name)")
    @PostMapping("/delete")
    public String logDelete(@RequestParam(value = "id") Long id, Principal principal) {
        logService.deleteById(id);
        return "redirect:/profile/logbook";
    }
}





