package com.example.feathers.web.exception.impl;

import com.example.feathers.util.SimplePair;
import com.example.feathers.web.exception.GenericException;

public class SuspendedAccountException extends GenericException {
    public SuspendedAccountException() {
        error = new SimplePair<>(666, "Your account has been Suspended");
    }
}