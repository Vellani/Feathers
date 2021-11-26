package com.example.feathers.web;

import com.example.feathers.database.model.view.ListAircraftViewModel;
import com.example.feathers.database.service.AircraftService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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


}
