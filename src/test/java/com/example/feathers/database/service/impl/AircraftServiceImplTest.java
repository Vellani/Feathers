package com.example.feathers.database.service.impl;

import com.example.feathers.database.model.binding.AircraftBindingModel;
import com.example.feathers.database.model.entity.UserEntity;
import com.example.feathers.database.model.entity.UserRoleEntity;
import com.example.feathers.database.model.entity.enums.AircraftClassEnum;
import com.example.feathers.database.repository.AircraftRepository;
import com.example.feathers.database.repository.UserRepository;
import com.example.feathers.database.service.AircraftService;
import com.example.feathers.util.UserRoleUtil;
import com.example.feathers.web.exception.impl.AircraftDoesNotExistException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(
        locations = "classpath:application-integrationtest.properties")
class AircraftServiceImplTest {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AircraftService aircraftService;
    @Autowired
    private AircraftRepository aircraftRepository;
    @Autowired
    private UserRoleUtil userRoleUtil;

    private AircraftBindingModel aircraftBindingModel;

    @BeforeEach
    void setup() throws IOException {
        aircraftRepository.deleteAll();
        userRepository.deleteAll();

        UserEntity testUser = new UserEntity()
                .setUsername("Testy")
                .setPassword("12345")
                .setEmail("testy@test.test")
                .setRoles(userRoleUtil.setUserRole())
                .setFirstName("JOHNNY");
        userRepository.save(testUser);

        MultipartFile multipartFile = new MockMultipartFile(
                "0",
                "",
                MediaType.TEXT_PLAIN_VALUE,
                "".getBytes()
        );

        aircraftBindingModel = new AircraftBindingModel()
                .setAircraftClass(AircraftClassEnum.SEP)
                .setIcaoModelName("TEST")
                .setRegistration("LZ-TEST")
                .setNumberOfEngines(1)
                .setPictureFile(multipartFile);

        aircraftService.addNewAircraft(aircraftBindingModel, "Testy");
    }


    @AfterEach
    void destroy() {
        aircraftRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    void testAddNewAircraft() {
        assertTrue(aircraftRepository.findByRegistration("LZ-TEST").isPresent());
    }

    @Test
    void testUpdateAircraft() throws IOException {

        aircraftBindingModel.setId(aircraftRepository.findByRegistration("LZ-TEST").get().getId());
        aircraftBindingModel.setRegistration("LZ-NEW");

        assertTrue(aircraftRepository.findByRegistration("LZ-TEST").isPresent());
        aircraftService.updateAircraft(aircraftBindingModel);
        assertTrue(aircraftRepository.findByRegistration("LZ-NEW").isPresent());
    }

    @Test
    void testFindAllMatchingRegistrations() {
        assertFalse(aircraftService.findAllMatchingRegistrations("Testy", "LZ-TEST").isEmpty());
    }

    @Test
    void testFindByRegistration() {
        assertNotNull(aircraftService.findByRegistration("LZ-TEST"));
        assertThrows(AircraftDoesNotExistException.class, () -> aircraftService.findByRegistration("LZ-NULL"));
    }

    @Test
    void testDeleteById() {
        aircraftService.deleteById(aircraftRepository.findByRegistration("LZ-TEST").get().getId());
        assertThrows(AircraftDoesNotExistException.class, () -> aircraftService.findByRegistration("LZ-TEST"));
    }

    // Oh for F* sake
    @Test
    void testStartDebugMode() throws IOException {
        aircraftRepository.deleteAll();

        UserEntity testUser = new UserEntity()
                .setUsername("Normal")
                .setPassword("12345")
                .setEmail("testdfgasdy@test.test")
                .setRoles(userRoleUtil.setUserRole())
                .setFirstName("adsfJOHNNY");
        userRepository.save(testUser);

        UserEntity testUserTwo = new UserEntity()
                .setUsername("Vippp")
                .setPassword("12345")
                .setEmail("testysdfg@test.test")
                .setRoles(userRoleUtil.setUserRole())
                .setFirstName("JOHNNY");
        userRepository.save(testUserTwo);

        aircraftService.startDebugMode();
        assertEquals(14, aircraftRepository.findAll().size());
    }


}