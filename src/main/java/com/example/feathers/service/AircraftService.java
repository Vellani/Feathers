package com.example.feathers.service;

import com.example.feathers.model.binding.AircraftAddBindingModel;
import com.example.feathers.model.entity.AircraftEntity;

import java.io.IOException;
import java.util.List;

public interface AircraftService {
    void addNewAircraft(AircraftAddBindingModel aircraftAddBindingModel);

    boolean alreadyExists(String registration);

    void initialize() throws IOException;

    String findAircraftData() throws IOException;

    List<String> findAllMatchingRegistrations(String reg);

    AircraftEntity findByRegistration(String registration);
}
