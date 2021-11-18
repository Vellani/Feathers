package com.example.feathers.web.exception;


public class NotFoundException extends RuntimeException {

    public static final int ERROR_CODE = 404;
    public static final String ERROR = "The page you are trying to find does not exist!";

    @Override
    public String getMessage() {
        return ERROR;
    }

    public Integer getCode() {
        return ERROR_CODE;
    }

}
