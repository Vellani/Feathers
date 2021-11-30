package com.example.feathers.web;

import com.example.feathers.database.model.entity.UserEntity;
import com.example.feathers.database.repository.UserRepository;
import com.example.feathers.util.UserRoleUtil;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
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
class AdminControllerTest {

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserRoleUtil userRoleUtil;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private MockMvc mockMvc;

    private UserEntity testUser;

    @BeforeEach
    void init() {
        userRepository.deleteAll();

        testUser = new UserEntity()
                .setUsername("Testy")
                .setPassword(passwordEncoder.encode("12345"))
                .setEmail("testy@test.test")
                .setRoles(userRoleUtil.setAdminRole())
                .setFirstName("JOHNNY");

        userRepository.save(testUser);
    }

    @AfterEach
    void reset() {
        userRepository.deleteAll();
    }

    @Test
    @WithMockUser(username = "Testy", roles = {"USER", "VIP", "ADMIN"})
    void testAdminPanelGet() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/profile/admin"))
                .andExpect(status().isOk())
                .andExpect(view().name("admin"));

        mockMvc.perform(MockMvcRequestBuilders.get("/profile/admin")
                .param("username", "Admin")
        )
                .andExpect(status().isOk())
                .andExpect(view().name("admin"));
    }

    @Test
    @WithMockUser(username = "Admin", roles = {"USER", "VIP", "ADMIN"})
    void testUserDelete() throws Exception {

        assertEquals(1, userRepository.findAll().size());

        mockMvc.perform(
                MockMvcRequestBuilders.post("/profile/admin/delete")
                        .param("id", userRepository.findByUsername("Testy").get().getId().toString())
                        .with(csrf())
        )
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/profile/admin"));

        assertEquals(0, userRepository.findAll().size());
    }





}