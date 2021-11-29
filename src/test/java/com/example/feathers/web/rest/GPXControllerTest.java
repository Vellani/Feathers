package com.example.feathers.web.rest;

import com.example.feathers.database.model.entity.UserEntity;
import com.example.feathers.database.repository.LogRepository;
import com.example.feathers.database.repository.UserRepository;
import com.example.feathers.database.service.AerodromeService;
import com.example.feathers.database.service.AircraftService;
import com.example.feathers.database.service.LogService;
import com.example.feathers.util.ObjectConverter;
import com.example.feathers.util.UserRoleUtil;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.io.IOException;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(
        locations = "classpath:application-integrationtest.properties")
class GPXControllerTest {
    @Autowired
    private UserRoleUtil userRoleUtil;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private MockMvc mockMvc;
    private UserEntity testUser;

    @MockBean
    private LogService logService;
    @MockBean
    private ObjectConverter objectConverter;

    private static final String PASSWORD = "12345";

    @BeforeEach
    void init(){
        testUser = new UserEntity();
        testUser
                .setUsername("Testy")
                .setPassword(PASSWORD)
                .setEmail("testy@test.test")
                .setRoles(userRoleUtil.setVipRole())
                .setFirstName("JOHNNY");

        userRepository.save(testUser);
    }

    @AfterEach
    void reset() {
        userRepository.deleteAll();
    }

    @Test
    @WithMockUser(username = "Testy", roles = {"USER", "VIP"})
    void testGetRegistrationsFromAPI() throws Exception {

        Mockito.when(logService.isOwnerOfLog(1L, "Testy")).thenReturn(true);
        Mockito.when(objectConverter.toPrimitives(logService.findSpecificGPXLog(1L))).thenReturn("answer".getBytes());

        mockMvc.perform(MockMvcRequestBuilders.get("/api/param")
                .param("gpx", "1")
                .accept(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentType(MediaType.APPLICATION_XML))
                .andExpect(jsonPath("$", is("answer")));

    }


}