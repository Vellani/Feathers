package com.example.feathers.web;

import com.example.feathers.database.model.entity.AircraftEntity;
import com.example.feathers.database.model.entity.UserEntity;
import com.example.feathers.database.model.entity.enums.AircraftClassEnum;
import com.example.feathers.database.repository.*;
import com.example.feathers.database.service.AerodromeService;
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
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

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

    private MockHttpServletRequestBuilder request;

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

        UserEntity testUser =
                new UserEntity()
                        .setUsername("Normal")
                        .setPassword(passwordEncoder.encode("12345"))
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

        request = MockMvcRequestBuilders.multipart("/profile/log")
                .file("gpxLog", multipartFile.getBytes())
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .param("dateOfLog", LocalDate.now().toString())
                .param("departureTime", LocalTime.now().toString())
                .param("arrivalTime", LocalTime.now().toString())
                .param("departureAerodrome", "Sofia Airport")
                .param("arrivalAerodrome", "Sofia Airport")
                .param("aircraft", aircraftEntity.getRegistration())
                .param("landings", "1")
                .param("pilotInCommandName", "Self")
                .with(csrf());
    }

    @AfterEach
    void reset() {
        userRepository.deleteAll();
        logRepository.deleteAll();
    }

    @Test
    @WithMockUser(username = "Normal", roles = {"USER", "VIP"})
    void testNewLogGet() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/profile/log"))
                .andExpect(status().isOk())
                .andExpect(view().name("log"));
    }

    @Test
    @WithMockUser(username = "Normal", roles = {"USER","VIP"})
    void testSuccessLogCreate() throws Exception {

        assertTrue(logRepository.findAll().isEmpty());

        mockMvc.perform(request)
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("logbook"));

        assertFalse(logRepository.findAll().isEmpty());
    }

    @Test
    @WithMockUser(username = "Normal", roles = {"USER","VIP"})
    void testFailedLogCreate() throws Exception {

        assertTrue(logRepository.findAll().isEmpty());

        mockMvc.perform(request.param("departureAerodrome", "INVALID_AERODROME"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("log"));

        assertTrue(logRepository.findAll().isEmpty());
    }

    @Test
    @WithMockUser(username = "Normal", roles = {"USER","VIP"})
    void testDeleteLog() throws Exception {

        assertTrue(logRepository.findAll().isEmpty());

        mockMvc.perform(request);

        assertFalse(logRepository.findAll().isEmpty());

        mockMvc.perform(
                MockMvcRequestBuilders.post("/profile/log/delete")
                        .param("id", "1")
                        .with(csrf())
        )
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/profile/logbook"));

        assertTrue(logRepository.findAll().isEmpty());


    }





}