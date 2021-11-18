package com.example.feathers.web;

import com.example.feathers.model.view.ListAircraftViewModel;
import com.example.feathers.service.AircraftService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/profile/dashboard")
public class DashboardController {

    private AircraftService aircraftService;

    public DashboardController(AircraftService aircraftService) {
        this.aircraftService = aircraftService;
    }


    @GetMapping("")
    public String dashboard(Model model, Principal principal) {

        List<ListAircraftViewModel> aircraft = aircraftService.findAllAircraftForUser(principal.getName());
        model.addAttribute("aircraft", aircraft);

        return "dashboard";
    }

    @PostMapping("/aircraft/delete")
    public String deleteAircraft(@RequestParam(value = "id") Long id) {
        // TODO IMPORTANT -> Check delete aircraft if present in a LOG, if present -> Delete log as well!
        aircraftService.deleteById(id);
        return "dashboard";
    }
}