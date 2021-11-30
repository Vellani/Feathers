package com.example.feathers.web.exception.impl;

import com.example.feathers.util.SimplePair;
import com.example.feathers.web.exception.GenericException;

public class FlightLogNotFoundException extends GenericException {
    public FlightLogNotFoundException() {
        error = new SimplePair<>(404, "The Flight log you are looking for does not exist!");
    }
}