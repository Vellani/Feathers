package com.example.feathers.database.service.impl;

import com.example.feathers.database.model.binding.UserRegisterBindingModel;
import com.example.feathers.database.model.entity.UserEntity;
import com.example.feathers.database.model.entity.UserRoleEntity;
import com.example.feathers.database.model.entity.enums.UserRolesEnum;
import com.example.feathers.database.repository.UserRepository;
import com.example.feathers.database.repository.UserRoleRepository;
import com.example.feathers.database.service.UserService;
import com.example.feathers.util.UserRoleUtil;
import com.google.gson.Gson;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(
        locations = "classpath:application-integrationtest.properties")
class UserServiceImplTest {

    @Autowired
    private UserService userService;
    @Autowired
    private UserRoleUtil userRoleUtil;
    @Autowired
    private UserRepository userRepository;

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
    void clean() {
        userRepository.deleteAll();
    }

    @Test
    void testUserExists() {
        assertTrue(userService.userExists(testUser.getUsername(), testUser.getEmail()));
    }

    @Test
    void testRegisterNewUser() {
        UserRegisterBindingModel urbm = new UserRegisterBindingModel()
                .setUsername("TestyTwo")
                .setPassword("123456")
                .setConfirmPassword("123456")
                .setEmail("testytwo@test.test");
        userService.registerNewUser(urbm);
        assertEquals(2L, userRepository.count());
    }





}