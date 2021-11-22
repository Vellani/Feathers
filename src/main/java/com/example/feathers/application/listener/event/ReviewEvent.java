package com.example.feathers.application.listener.event;

import org.springframework.context.ApplicationEvent;

public class ReviewEvent extends ApplicationEvent {

    public ReviewEvent(Object source) {
        super(source);
    }
}
