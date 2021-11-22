package com.example.feathers.web.exception;


import com.example.feathers.util.SimplePair;

public class NotFoundException extends RuntimeException implements CustomErrorInterface {

    private static final SimplePair<Integer, String> ERROR = new SimplePair<>(404, "The page you are trying to find does not exist!");

    public SimplePair<Integer, String> getError() {
        return ERROR;
    }
}
