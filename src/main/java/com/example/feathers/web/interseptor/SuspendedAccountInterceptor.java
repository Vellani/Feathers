package com.example.feathers.web.interseptor;

import com.example.feathers.util.UserRoleUtil;
import com.example.feathers.web.exception.SuspendedAccountException;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class SuspendedAccountInterceptor implements HandlerInterceptor {

    private final UserRoleUtil userRoleUtil;

    public SuspendedAccountInterceptor(UserRoleUtil userRoleUtil) {
        this.userRoleUtil = userRoleUtil;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws ServletException {

        if (request.isUserInRole("ROLE_" + userRoleUtil.getSuspendedRole().getRole().name()) && request.getSession() != null) {
            request.logout();
            throw new SuspendedAccountException();
        }

        return true;
    }

}
