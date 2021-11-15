package com.example.feathers.web;

import com.example.feathers.model.binding.LogAddBindingModel;
import com.example.feathers.service.AerodromeService;
import com.example.feathers.service.AircraftService;
import com.example.feathers.service.LogService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

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
    public LogAddBindingModel logAddBindingModel() {
        return new LogAddBindingModel();
    }


    @GetMapping("/log{id}")
    public String logAdd(@PathVariable(required = false) Long id, Model model) {
        // TODO Verify if id exists and database AND user is authenticated to use that log id
        if (id != null) {
            LogAddBindingModel logViewModel = logService.findById(id);
            model.addAttribute("logAddBindingModel", logViewModel);
        }
        return "log-add";
    }

    @PostMapping("/log{id}")
    public String logAddNew(@PathVariable(required = false) Long id, @Valid LogAddBindingModel logAddBindingModel,
                            BindingResult bindingResult, RedirectAttributes redirectAttributes) {

        logAddBindingModel.setId(id);

        if (!aerodromeService.existsByName(logAddBindingModel.getDepartureAerodrome()))
            bindingResult.rejectValue("departureAerodrome", "error.logAddBindingModel", "Aerodrome not fount.");
        if (!aerodromeService.existsByName(logAddBindingModel.getArrivalAerodrome()))
            bindingResult.rejectValue("arrivalAerodrome", "error.logAddBindingModel","Aerodrome not fount.");
        if (!aircraftService.alreadyExists(logAddBindingModel.getAircraft()))
            bindingResult.rejectValue("aircraft", "error.logAddBindingModel","Registration not fount.");

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("logAddBindingModel", logAddBindingModel)
                    .addFlashAttribute("org.springframework.validation.BindingResult.logAddBindingModel", bindingResult);

            return "redirect:log";
        }

        logService.createNewLog(logAddBindingModel);

        return "redirect:logbook";
    }

    @GetMapping("/log/delete{id}")
    public String logDelete(@PathVariable Long id) {
        logService.deleteById(id);
        return "redirect:/profile/logbook";
    }

    // In case I need to separate them again
    /*@GetMapping("/add")
    public String logAdd(Model model) {
        return "log-add";
    }

    @PostMapping("/add")
    public String logAddNew(@Valid LogAddBindingModel logAddBindingModel,
                            BindingResult bindingResult, RedirectAttributes redirectAttributes) {

        logAddBindingModel.setId(null);

        if (!aerodromeService.existsByName(logAddBindingModel.getDepartureAerodrome()))
            bindingResult.rejectValue("departureAerodrome", "error.logAddBindingModel", "Aerodrome not fount.");
        if (!aerodromeService.existsByName(logAddBindingModel.getArrivalAerodrome()))
            bindingResult.rejectValue("arrivalAerodrome", "error.logAddBindingModel","Aerodrome not fount.");
        if (!aircraftService.alreadyExists(logAddBindingModel.getAircraft()))
            bindingResult.rejectValue("aircraft", "error.logAddBindingModel","Registration not fount.");

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("logAddBindingModel", logAddBindingModel)
                    .addFlashAttribute("org.springframework.validation.BindingResult.logAddBindingModel", bindingResult);

            return "redirect:add";
        }

        logService.createNewLog(logAddBindingModel);

        return "logbook";
    }

    @GetMapping("/view/{id}")
    private String logView(@PathVariable Long id, Model model) {
        LogAddBindingModel logViewModel = logService.findById(id);
        model.addAttribute("logViewModel", logViewModel);
        return "log-view";
    }

    @PostMapping("/update/{id}")
    public String update(@PathVariable Long id, @Valid LogAddBindingModel logAddBindingModel,
                            BindingResult bindingResult, RedirectAttributes redirectAttributes) {

        logAddBindingModel.setId(id);

        if (!aerodromeService.existsByName(logAddBindingModel.getDepartureAerodrome()))
            bindingResult.rejectValue("departureAerodrome", "error.logAddBindingModel", "Aerodrome not fount.");
        if (!aerodromeService.existsByName(logAddBindingModel.getArrivalAerodrome()))
            bindingResult.rejectValue("arrivalAerodrome", "error.logAddBindingModel","Aerodrome not fount.");
        if (!aircraftService.alreadyExists(logAddBindingModel.getAircraft()))
            bindingResult.rejectValue("aircraft", "error.logAddBindingModel","Registration not fount.");

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("logAddBindingModel", logAddBindingModel)
                    .addFlashAttribute("org.springframework.validation.BindingResult.logAddBindingModel", bindingResult);

            return "redirect:/profile/log/view/" + id.toString();
        }

        logService.updateLog(logAddBindingModel);

        return "logbook";
    }*/

}





