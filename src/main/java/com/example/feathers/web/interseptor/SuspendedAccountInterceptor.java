package com.example.feathers.web.interseptor;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class SuspendedAccountInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        try {
            if (request.isUserInRole(HttpStatus.UNAUTHORIZED.toString()) && request.getSession() != null) {
                request.logout();
                response.sendError(403, "Account Suspended!");
                return false;
            }
        } catch (Exception ignored) {}
        return true;
    }
}
