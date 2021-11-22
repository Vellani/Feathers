package com.example.feathers.web.exception;

import com.example.feathers.util.SimplePair;

public class SuspendedAccountException extends RuntimeException implements CustomErrorInterface {

    private static final SimplePair<Integer, String> ERROR = new SimplePair<>(666, "Your account has been Suspended");

    public SimplePair<Integer, String> getError() {
        return ERROR;
    }

}