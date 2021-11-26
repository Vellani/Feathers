package com.example.feathers.application.initialize;

import com.example.feathers.database.service.AerodromeService;
import com.example.feathers.database.service.AircraftService;
import com.example.feathers.database.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Profile("!test")
@Component
public class InitialSetup implements CommandLineRunner {

	private final AerodromeService aerodromeService;
	private final AircraftService aircraftService;
	private final UserService userService;

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