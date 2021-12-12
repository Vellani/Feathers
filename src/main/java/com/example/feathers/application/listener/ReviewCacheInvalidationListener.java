package com.example.feathers.application.listener;

import com.example.feathers.application.listener.event.ReviewEvent;
import com.example.feathers.application.schedule.DatabaseCleaner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class ReviewCacheInvalidationListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(ReviewCacheInvalidationListener.class);
    private final CacheManager cacheManager;

    public ReviewCacheInvalidationListener(CacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }

    @EventListener(ReviewEvent.class)
    @CacheEvict(value = "reviews")
    public void invalidatingReviews(ReviewEvent o) {
        LOGGER.info(o.getSource().toString());
        Objects.requireNonNull(cacheManager.getCache("reviews")).clear();
    }

}
