package com.example.feathers.web;

import com.example.feathers.model.binding.LogBindingModel;
import com.example.feathers.service.AerodromeService;
import com.example.feathers.service.AircraftService;
import com.example.feathers.service.LogService;
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


    @GetMapping("/log{id}")
    public String logAdd(@PathVariable(required = false) Long id, Model model, Principal principal) {
        // TODO Verify if id exists and database

        if (id != null) {
            LogBindingModel logModel = logService.findById(id);

            if (logModel == null || !logModel.getCreator().getUsername().equals(principal.getName())) {
                return "redirect:logbook";
            }

            model.addAttribute("logBindingModel", logModel);
        }
        return "log";
    }

    @PostMapping("/log{id}")
    public String logAddNew(@PathVariable(required = false) Long id,
                            @Valid LogBindingModel logBindingModel,
                            BindingResult bindingResult,
                            RedirectAttributes redirectAttributes,
                            Principal principal) {

        logBindingModel.setId(id);

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

    @PostMapping("/log/delete")
    public String logDelete(@RequestParam(value = "id") Long id) {
        logService.deleteById(id);
        return "redirect:/profile/logbook";
    }

}





