package com.example.feathers.setup;

import com.example.feathers.service.AerodromeService;
import com.example.feathers.service.AircraftService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class InitialSetup implements CommandLineRunner {

	private AerodromeService aerodromeService;
	private AircraftService aircraftService;

    public InitialSetup(AerodromeService aerodromeService, AircraftService aircraftService) {
        this.aerodromeService = aerodromeService;
        this.aircraftService = aircraftService;
    }

    @Override
    public void run(String... args) throws Exception {
        // Required
        aerodromeService.initialize();

        // For debug purposes
        aircraftService.initialize();
    }
}