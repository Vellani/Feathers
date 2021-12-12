package com.example.feathers.web.interseptor;

import com.example.feathers.web.exception.*;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class GlobalExceptionHandler {

    /*@ExceptionHandler(GenericException.class)
    public <T extends GenericException> ModelAndView exceptionHandler(T e) {
        ModelAndView modelAndView = new ModelAndView("error");
        modelAndView.addObject("message", e.getError().getValue());
        modelAndView.addObject("code", e.getError().getKey());
        return modelAndView;
    }*/

   /* @ExceptionHandler(Throwable.class)
    public ModelAndView genericHandler(Throwable e) {
        ModelAndView modelAndView = new ModelAndView("error");

        modelAndView.addObject("message", "Ooops, something went wrong!"); // fun fact, i am not on it
        modelAndView.addObject("code", 404);
        return modelAndView;
    }*/

    // TODO not working properly
    @ExceptionHandler({Throwable.class, NoHandlerFoundException.class})
    public ModelAndView genericHandler(HttpServletRequest request, Throwable e) {
        ModelAndView modelAndView = new ModelAndView("error");

        String message;
        Integer code;

        if (e instanceof GenericException customEx){
            message = customEx.getError().getValue();
            code = customEx.getError().getKey();
        } else {
            message = "Ooops, we can't find what you are looking for!";
            code = 404;
        }

        modelAndView.addObject("message", message); // fun fact, i am not on it
        modelAndView.addObject("code", code);

        return modelAndView;
    }

}
