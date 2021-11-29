package com.example.feathers.application.schedule;

import com.example.feathers.database.service.AircraftService;
import com.example.feathers.database.service.LogService;
import com.example.feathers.database.service.ReviewService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;



@Component
public class DatabaseCleaner {

    private static final Logger LOGGER = LoggerFactory.getLogger(DatabaseCleaner.class);

    private final LogService logService;
    private final AircraftService aircraftService;
    private final ReviewService reviewService;

    public DatabaseCleaner(LogService logService, AircraftService aircraftService, ReviewService reviewService) {
        this.logService = logService;
        this.aircraftService = aircraftService;
        this.reviewService = reviewService;
    }

    @Scheduled(cron = "0 0 2 ? * SUN") //Every Sunday at 02:00 (24h)
    public void cleanDatabase() {
        LOGGER.info("Starting scheduled Database clean-up!");
        logService.cleanUp();
        reviewService.cleanUp();
        aircraftService.cleanUp();

    }

}
