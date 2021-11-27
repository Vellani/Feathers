package com.example.feathers.database.service.impl;

import com.example.feathers.database.model.entity.UserEntity;
import com.example.feathers.database.model.entity.UserRoleEntity;
import com.example.feathers.database.model.entity.enums.UserRolesEnum;
import com.example.feathers.database.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;
import java.util.Set;

@ExtendWith(MockitoExtension.class)
class AccountServiceImplTest {

    private UserEntity testUserEntity;

    private AccountServiceImpl userServiceToTest;

    @Mock
    private UserRepository mockUserRepository;

    @BeforeEach
    void init() {
        userServiceToTest = new AccountServiceImpl(mockUserRepository);
        testUserEntity = new UserEntity();
        testUserEntity.setUsername("Testy");
        testUserEntity.setEmail("Testy@test.test");
        testUserEntity.setPassword("12345");
        testUserEntity.setRoles(Set.of(new UserRoleEntity().setRole(UserRolesEnum.USER)));
    }

    @Test
    void testUserNotFound() {
        Assertions.assertThrows(
                UsernameNotFoundException.class,
                () -> userServiceToTest.loadUserByUsername("Username is invalid")
        );
    }

    @Test
    void loadUserByUsername() {
        Mockito.when(mockUserRepository.findByUsername(testUserEntity.getUsername())).thenReturn(Optional.of(testUserEntity));

        UserDetails actual = userServiceToTest.loadUserByUsername(testUserEntity.getUsername());

        Assertions.assertEquals(actual.getUsername(), testUserEntity.getUsername());
    }
}