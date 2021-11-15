package com.example.feathers.setup;

import com.example.feathers.service.AerodromeService;
import com.example.feathers.service.AircraftService;
import com.example.feathers.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class InitialSetup implements CommandLineRunner {

	private AerodromeService aerodromeService;
	private AircraftService aircraftService;
	private UserService userService;

    public InitialSetup(AerodromeService aerodromeService, AircraftService aircraftService, UserService userService) {
        this.aerodromeService = aerodromeService;
        this.aircraftService = aircraftService;
        this.userService = userService;
    }

    @Override
    public void run(String... args) throws Exception {
        // Required
        userService.initialize();
        aerodromeService.initialize();

        // For debug purposes
        aircraftService.initialize();
    }
}