package com.example.feathers.service;

import com.example.feathers.model.binding.AircraftBindingModel;
import com.example.feathers.model.entity.AircraftEntity;
import com.example.feathers.model.view.ListAircraftViewModel;

import java.io.IOException;
import java.security.Principal;
import java.util.List;

public interface AircraftService {
    void addNewAircraft(AircraftBindingModel aircraftBindingModel, Principal principal);

    boolean alreadyExists(String registration);

    void initialize() throws IOException;

    String findAircraftData() throws IOException;

    List<String> findAllMatchingRegistrations(String username, String reg);

    AircraftEntity findByRegistration(String registration);

    List<ListAircraftViewModel> findAllAircraftForUser(String name);

    void deleteById(Long id);

    boolean isOwnerOfAircraft(Long id, String name);

    AircraftBindingModel findById(Long id);
    AircraftEntity findAircraftEntityById(Long id);

    boolean existByUsernameAndRegistration(String name, String registration);
}
