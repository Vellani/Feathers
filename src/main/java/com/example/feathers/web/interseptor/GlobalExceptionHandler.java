package com.example.feathers.web.interseptor;

import com.example.feathers.web.exception.NotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class GlobalExceptionHandler {

    // TODO the rest of the errors

    @ExceptionHandler(NotFoundException.class)
    public ModelAndView exceptionHandler(NotFoundException e) {
        ModelAndView modelAndView = new ModelAndView("error");
        modelAndView.addObject("message", e.getMessage());
        modelAndView.addObject("code", e.getCode());
        return modelAndView;
    }

}
