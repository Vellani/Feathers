package com.example.feathers.application.schedule;

import com.example.feathers.application.listener.event.ReviewEvent;
import com.example.feathers.database.service.AircraftService;
import com.example.feathers.database.service.LogService;
import com.example.feathers.database.service.ReviewService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;



@Component
public class DatabaseCleaner {

    private static final Logger LOGGER = LoggerFactory.getLogger(DatabaseCleaner.class);

    private final LogService logService;
    private final AircraftService aircraftService;
    private final ReviewService reviewService;
    private final ApplicationEventPublisher applicationEventPublisher;

    public DatabaseCleaner(LogService logService, AircraftService aircraftService, ReviewService reviewService, ApplicationEventPublisher applicationEventPublisher) {
        this.logService = logService;
        this.aircraftService = aircraftService;
        this.reviewService = reviewService;
        this.applicationEventPublisher = applicationEventPublisher;
    }

    @Scheduled(cron = "0 0 2 ? * SUN") //Every Sunday at 02:00 (24h)
    public void cleanDatabase() {
        LOGGER.info("Starting scheduled Database clean-up!");
        logService.cleanUp();
        reviewService.cleanUp();
        aircraftService.cleanUp();

        // To invalidate Caches!
        ApplicationEvent event = new ReviewEvent(this);
        applicationEventPublisher.publishEvent(event);
    }

}
