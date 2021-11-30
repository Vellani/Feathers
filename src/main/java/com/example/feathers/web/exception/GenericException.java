package com.example.feathers.web.exception;

import com.example.feathers.util.SimplePair;

public class GenericException extends RuntimeException{

    protected static SimplePair<Integer, String> error;

    public GenericException() {
        error = new SimplePair<>(404, "What you are looking for does not exist!");
    }

    public SimplePair<Integer, String> getError() {
        return error;
    }


}
