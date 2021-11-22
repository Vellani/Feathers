package com.example.feathers.application.listener.event;

import org.springframework.context.ApplicationEvent;

public class UserCreatedEvent extends ApplicationEvent {

    private String username;

    public UserCreatedEvent(Object source, String username) {
        super(source);
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
}
