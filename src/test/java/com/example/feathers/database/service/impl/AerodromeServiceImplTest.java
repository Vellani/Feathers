package com.example.feathers.database.service.impl;

import com.example.feathers.database.service.AerodromeService;
import com.example.feathers.web.exception.impl.AerodromeDoesNotExistException;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(
        locations = "classpath:application-integrationtest.properties")
class AerodromeServiceImplTest {

    @Autowired
    private AerodromeService aerodromeService;

    @Test
    void testFindAllMatchingAerodromes() {
        assertEquals(2, aerodromeService.findAllMatchingAerodromes("Sofia").size());
    }

    @Test
    void testFindByName() {
        assertEquals("Sofia Airport", aerodromeService.findByName("Sofia Airport").getName());
        assertThrows(AerodromeDoesNotExistException.class, () -> aerodromeService.findByName("Sofia THROW"));
    }

    @Test
    void testExistsByName() {
        assertTrue(aerodromeService.existsByName("Sofia Airport"));
    }


}