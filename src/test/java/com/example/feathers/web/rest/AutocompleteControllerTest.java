package com.example.feathers.web.rest;

import com.example.feathers.database.model.entity.AircraftEntity;
import com.example.feathers.database.model.entity.UserEntity;
import com.example.feathers.database.model.entity.enums.AircraftClassEnum;
import com.example.feathers.database.repository.AircraftRepository;
import com.example.feathers.database.repository.UserRepository;
import com.example.feathers.database.service.AerodromeService;
import com.example.feathers.database.service.AircraftService;
import com.example.feathers.util.UserRoleUtil;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.io.IOException;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(
        locations = "classpath:application-integrationtest.properties")
class AutocompleteControllerTest {

    @Autowired
    private UserRoleUtil userRoleUtil;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private AircraftRepository aircraftRepository;

    private UserEntity testUser;


    @BeforeEach
    void init() {

        testUser = new UserEntity();
        testUser
                .setUsername("Testy")
                .setPassword("12345")
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

    }

    @AfterEach
    void reset() {
        userRepository.deleteAll();
    }

    @Test
    @WithMockUser(username = "Testy", roles = {"USER", "VIP"})
    void testGetRegistrationsFromAPI() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/param")
                .param("reg", "lz")
                .accept(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(1)));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/param")
                .param("reg", "")
                .accept(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().is4xxClientError());
    }

    @Test
    @WithMockUser(username = "Admin", roles = {"USER", "VIP"})
    void testGetAerodromesFromAPI() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/param")
                .param("aero", "Sofia")
                .accept(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/param")
                .param("aero", "")
                .accept(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().is4xxClientError());
    }

    @Test
    @WithMockUser(username = "Admin", roles = {"USER", "VIP", "ADMIN"})
    void testGetUsersFromAPI() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/param")
                .param("user", "Testy")
                .accept(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(1)));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/param")
                .param("user", "")
                .accept(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().is4xxClientError());

    }


}