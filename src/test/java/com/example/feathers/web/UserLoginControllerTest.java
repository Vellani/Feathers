package com.example.feathers.web;

import com.example.feathers.database.model.entity.UserEntity;
import com.example.feathers.database.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(
        locations = "classpath:application-integrationtest.properties")
class UserLoginControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private UserEntity testUser;

    private static final String PASSWORD = "12345";

    @BeforeEach
    void init() {
        testUser = new UserEntity();
        testUser
                .setUsername("Testy")
                .setPassword(passwordEncoder.encode(PASSWORD))
                .setEmail("testy@test.test");

        userRepository.save(testUser);
    }

    @AfterEach
    void reset() {
        userRepository.deleteAll();
    }

    @Test
    void testLoginGet() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/user/login"))
                .andExpect(status().isOk())
                .andExpect(view().name("login"))
                .andExpect(model().attributeExists("invalid"));
    }

   @Test
   @WithMockUser(username = "Testy", roles = "USER")
   void testSuccessLogin() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.post("/user/login")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("username", testUser.getUsername())
                        .param("password", PASSWORD)
                        .with(csrf())
        )
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/profile/logbook"));
    }

    @Test
    @WithMockUser(username = "Testy", roles = "USER")
    void testFailedLogin() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.post("/user/error")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("username", testUser.getUsername())
                        .param("password", "WRONG_PASSWORD")
                        //.accept(MediaType.APPLICATION_JSON)
                        .with(csrf())
        )
                .andExpect(redirectedUrl("/user/login"));

    }



}