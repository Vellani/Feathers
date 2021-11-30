package com.example.feathers.web.exception.impl;

import com.example.feathers.util.SimplePair;
import com.example.feathers.web.exception.GenericException;

public class UserNotFoundException extends GenericException {
    public UserNotFoundException() {
        error = new SimplePair<>(404, "The User you are looking for cannot be found!");
    }
}