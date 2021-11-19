package com.example.feathers;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class FeathersApplication {

    public static void main(String[] args) {
        SpringApplication.run(FeathersApplication.class, args);
    }

}
