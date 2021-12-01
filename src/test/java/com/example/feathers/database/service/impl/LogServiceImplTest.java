package com.example.feathers.database.service.impl;

import com.example.feathers.database.model.binding.LogBindingModel;
import com.example.feathers.database.model.binding.UserRegisterBindingModel;
import com.example.feathers.database.model.entity.AircraftEntity;
import com.example.feathers.database.model.entity.LogEntity;
import com.example.feathers.database.model.entity.UserEntity;
import com.example.feathers.database.model.entity.enums.AircraftClassEnum;
import com.example.feathers.database.repository.AircraftRepository;
import com.example.feathers.database.repository.LogRepository;
import com.example.feathers.database.repository.UserRepository;
import com.example.feathers.database.service.LogService;
import com.example.feathers.database.service.UserService;
import com.example.feathers.util.UserRoleUtil;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(
        locations = "classpath:application-integrationtest.properties")
class LogServiceImplTest {

    @Autowired
    private UserRoleUtil userRoleUtil;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private LogService logService;
    @Autowired
    private LogRepository logRepository;
    @Autowired
    private AircraftRepository aircraftRepository;

    private UserEntity testUser;
    private static final String PASSWORD = "12345";
    private MockMultipartFile multipartFile;
    private LogBindingModel logBindingModel;

    @BeforeEach
    void init() {
        aircraftRepository.deleteAll();
        userRepository.deleteAll();
        logRepository.deleteAll();

        multipartFile = new MockMultipartFile(
                "0",
                "",
                MediaType.TEXT_PLAIN_VALUE,
                "".getBytes()
        );

        testUser = new UserEntity();
        testUser
                .setUsername("Testy")
                .setPassword(PASSWORD)
                .setEmail("testy@test.test")
                .setRoles(userRoleUtil.setVipRole())
                .setFirstName("JOHNNY");

        userRepository.save(testUser);

        AircraftEntity aircraftEntity =
                new AircraftEntity()
                        .setRegistration("LZ-SPE")
                        .setIcaoModelName("PA28")
                        .setNumberOfEngines(1)
                        .setAircraftClass(AircraftClassEnum.SEP)
                        .setCreator(testUser);

        aircraftRepository.save(aircraftEntity);

        logBindingModel = new LogBindingModel()
                .setDateOfLog(LocalDate.parse("2021-11-01", DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                .setDepartureTime(LocalTime.parse("20:00", DateTimeFormatter.ofPattern("HH:mm")))
                .setArrivalTime(LocalTime.parse("21:00", DateTimeFormatter.ofPattern("HH:mm")))
                .setDepartureAerodrome("Sofia Airport")
                .setArrivalAerodrome("Sofia Airport")
                .setAircraft("LZ-SPE")
                .setLandings(1)
                .setPilotInCommandName("Self")
                .setGpxLog(multipartFile);

    }

    @AfterEach
    void clean() {
        userRepository.deleteAll();
        logRepository.deleteAll();
        aircraftRepository.deleteAll();
    }

    @Test
    void testCreateLog() {
        assertEquals(0, logRepository.count());
        logService.createNewLog(logBindingModel, "Testy");
        assertEquals(1, logRepository.count());
    }


    @Test
    void testUpdateLog() {
        assertEquals(0, logRepository.count());
        logService.createNewLog(logBindingModel, "Testy");
        assertEquals(1, logRepository.count());
        logBindingModel.setId(1L);
        logBindingModel.setDepartureAerodrome("Plovdiv International Airport");

        logService.updateLog(logBindingModel);

        assertEquals(1, logRepository.count());
        assertEquals(logBindingModel.getDepartureAerodrome(), logRepository.findById(1L).get().getDepartureAerodrome().getName());
    }

    @Test
    void testGetAllLogs() {
        logService.createNewLog(logBindingModel, "Testy");
        logService.createNewLog(logBindingModel, "Testy");
        logService.createNewLog(logBindingModel, "Testy");
        assertEquals(3, logService.getAllLogs("Testy").size());
    }







}
