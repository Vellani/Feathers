package com.example.feathers.web;

import com.example.feathers.database.model.binding.UserRegisterBindingModel;
import com.example.feathers.database.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(
        locations = "classpath:application-integrationtest.properties")
class UserRegisterControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    private UserRegisterBindingModel testUser;

    @BeforeEach
    void init() {
        userRepository.deleteAll();

        testUser = new UserRegisterBindingModel();
        testUser
                .setUsername("Testy")
                .setPassword("12345")
                .setEmail("testy@test.test")
                .setConfirmPassword("12345");
    }

    @AfterEach
    void reset() {
        userRepository.deleteAll();
    }

    @Test
    void testRegisterUser() throws Exception {
         mockMvc.perform(MockMvcRequestBuilders.get("/user/register"))
                .andExpect(status().isOk())
                .andExpect(view().name("sign-up"))
                .andExpect(model().attributeExists("exists"))
                .andExpect(model().attributeExists("passwordsMatch"));
    }

    @Test
    void testSuccessfulRegistration() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.post("/user/register")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("username", testUser.getUsername())
                        .param("password", testUser.getPassword())
                        .param("confirmPassword", testUser.getConfirmPassword())
                        .param("email", testUser.getEmail())
                        //.accept(MediaType.APPLICATION_JSON)
                        .with(csrf())
        ).andExpect(status().is3xxRedirection());

        assertEquals(1, userRepository.count());
    }

    @Test
    void testFailedRegistration() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.post("/user/register")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("username", testUser.getUsername())
                        .param("password", testUser.getPassword())
                        .param("confirmPassword", "Not-a-matching-password")
                        .param("email", testUser.getEmail())
                        //.accept(MediaType.APPLICATION_JSON)
                        .with(csrf())
        ).andExpect(status().is3xxRedirection());

        assertEquals(0, userRepository.count());
    }



}