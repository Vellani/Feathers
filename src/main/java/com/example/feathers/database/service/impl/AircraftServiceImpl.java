package com.example.feathers.database.service.impl;

import com.example.feathers.database.model.binding.AircraftBindingModel;
import com.example.feathers.database.model.entity.AircraftEntity;
import com.example.feathers.database.model.entity.enums.AircraftClassEnum;
import com.example.feathers.database.model.seed.AircraftSeed;
import com.example.feathers.database.model.service.AircraftServiceModel;
import com.example.feathers.database.model.view.ListAircraftViewModel;
import com.example.feathers.database.repository.AircraftRepository;
import com.example.feathers.database.service.AircraftService;
import com.example.feathers.database.service.LogService;
import com.example.feathers.database.service.UserService;
import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.Principal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static com.example.feathers.global.Constants.AIRCRAFT_PATH;

@Service
public class AircraftServiceImpl implements AircraftService {

    private final UserService userService;
    private final AircraftRepository aircraftRepository;
    private final LogService logService;
    private final ModelMapper modelMapper;
    private final Gson gson;

    public AircraftServiceImpl(UserService userService,
                               AircraftRepository aircraftRepository,
                               @Lazy LogService logService,
                               ModelMapper modelMapper,
                               Gson gson) {
        this.userService = userService;
        this.aircraftRepository = aircraftRepository;
        this.logService = logService;
        this.modelMapper = modelMapper;
        this.gson = gson;
    }

    @Override
    public void addNewAircraft(AircraftBindingModel aircraftBindingModel, Principal principal) {
        // Uppercassing everything
        aircraftBindingModel.setIcaoModelName(aircraftBindingModel.getIcaoModelName().toUpperCase());
        aircraftBindingModel.setRegistration(aircraftBindingModel.getRegistration().toUpperCase());

        AircraftServiceModel middleManAircraft = modelMapper.map(aircraftBindingModel, AircraftServiceModel.class);
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
    public List<ListAircraftViewModel> findAllAircraftForUser(String name) {
        List<AircraftEntity> aircraftEntities = aircraftRepository.findByCreator_Username(name);
        List<ListAircraftViewModel> collect = aircraftEntities.stream().map(e -> {
            ListAircraftViewModel viewModel = modelMapper.map(e, ListAircraftViewModel.class);
            viewModel.setFlights(logService.countAllFlightsWithAircraft(e));
            return viewModel;
        }).collect(Collectors.toList());
        return collect;
    }

    @Override
    public void deleteById(Long id) {
        aircraftRepository.deleteById(id);
    }

    @Override
    public boolean isOwnerOfAircraft(Long id, String name) {
        if (id == null) return true;
        Optional<AircraftEntity> exists = aircraftRepository.findByIdAndCreator_Username(id, name);
        return exists.isPresent();
    }

    @Override
    public AircraftBindingModel findById(Long id) {
        AircraftEntity aircraftEntity = aircraftRepository.findById(id).orElse(null);
        return modelMapper.map(aircraftEntity, AircraftBindingModel.class);
    }

    @Override
    public AircraftEntity findAircraftEntityById(Long id) {
        // .orElse(null) is irrelevant since we try to delete real ID
        return aircraftRepository.findById(id).orElse(null);
    }

    @Override
    public boolean existByUsernameAndRegistration(String name, String registration) {
        return aircraftRepository.existsByCreator_UsernameAndRegistration(name, registration);
    }

    @Override
    public String findAircraftData() throws IOException {
        return Files.readString(Path.of(AIRCRAFT_PATH));
    }

    @Override
    public void initialize() throws IOException {
        if (aircraftRepository.count() == 0) {
            // Does not go through the Service Model since this is Custom initialize
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
