package com.example.feathers.web.autocomplete;

import com.example.feathers.service.AerodromeService;
import com.example.feathers.service.AircraftService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping(value = "/api/param")
public class AutocompleteController {

    private final AircraftService aircraftService;
    private final AerodromeService aerodromeService;

    public AutocompleteController(AircraftService aircraftService, AerodromeService aerodromeService) {
        this.aircraftService = aircraftService;
        this.aerodromeService = aerodromeService;
    }

    @RequestMapping(params = "reg")
    public ResponseEntity<List<String>> getRegistrations(@RequestParam(value = "reg", required = false) String reg, Principal principal) {
        List<String> registrations = validateString(reg)
                ? aircraftService.findAllMatchingRegistrations(principal.getName(), reg.toUpperCase())
                : null;
        return registrations == null
                ? ResponseEntity.notFound().build()
                : ResponseEntity.ok(registrations);
    }


    @RequestMapping(params = "aero")
    public ResponseEntity<List<String>> gerAerodromes(@RequestParam(value = "aero", required = false) String aero) {
        List<String> aerodromes = validateString(aero)
                ? aerodromeService.findAllMatchingAerodromes(aero.toUpperCase())
                : null;
        return aerodromes == null
                ? ResponseEntity.notFound().build()
                : ResponseEntity.ok(aerodromes);
    }

    // Server-side Input validation
    private boolean validateString(String input) {
        return (input != null && input.length() > 1);
    }
}
