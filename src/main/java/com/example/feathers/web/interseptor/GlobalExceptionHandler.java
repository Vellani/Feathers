package com.example.feathers.web.interseptor;

import com.example.feathers.web.exception.*;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class GlobalExceptionHandler {

   @ExceptionHandler(GenericException.class)
    public <T extends GenericException> ModelAndView exceptionHandler(T e) {
        ModelAndView modelAndView = new ModelAndView("error");
        modelAndView.addObject("message", e.getError().getValue());
        modelAndView.addObject("code", e.getError().getKey());
        return modelAndView;
    }

    /*@ExceptionHandler
    public  ModelAndView genericHandler(Exception e) {
        ModelAndView modelAndView = new ModelAndView("error");
        modelAndView.addObject("message", "Ooops, something went wrong. \n " +
                "Our one single person in charge of keeping this website running is on it!"); // fun fact, i am not on it
        modelAndView.addObject("code", e.getMessage());
        return modelAndView;
    }*/


}
