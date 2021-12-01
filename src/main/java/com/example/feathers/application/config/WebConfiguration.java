package com.example.feathers.application.config;

import com.example.feathers.web.interseptor.DebugInterceptor;
import com.example.feathers.web.interseptor.SuspendedAccountInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfiguration implements WebMvcConfigurer {

    private final SuspendedAccountInterceptor suspendedAccountInterceptor;
    public final DebugInterceptor debugInterceptor;

    public WebConfiguration(SuspendedAccountInterceptor suspendedAccountInterceptor, DebugInterceptor debugInterceptor) {
        this.suspendedAccountInterceptor = suspendedAccountInterceptor;
        this.debugInterceptor = debugInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(suspendedAccountInterceptor);
        registry.addInterceptor(debugInterceptor);
    }
}
