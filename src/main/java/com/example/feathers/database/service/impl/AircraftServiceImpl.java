package com.example.feathers.database.service.impl;

import com.example.feathers.database.cloudinary.CloudinaryImage;
import com.example.feathers.database.cloudinary.CloudinaryService;
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
import org.springframework.context.annotation.Profile;
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
    private final CloudinaryService cloudinaryService;

    public AircraftServiceImpl(UserService userService,
                               AircraftRepository aircraftRepository,
                               @Lazy LogService logService,
                               ModelMapper modelMapper,
                               Gson gson,
                               CloudinaryService cloudinaryService) {
        this.userService = userService;
        this.aircraftRepository = aircraftRepository;
        this.logService = logService;
        this.modelMapper = modelMapper;
        this.gson = gson;
        this.cloudinaryService = cloudinaryService;
    }

    @Override
    public void addNewAircraft(AircraftBindingModel aircraftBindingModel, String currentAccountName) throws IOException {
        AircraftServiceModel serviceModel = modelMapper.map(aircraftBindingModel, AircraftServiceModel.class);

        if (!aircraftBindingModel.getPictureFile().isEmpty()) {
            CloudinaryImage upload = cloudinaryService.upload(aircraftBindingModel.getPictureFile());
            serviceModel.setPicturePublicId(upload.getPublicId()).setPictureUrl(upload.getUrl());
        }

        AircraftEntity aircraft = modelMapper.map(serviceModel, AircraftEntity.class);
        aircraft.setCreator(userService.findUserByUsername(currentAccountName));
        aircraftRepository.save(aircraft);
    }

    @Override
    public void updateAircraft(AircraftBindingModel aircraftBindingModel) throws IOException {
        AircraftEntity aircraftToUpdate = aircraftRepository.findById(aircraftBindingModel.getId()).orElseThrow();

        AircraftServiceModel serviceModel = modelMapper.map(aircraftBindingModel, AircraftServiceModel.class);

        if (!aircraftBindingModel.getPictureFile().isEmpty()) {
            if (!aircraftToUpdate.getPictureUrl().isEmpty()) {
                cloudinaryService.delete(aircraftToUpdate.getPicturePublicId());
            }
            CloudinaryImage upload = cloudinaryService.upload(aircraftBindingModel.getPictureFile());
            serviceModel.setPictureUrl(upload.getPublicId()).setPictureUrl(upload.getUrl());
        }

        modelMapper.map(serviceModel, aircraftToUpdate);
        aircraftRepository.save(aircraftToUpdate);
    }

    @Override
    public void cleanUp() {
        aircraftRepository.cleanUpDatabase();
    }

    @Override
    public boolean alreadyExists(String registration) {
        return aircraftRepository.findByRegistration(registration).isPresent();
    }

    @Override
    public List<String> findAllMatchingRegistrations(String username, String reg) {
        return aircraftRepository.findAllMatchingRegistrations(username, reg);
    }

    @Override
    public AircraftEntity findByRegistration(String registration) {
        return aircraftRepository.findByRegistration(registration).orElseThrow();
    }

    @Override
    public List<ListAircraftViewModel> findAllAircraftForUser(String name) {
        List<AircraftEntity> aircraftEntities = aircraftRepository.findByCreator_Username(name);
        return aircraftEntities.stream().map(e -> {
            ListAircraftViewModel viewModel = modelMapper.map(e, ListAircraftViewModel.class);
            viewModel.setFlights(logService.countAllFlightsWithAircraft(e));
            return viewModel;
        }).collect(Collectors.toList());
    }

    @Override
    public void deleteById(Long id) {
        aircraftRepository.deleteById(id);
    }

    @Override
    public boolean isOwnerOfAircraft(Long id, String name) {
        if (id == null) return true; // if id == null -> new aircraft
        return aircraftRepository.findByIdAndCreator_Username(id, name).isPresent();
    }

    @Override
    public AircraftBindingModel findById(Long id) {
        return modelMapper.map(aircraftRepository.findById(id).orElseThrow(), AircraftBindingModel.class);
    }

    @Override
    public AircraftEntity findAircraftEntityById(Long id) {
        return aircraftRepository.findById(id).orElseThrow();
    }

    @Override
    public boolean existByUsernameAndRegistration(String name, String registration) {
        return aircraftRepository.existsByCreator_UsernameAndRegistration(name, registration);
    }

    @Override
    public void startDebugMode() throws IOException {
        if (aircraftRepository.count() == 0) {
            // Does not go through the Service Model since this is Custom initialize
            Set<AircraftSeed> collect = Arrays.stream(
                    gson.fromJson(
                            Files.readString(Path.of(AIRCRAFT_PATH)),
                            AircraftSeed[].class))
                    .collect(Collectors.toSet());

            collect.forEach(e -> {
                AircraftEntity aircraft = modelMapper.map(e, AircraftEntity.class);
                aircraft.setAircraftClass(AircraftClassEnum.valueOf(e.getAircraftClass()));
                aircraft.setCreator(userService.findUserByUsername(e.getOwner()));

                aircraftRepository.save(aircraft);
            });
            System.out.println("Added " + aircraftRepository.count() + " Aircraft in the Database under user ID 2! (Name: Normal, Pass: 12345)");
        }
    }
}
