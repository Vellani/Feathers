package com.example.feathers.web.exception.impl;

import com.example.feathers.util.SimplePair;
import com.example.feathers.web.exception.GenericException;

public class AircraftDoesNotExistException extends GenericException {
    public AircraftDoesNotExistException() {
        error = new SimplePair<>(404, "The aircraft you are trying to find does not exist!");
    }
}