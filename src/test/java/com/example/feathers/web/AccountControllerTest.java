package com.example.feathers.web;

import com.example.feathers.database.model.entity.UserEntity;
import com.example.feathers.database.model.entity.enums.UserRolesEnum;
import com.example.feathers.database.repository.UserRepository;
import com.example.feathers.util.UserRoleUtil;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
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
class AccountControllerTest {


    @Autowired
    private UserRoleUtil userRoleUtil;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private MockMvc mockMvc;
    private UserEntity testUser;

    private static final String PASSWORD = "12345";
    @BeforeEach
    void init() {

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
    void testLogbookGet() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/profile/"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:logbook"));
    }

    @Test
    @WithMockUser(username = "Testy", roles = {"USER", "VIP"})
    void testLogbookVersionTwoGet() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/profile/logbook"))
                .andExpect(status().isOk())
                .andExpect(view().name("logbook"));
    }

    @Test
    @WithMockUser(username = "Testy", roles = {"USER", "VIP"})
    void testDetailsGet() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/profile/details"))
                .andExpect(status().isOk())
                .andExpect(view().name("account"));
    }

    @Test
    @WithMockUser(username = "Testy", roles = {"USER","VIP"})
    void testUpdateSettings() throws Exception {

        mockMvc.perform(
                MockMvcRequestBuilders.post("/profile/details")
                        .param("email", testUser.getEmail())
                        .param("roles", UserRolesEnum.USER.toString())
                        .param("licenseNumber", "1234566")
                        .param("firstName", "First_NAME")
                        .param("lastName", "Last_Name")
                        .param("address", "Sofia,bulgaria,something")
                        .with(csrf())
        )
                .andExpect(status().isOk())
                //.andExpect(redirectedUrl("account"));
                .andExpect(view().name("account"));

        assertEquals("1234566", userRepository.findByUsername(testUser.getUsername()).get().getLicenseNumber());

        mockMvc.perform(
                MockMvcRequestBuilders.post("/profile/details")
                        .param("address", "Sofia,bulgaria,something")
                        .param("email", "invalid-mail")
                        .with(csrf())
        )
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/profile/details"));
    }

    @Test
    @WithMockUser(username = "Testy", roles = {"USER", "VIP"})
    void testPasswordGet() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/profile/details/password"))
                .andExpect(status().isOk())
                .andExpect(view().name("change-password"));
    }

    @Test
    @WithMockUser(username = "Testy", roles = {"USER","VIP"})
    void testPasswordChange() throws Exception {

        String password = "ChangedPassword";


        mockMvc.perform(
                MockMvcRequestBuilders.post("/profile/details/password")
                        .param("password", password)
                        .param("confirmPassword",password)
                        .with(csrf())
        )
                .andExpect(status().isOk())
                .andExpect(view().name("account"));


        mockMvc.perform(
                MockMvcRequestBuilders.post("/profile/details/password")
                        .param("password", password)
                        .param("confirmPassword","NewNewPassword_ButNotMatching")
                        .with(csrf())
        )
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("password"));


    }





}