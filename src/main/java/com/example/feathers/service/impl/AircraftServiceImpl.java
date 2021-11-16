package com.example.feathers.service.impl;

import com.example.feathers.model.binding.AircraftAddBindingModel;
import com.example.feathers.model.entity.AircraftEntity;
import com.example.feathers.model.entity.enums.AircraftClassEnum;
import com.example.feathers.model.seed.AircraftSeed;
import com.example.feathers.model.service.AircraftServiceModel;
import com.example.feathers.repository.AircraftRepository;
import com.example.feathers.service.AircraftService;
import com.example.feathers.service.UserService;
import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.Principal;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AircraftServiceImpl implements AircraftService {

    private final UserService userService;
    private final AircraftRepository aircraftRepository;
    private final ModelMapper modelMapper;
    private final Gson gson;

    public AircraftServiceImpl(UserService userService, AircraftRepository aircraftRepository, ModelMapper modelMapper, Gson gson) {
        this.userService = userService;
        this.aircraftRepository = aircraftRepository;
        this.modelMapper = modelMapper;
        this.gson = gson;
    }

    @Override
    public void addNewAircraft(AircraftAddBindingModel aircraftAddBindingModel, Principal principal) {
        AircraftServiceModel middleManAircraft = modelMapper.map(aircraftAddBindingModel, AircraftServiceModel.class);
        AircraftEntity aircraft = modelMapper.map(middleManAircraft, AircraftEntity.class);
        aircraft.setCreator(userService.findUserByUsername(principal.getName()));
        aircraftRepository.save(aircraft);
    }

    @Override
    public boolean alreadyExists(String registration) {
        return aircraftRepository.existsByRegistration(registration);
    }

    @Override
    public List<String> findAllMatchingRegistrations(String username, String reg) {
        return aircraftRepository.findAllMatchingRegistrations(username, reg);
    }

    @Override
    public AircraftEntity findByRegistration(String registration) {
        return aircraftRepository.findByRegistration(registration).orElse(null);
    }

    @Override
    public String findAircraftData() throws IOException {
        return Files.readString(Path.of("src/main/resources/data/aircraft.json"));
    }

    @Override
    public void initialize() throws IOException {
        if (aircraftRepository.count() == 0) {
            System.out.println("Loading Aircraft into the database under user ID 2!");
            // Does not go through the Service Model since this is Custom setup
            Set<AircraftSeed> collect = Arrays.stream(gson.fromJson(findAircraftData(), AircraftSeed[].class)).collect(Collectors.toSet());

            collect.forEach(e -> {
                AircraftEntity aircraft = modelMapper.map(e, AircraftEntity.class);
                aircraft.setAircraftClass(AircraftClassEnum.valueOf(e.getAircraftClass()));
                aircraft.setCreator(userService.findById(2)); // TODO Make more aircraft ?

                aircraftRepository.save(aircraft);
            });
            System.out.println("Added " + aircraftRepository.count() + " Aircraft in the Database under user ID 2! (Name: Normal, Pass: 12345)");
        }
    }
}
