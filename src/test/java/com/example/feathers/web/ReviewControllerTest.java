package com.example.feathers.web;

import com.example.feathers.database.model.entity.UserEntity;
import com.example.feathers.database.repository.ReviewRepository;
import com.example.feathers.database.repository.UserRepository;
import com.example.feathers.database.service.ReviewService;
import com.example.feathers.util.UserRoleUtil;
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
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(
        locations = "classpath:application-integrationtest.properties")
class ReviewControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRoleUtil userRoleUtil;

    @Autowired
    private ReviewRepository reviewRepository;

    private UserEntity testUser;

    private static final String PASSWORD = "12345";
    @BeforeEach
    void init() {
        testUser = new UserEntity();
        testUser
                .setUsername("Testy")
                .setPassword(passwordEncoder.encode(PASSWORD))
                .setEmail("testy@test.test")
                .setRoles(userRoleUtil.setVipRole())
                .setFirstName("JOHNNY");

        userRepository.save(testUser);
    }

    @AfterEach
    void reset() {
        userRepository.deleteAll();
        reviewRepository.deleteAll();
    }

    @Test
    @WithMockUser(username = "Testy", roles = {"USER", "VIP"})
    void testLoginGet() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/profile/review"))
                .andExpect(status().isOk())
                .andExpect(view().name("review"));
    }

    @Test
    @WithMockUser(username = "Testy", roles = {"USER","VIP"})
    void testSuccessReview() throws Exception {

        assertTrue(reviewRepository.findAll().isEmpty());

        mockMvc.perform(
                MockMvcRequestBuilders.post("/profile/review")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("content", "Some awesome Review")
                        .param("rating", "5")
                        //.accept(MediaType.APPLICATION_JSON)
                        .with(csrf())
        )
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/profile/logbook"));

        assertFalse(reviewRepository.findAll().isEmpty());

    }

    @Test
    @WithMockUser(username = "Testy", roles = {"USER","VIP"})
    void testFailedReview() throws Exception {

        assertTrue(reviewRepository.findAll().isEmpty());

        mockMvc.perform(
                MockMvcRequestBuilders.post("/profile/review")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("rating", "5")
                        //.accept(MediaType.APPLICATION_JSON)
                        .with(csrf())
        )
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:review"));

        assertTrue(reviewRepository.findAll().isEmpty());

    }

}