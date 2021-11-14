package com.example.feathers.web.autocomplete;

import com.example.feathers.service.AerodromeService;
import com.example.feathers.service.AircraftService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping(value = "/api/param")
public class AutocompleteController {

    private final AircraftService aircraftService;
    private final AerodromeService aerodromeService;

    public AutocompleteController(AircraftService aircraftService, AerodromeService aerodromeService) {
        this.aircraftService = aircraftService;
        this.aerodromeService = aerodromeService;
    }

    @RequestMapping(params = "reg")
    @ResponseBody
    public List<String> getRegistrations(@RequestParam(value = "reg", required = false) String reg) {
        return validateString(reg) ? aircraftService.findAllMatchingRegistrations(reg.toUpperCase()) : null;
    }

    @RequestMapping(params = "aero")
    @ResponseBody
    public List<String> gerAerodromes(@RequestParam(value = "aero", required = false) String aero) {
        return validateString(aero) ? aerodromeService.findAllMatchingAerodromes(aero.toUpperCase()) : null;
    }

    private boolean validateString(String input) {
        return (input != null && input.length() > 1);
    }
}
