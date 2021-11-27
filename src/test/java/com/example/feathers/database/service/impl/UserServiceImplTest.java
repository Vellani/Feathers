package com.example.feathers.database.service.impl;

import com.example.feathers.database.model.binding.UserRegisterBindingModel;
import com.example.feathers.database.model.entity.UserEntity;
import com.example.feathers.database.model.entity.UserRoleEntity;
import com.example.feathers.database.model.entity.enums.UserRolesEnum;
import com.example.feathers.database.repository.UserRepository;
import com.example.feathers.database.repository.UserRoleRepository;
import com.example.feathers.util.UserRoleUtil;
import com.google.gson.Gson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.test.context.TestPropertySource;

import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@TestPropertySource(
        locations = "classpath:application-integrationtest.properties")
class UserServiceImplTest {

    private UserEntity testUserEntity;
    private UserServiceImpl userService;

    @Mock
    private UserRoleUtil userRoleUtil;

    @Mock
    private UserRepository mockedUserRepository;

    @BeforeEach
    void init() {
        userService = new UserServiceImpl(mockedUserRepository,
                new ModelMapper(), new Pbkdf2PasswordEncoder(), userRoleUtil, new Gson());

        testUserEntity = new UserEntity();
        testUserEntity.setUsername("Admin");
        testUserEntity.setEmail("account_email0@test.test");
        testUserEntity.setPassword("12345");
        testUserEntity.setRoles(Set.of(new UserRoleEntity().setRole(UserRolesEnum.ADMIN)));
    }

    @Test
    void testUserExists() {
        Mockito.when(mockedUserRepository.existsByUsernameOrEmail(testUserEntity.getUsername(), testUserEntity.getEmail()))
                .thenReturn(true);
        assertTrue(userService.userExists(testUserEntity.getUsername(), testUserEntity.getEmail()));
    }

    @Test
    void testRegisterNewUser() {

        Mockito.when(userRoleUtil.setUserRole()).thenReturn(Set.of(new UserRoleEntity().setRole(UserRolesEnum.USER)));
        Mockito.when(mockedUserRepository.count()).thenReturn(1L);

        UserRegisterBindingModel urbm = new UserRegisterBindingModel()
                .setUsername("Testy")
                .setPassword("12345")
                .setConfirmPassword("12345")
                .setEmail("testy@test.test");

        userService.registerNewUser(urbm);
        assertEquals(1L, mockedUserRepository.count());

    }






}