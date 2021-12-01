package com.example.feathers.database.service;

import com.example.feathers.database.model.binding.AircraftBindingModel;
import com.example.feathers.database.model.entity.AircraftEntity;
import com.example.feathers.database.model.view.ListAircraftViewModel;
import com.example.feathers.util.SimplePair;

import java.io.IOException;
import java.security.Principal;
import java.util.List;

public interface AircraftService {
    void addNewAircraft(AircraftBindingModel aircraftBindingModel, String currentAccountName) throws IOException;

    boolean alreadyExists(String registration);

    List<String> findAllMatchingRegistrations(String username, String reg);

    AircraftEntity findByRegistration(String registration);

    List<ListAircraftViewModel> findAllAircraftForUser(String name);

    void deleteById(Long id);

    boolean isOwnerOfAircraft(Long id, String name);

    AircraftBindingModel findById(Long id);
    AircraftEntity findAircraftEntityById(Long id);

    boolean existByUsernameAndRegistration(String name, String registration);

    void updateAircraft(AircraftBindingModel aircraftBindingModel) throws IOException;

    void cleanUp();

    void startDebugMode() throws IOException;

}
