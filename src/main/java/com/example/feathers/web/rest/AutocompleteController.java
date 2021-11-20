package com.example.feathers.web.rest;

import com.example.feathers.database.service.AerodromeService;
import com.example.feathers.database.service.AircraftService;
import com.example.feathers.database.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
    private final UserService userService;

    public AutocompleteController(AircraftService aircraftService, AerodromeService aerodromeService, UserService userService) {
        this.aircraftService = aircraftService;
        this.aerodromeService = aerodromeService;
        this.userService = userService;
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
    public ResponseEntity<List<String>> getAerodromes(@RequestParam(value = "aero", required = false) String aero) {
        List<String> aerodromes = validateString(aero)
                ? aerodromeService.findAllMatchingAerodromes(aero.toUpperCase())
                : null;
        return aerodromes == null
                ? ResponseEntity.notFound().build()
                : ResponseEntity.ok(aerodromes);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')") // Redundant since this URL structure is already behind /profile/admin/
    @RequestMapping(params = "user")
    public ResponseEntity<List<String>> getUsers(@RequestParam(value = "user", required = false) String user) {
        List<String> userString = validateString(user)
                ? userService.findUserForAdmin(user)
                : null;
        return userString == null
                ? ResponseEntity.notFound().build()
                : ResponseEntity.ok(userString);
    }

    // Server-side Input validation
    private boolean validateString(String input) {
        return (input != null && input.length() > 1);
    }
}
