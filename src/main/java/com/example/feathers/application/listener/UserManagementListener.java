package com.example.feathers.application.listener;

import com.example.feathers.application.event.UserCreatedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class UserManagementListener {

    private Logger LOGGER = LoggerFactory.getLogger(UserManagementListener.class);

    @EventListener(UserCreatedEvent.class)
    public void userCreated(UserCreatedEvent userCreatedEvent) {
        LOGGER.info("User [" + userCreatedEvent.getUsername() + "] created!");
    }

    /* TODO @EventListener(UserDeletedEvent.class)
    public void userDeleted()*/


}
