package com.example.feathers.service;

import com.example.feathers.model.entity.AerodromeEntity;

import java.io.IOException;
import java.util.List;

public interface AerodromeService {
    void initialize() throws IOException;
    String findAerodromeData() throws IOException;

    List<String> findAllMatchingAerodromes(String toUpperCase);

    AerodromeEntity findByName(String aerodrome);

    boolean existsByName(String departureAerodrome);

}
