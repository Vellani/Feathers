package com.example.feathers.web;

import com.example.feathers.database.model.view.ListAircraftViewModel;
import com.example.feathers.database.service.AircraftService;
import com.example.feathers.database.service.LogService;
import com.example.feathers.util.SimplePair;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/profile/dashboard")
public class DashboardController {

    private final AircraftService aircraftService;
    private final LogService logService;

    public DashboardController(AircraftService aircraftService, LogService logService) {
        this.aircraftService = aircraftService;
        this.logService = logService;
    }

    @GetMapping("")
    public String dashboard(Model model, Principal principal) {

        List<ListAircraftViewModel> aircraft = aircraftService.findAllAircraftForUser(principal.getName());
        model.addAttribute("aircraft", aircraft);

        SimplePair<String, Integer> mostUsedAircraft = logService.findMostUsedAircraft();
        model.addAttribute("mostUsedAircraft", mostUsedAircraft);

        SimplePair<SimplePair<String, Integer>, SimplePair<String, Integer>> mostUsedAirport =
                logService.findMostUsedAirport();
        model.addAttribute("mostUsedDepAerodrome", mostUsedAirport != null
                ? mostUsedAirport.getKey()
                : new SimplePair<>("null", 0));
        model.addAttribute("mostUsedArrAerodrome", mostUsedAirport != null
                ? mostUsedAirport.getValue()
                : new SimplePair<>("null", 0));

        return "dashboard";
    }


}
