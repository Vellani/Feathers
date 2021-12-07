package com.example.feathers.database.service.impl;


import com.example.feathers.application.initialize.InitialSetup;
import com.example.feathers.database.repository.AircraftRepository;
import com.example.feathers.database.repository.LogRepository;
import com.example.feathers.database.repository.ReviewRepository;
import com.example.feathers.database.repository.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(
        locations = "classpath:application-integrationtest.properties")
public class DebugModelTest {

    @Autowired
    private InitialSetup initialSetup;
    @Autowired
    private AircraftRepository aircraftRepository;
    @Autowired
    private LogRepository logRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ReviewRepository reviewRepository;

    @BeforeEach
    void setUp() {
        logRepository.deleteAll();
        reviewRepository.deleteAll();
        aircraftRepository.deleteAll();
    }

    @AfterEach
    void cleanUp() {
        logRepository.deleteAll();
        reviewRepository.deleteAll();
        aircraftRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    public void testAllLoadOfDebugMode() throws IOException {
        initialSetup.loadEasyDebug();

        Assertions.assertEquals(14, aircraftRepository.count());
        Assertions.assertEquals(5, userRepository.count());
        Assertions.assertEquals(3, reviewRepository.count());
        Assertions.assertEquals(8, logRepository.count());



    }





}
