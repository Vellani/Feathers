package com.example.feathers.application.listener;

import com.example.feathers.application.listener.event.ReviewEvent;
import org.springframework.cache.CacheManager;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class ReviewCacheInvalidationListener {

    private final CacheManager cacheManager;

    public ReviewCacheInvalidationListener(CacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }

    @EventListener(ReviewEvent.class)
    public void invalidatingReviews() {
        Objects.requireNonNull(cacheManager.getCache("reviews")).clear();
    }

}
