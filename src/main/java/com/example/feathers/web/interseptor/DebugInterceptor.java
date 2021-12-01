package com.example.feathers.web.interseptor;

import com.example.feathers.application.initialize.InitialSetup;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class DebugInterceptor  implements HandlerInterceptor {

    private final InitialSetup initialSetup;

    public DebugInterceptor(InitialSetup initialSetup) {
        this.initialSetup = initialSetup;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws ServletException, IOException {

        if (request.getServletPath().equals("/debug")) {
            initialSetup.loadEasyDebug();
            response.sendRedirect(request.getContextPath() + "/");
            return false;
        }

        return true;
    }

}
