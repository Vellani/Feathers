package com.example.feathers.web.interseptor;

import com.example.feathers.web.exception.CustomErrorInterface;
import com.example.feathers.web.exception.NotFoundException;
import com.example.feathers.web.exception.SuspendedAccountException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({NotFoundException.class, SuspendedAccountException.class})
    public <T extends CustomErrorInterface> ModelAndView exceptionHandler(T e) {
        ModelAndView modelAndView = new ModelAndView("error");
        modelAndView.addObject("message", e.getError().getValue());
        modelAndView.addObject("code", e.getError().getKey());
        return modelAndView;
    }

    @ExceptionHandler
    public  ModelAndView genericHandler(Exception e) {
        ModelAndView modelAndView = new ModelAndView("error");
        modelAndView.addObject("message", "Ooops, something went wrong. \n Our monkeys are on it!");
        //modelAndView.addObject("code", e.g);
        return modelAndView;
    }


}
