package com.example.feathers.web;

import com.example.feathers.database.model.binding.AircraftBindingModel;
import com.example.feathers.database.model.entity.AerodromeEntity;
import com.example.feathers.database.model.entity.AircraftEntity;
import com.example.feathers.database.model.entity.UserEntity;
import com.example.feathers.database.model.entity.enums.AircraftClassEnum;
import com.example.feathers.database.model.seed.AircraftSeed;
import com.example.feathers.database.repository.*;
import com.example.feathers.database.service.AircraftService;
import com.example.feathers.util.UserRoleUtil;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(
        locations = "classpath:application-integrationtest.properties")
class LogControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRoleUtil userRoleUtil;

    @Autowired
    private LogRepository logRepository;

    @Autowired
    private AircraftRepository aircraftRepository;
    @Autowired
    private AircraftService aircraftService;

    @Autowired
    private AerodromeRepository aerodromeRepository;

    private UserEntity testUser;
    private AerodromeEntity aerodromeEntity;
    private AircraftBindingModel aircraftEntity;

    private static final String PASSWORD = "12345";

    @Mock
    private MockMultipartFile multipartFile;

    @BeforeEach
    void init() throws IOException {

        multipartFile = new MockMultipartFile(
                "0",
                "",
                MediaType.TEXT_PLAIN_VALUE,
                "".getBytes()
        );

        testUser = new UserEntity();
        testUser
                .setUsername("Testy")
                .setPassword(passwordEncoder.encode(PASSWORD))
                .setEmail("testy@test.test")
                .setRoles(userRoleUtil.setVipRole())
                .setFirstName("JOHNNY");

        userRepository.save(testUser);

        aircraftEntity = new AircraftBindingModel();
        aircraftEntity.setRegistration("LZ-SPE");
        aircraftEntity.setIcaoModelName("PA28");
        aircraftEntity.setNumberOfEngines(1);
        aircraftEntity.setAircraftClass(AircraftClassEnum.SEP);
        aircraftEntity.setPictureFile(multipartFile);
        aircraftService.addNewAircraft(aircraftEntity, testUser.getUsername());

        aerodromeEntity = new AerodromeEntity();
        aerodromeEntity.setIcaoCode("LBSF");
        aerodromeEntity.setName("Sofia");
        aerodromeEntity.setCountry("bg");
        aerodromeRepository.save(aerodromeEntity);

    }

    @AfterEach
    void reset() {
        userRepository.deleteAll();
        aircraftRepository.deleteAll();
        aerodromeRepository.deleteAll();
        logRepository.deleteAll();
    }

    @Test
    @WithMockUser(username = "Testy", roles = {"USER", "VIP"})
    void testNewLogGet() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/profile/log"))
                .andExpect(status().isOk())
                .andExpect(view().name("log"));
    }

    @Test
    @WithMockUser(username = "Testy", roles = {"USER","VIP"})
    void testSuccessLogCreate() throws Exception {

        assertTrue(logRepository.findAll().isEmpty());

        mockMvc.perform(
                MockMvcRequestBuilders.multipart("/profile/log")
                        //.contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .file("gpxLog", multipartFile.getBytes())
                        .contentType(MediaType.MULTIPART_FORM_DATA)
                        .param("dateOfLog", LocalDate.now().toString())
                        .param("departureTime", LocalTime.now().toString())
                        .param("arrivalTime", LocalTime.now().toString())
                        .param("departureAerodrome", aerodromeEntity.getName())
                        .param("arrivalAerodrome", aerodromeEntity.getName())
                        .param("aircraft", aircraftEntity.getRegistration())
                        .param("landings", "1")
                        .param("pilotInCommandName", "Self")
                        //.accept(MediaType.APPLICATION_JSON)
                        .with(csrf())
        )
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("logbook"));

        assertFalse(logRepository.findAll().isEmpty());
    }

    @Test
    @WithMockUser(username = "Testy", roles = {"USER","VIP"})
    void testFailedLogCreate() throws Exception {

        assertTrue(logRepository.findAll().isEmpty());

        mockMvc.perform(
                MockMvcRequestBuilders.multipart("/profile/log")
                        //.contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .file("gpxLog", multipartFile.getBytes())
                        .contentType(MediaType.MULTIPART_FORM_DATA)
                        .param("dateOfLog", LocalDate.now().toString())
                        .param("departureTime", LocalTime.now().toString())
                        .param("arrivalTime", LocalTime.now().toString())
                        .param("landings", "1")
                        .param("pilotInCommandName", "Self")
                        //.accept(MediaType.APPLICATION_JSON)
                        .with(csrf())
        )
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("log"));

        assertTrue(logRepository.findAll().isEmpty());
    }

    @Test
    @WithMockUser(username = "Testy", roles = {"USER","VIP"})
    void testDeleteLog() throws Exception {

        assertTrue(logRepository.findAll().isEmpty());

        mockMvc.perform(
                MockMvcRequestBuilders.multipart("/profile/log")
                        //.contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .file("gpxLog", multipartFile.getBytes())
                        .contentType(MediaType.MULTIPART_FORM_DATA)
                        .param("dateOfLog", LocalDate.now().toString())
                        .param("departureTime", LocalTime.now().toString())
                        .param("arrivalTime", LocalTime.now().toString())
                        .param("departureAerodrome", aerodromeEntity.getName())
                        .param("arrivalAerodrome", aerodromeEntity.getName())
                        .param("aircraft", aircraftEntity.getRegistration())
                        .param("landings", "1")
                        .param("pilotInCommandName", "Self")
                        //.accept(MediaType.APPLICATION_JSON)
                        .with(csrf())
        );

        assertFalse(logRepository.findAll().isEmpty());

        mockMvc.perform(
                MockMvcRequestBuilders.post("/profile/log/delete")
                        .param("id", "1")
                        //.accept(MediaType.APPLICATION_JSON)
                        .with(csrf())
        )
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/profile/logbook"));

        assertTrue(logRepository.findAll().isEmpty());


    }





}