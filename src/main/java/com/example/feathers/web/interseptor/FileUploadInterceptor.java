package com.example.feathers.web.interseptor;

import com.example.feathers.service.MonitoringService;
import org.hibernate.Interceptor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class FileUploadInterceptor implements HandlerInterceptor {

    private final MonitoringService monitoringService;

    public FileUploadInterceptor(MonitoringService monitoringService) {
        this.monitoringService = monitoringService;
    }



    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //System.out.print(request.getRemoteUser());
        monitoringService.onRequest();
        return true;
    }
}
