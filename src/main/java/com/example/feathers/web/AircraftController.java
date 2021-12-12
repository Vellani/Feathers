package com.example.feathers.web;

import com.example.feathers.database.model.binding.AircraftBindingModel;
import com.example.feathers.database.service.AircraftService;
import com.example.feathers.database.service.LogService;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.io.IOException;
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
    public AircraftBindingModel aircraftBindingModel(Long id) {
        return id != null
                ? aircraftService.findById(id)
                : new AircraftBindingModel();
    }

    @PreAuthorize("@aircraftServiceImpl.isOwnerOfAircraft(#id, #principal.name)")
    @GetMapping("")
    public String aircraftAdd(@RequestParam(required = false) Long id, Model model, Principal principal) {
        if (id != null) model.addAttribute("aircraftBindingModel", aircraftBindingModel(id));
        return "aircraft";
    }

    @PreAuthorize("@aircraftServiceImpl.isOwnerOfAircraft(#id, #principal.name)")
    @PostMapping("")
    public String aircraftAddNew(@RequestParam(required = false) Long id,
                                 @Valid AircraftBindingModel aircraftBindingModel,
                                 BindingResult bindingResult,
                                 RedirectAttributes redirectAttributes,
                                 Principal principal) throws IOException {

        aircraftBindingModel.setId(id);

        if(id == null && aircraftService.existByUsernameAndRegistration(principal.getName(), aircraftBindingModel.getRegistration())) {
            bindingResult.rejectValue("registration", "error.aircraftBindingModel", "Registration already exists.");
        }

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("aircraftBindingModel", aircraftBindingModel)
                    .addFlashAttribute("org.springframework.validation.BindingResult.aircraftBindingModel", bindingResult);
            return "redirect:aircraft";
        }

        if (id == null) aircraftService.addNewAircraft(aircraftBindingModel, principal.getName());
        else aircraftService.updateAircraft(aircraftBindingModel);

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
