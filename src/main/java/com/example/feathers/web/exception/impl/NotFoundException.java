package com.example.feathers.web.exception.impl;


import com.example.feathers.util.SimplePair;
import com.example.feathers.web.exception.GenericException;

public class NotFoundException extends GenericException {
    public NotFoundException() {
        error = new SimplePair<>(404, "The page you are trying to find does not exist!");
    }
}
