package com.example.feathers.config;

import com.example.feathers.web.interseptor.FileUploadInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfiguration implements WebMvcConfigurer {

    private final FileUploadInterceptor fileUploadInterceptor;

    public WebConfiguration(FileUploadInterceptor fileUploadInterceptor) {
        this.fileUploadInterceptor = fileUploadInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(fileUploadInterceptor);
    }
}
