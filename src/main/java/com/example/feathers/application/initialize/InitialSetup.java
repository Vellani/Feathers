package com.example.feathers.application.initialize;

import com.example.feathers.database.service.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Profile("!test")
@Component
public class InitialSetup implements CommandLineRunner {

	private final AerodromeService aerodromeService;
	private final AircraftService aircraftService;
	private final UserService userService;
	private final LogService logService;
	private final ReviewService reviewService;

    public InitialSetup(AerodromeService aerodromeService, AircraftService aircraftService, UserService userService, LogService logService, ReviewService reviewService) {
        this.aerodromeService = aerodromeService;
        this.aircraftService = aircraftService;
        this.userService = userService;
        this.logService = logService;
        this.reviewService = reviewService;
    }

    @Override
    public void run(String... args) throws Exception {
        // Required
        userService.initialize();
        aerodromeService.initialize();
    }

    public void loadEasyDebug() throws IOException {
        if (userService.getAll().size() <= 1) {
            userService.startDebugMode();
            aircraftService.startDebugMode();
            logService.startDebugMode();
            reviewService.startDebugMode();
        }
    }
}