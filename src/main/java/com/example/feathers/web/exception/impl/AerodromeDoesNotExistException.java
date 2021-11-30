package com.example.feathers.web.exception.impl;

import com.example.feathers.util.SimplePair;
import com.example.feathers.web.exception.GenericException;

public class AerodromeDoesNotExistException extends GenericException {
    public AerodromeDoesNotExistException() {
        error =  new SimplePair<>(404, "The aerodrome you are trying to find does not exist!");

    }
}