package com.example.feathers.application.config;

import com.example.feathers.web.interseptor.SuspendedAccountInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfiguration implements WebMvcConfigurer {

    private final SuspendedAccountInterceptor suspendedAccountInterceptor;

    public WebConfiguration(SuspendedAccountInterceptor suspendedAccountInterceptor) {
        this.suspendedAccountInterceptor = suspendedAccountInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(suspendedAccountInterceptor);
    }
}
