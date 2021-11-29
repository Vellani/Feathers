package com.example.feathers.web;

import com.example.feathers.database.model.binding.AircraftBindingModel;
import com.example.feathers.database.model.entity.AerodromeEntity;
import com.example.feathers.database.model.entity.AircraftEntity;
import com.example.feathers.database.model.entity.UserEntity;
import com.example.feathers.database.model.entity.enums.AircraftClassEnum;
import com.example.feathers.database.repository.AircraftRepository;
import com.example.feathers.database.repository.UserRepository;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(
        locations = "classpath:application-integrationtest.properties")
class AircraftControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private AircraftRepository aircraftRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserRoleUtil userRoleUtil;
    @Autowired
    private UserRepository userRepository;
    @Mock
    private MockMultipartFile multipartFile;


    private UserEntity testUser;
    private MockHttpServletRequestBuilder request;

    @BeforeEach
    void init() throws IOException {

        multipartFile = new MockMultipartFile(
                "0",
                "",
                MediaType.TEXT_PLAIN_VALUE,
                "".getBytes()
        );

        testUser = new UserEntity()
                .setUsername("Testy")
                .setPassword(passwordEncoder.encode("12345"))
                .setEmail("testy@test.test")
                .setRoles(userRoleUtil.setVipRole())
                .setFirstName("JOHNNY");

        userRepository.save(testUser);

        request = MockMvcRequestBuilders.multipart("/profile/aircraft")
                .file("pictureFile", multipartFile.getBytes())
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .param("registration", "LZ-SPE")
                .param("icaoModelName", "PA28")
                .param("aircraftClass", AircraftClassEnum.SEP.toString())
                .param("numberOfEngines", "1")
                .with(csrf());
    }

    @AfterEach
    void reset() {
        userRepository.deleteAll();
        aircraftRepository.deleteAll();
    }

    @Test
    @WithMockUser(username = "Testy", roles = {"USER", "VIP"})
    void testNewLogGet() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/profile/aircraft"))
                .andExpect(status().isOk())
                .andExpect(view().name("aircraft"));
    }

    @Test
    @WithMockUser(username = "Testy", roles = {"USER","VIP"})
    void testAircraftCreate() throws Exception {

        assertTrue(aircraftRepository.findAll().isEmpty());

        mockMvc.perform(request)
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/profile/dashboard"));

        assertEquals(1, aircraftRepository.findAll().size());

        // Same aircraft cant be saved twice
        mockMvc.perform(request)
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl(""));

        assertEquals(1, aircraftRepository.findAll().size());

    }

    @Test
    @WithMockUser(username = "Testy", roles = {"USER","VIP"})
    void testAircraftDelete() throws Exception {

        AircraftEntity aircraftEntity =
                new AircraftEntity()
                        .setRegistration("LZ-SPE")
                        .setIcaoModelName("PA28")
                        .setNumberOfEngines(1)
                        .setAircraftClass(AircraftClassEnum.SEP)
                        .setCreator(testUser);
        aircraftRepository.save(aircraftEntity);

        assertEquals(1, aircraftRepository.findAll().size());

        mockMvc.perform(
                MockMvcRequestBuilders.post("/profile/aircraft/delete")
                        .param("id", aircraftRepository.findByRegistration("LZ-SPE").get().getId().toString())
                        .with(csrf())
        )
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/profile/dashboard"));
        assertEquals(0, aircraftRepository.findAll().size());
    }



}