package com.example.feathers.service.impl;

import com.example.feathers.model.binding.LogAddBindingModel;
import com.example.feathers.model.entity.AerodromeEntity;
import com.example.feathers.model.entity.AircraftEntity;
import com.example.feathers.model.entity.LogEntity;
import com.example.feathers.model.service.LogServiceModel;
import com.example.feathers.model.view.ListedLogViewModel;
import com.example.feathers.repository.LogRepository;
import com.example.feathers.service.AerodromeService;
import com.example.feathers.service.AircraftService;
import com.example.feathers.service.LogService;
import com.example.feathers.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LogServiceImpl implements LogService {

    private final LogRepository logRepository;
    private final AircraftService aircraftService;
    private final AerodromeService aerodromeService;
    //private final UserService userService;
    private final ModelMapper modelMapper;

    public LogServiceImpl(LogRepository logRepository,
                          AircraftService aircraftService,
                          AerodromeService aerodromeService,
                          UserService userService,
                          ModelMapper modelMapper) {
        this.logRepository = logRepository;
        this.aircraftService = aircraftService;
        this.aerodromeService = aerodromeService;
        //this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @Override
    public void createNewLog(LogAddBindingModel logAddBindingModel) {
        logRepository.save(modelMapper.map(createServiceModel(logAddBindingModel), LogEntity.class));
    }

    @Override
    public void updateLog(LogAddBindingModel logAddBindingModel) {
        logRepository.save(modelMapper.map(createServiceModel(logAddBindingModel), LogEntity.class));
    }

    @Override
    public void deleteById(Long id) {
        logRepository.deleteById(id);
    }

    @Override
    public List<ListedLogViewModel> getAllLogs() {
        return logRepository.findAllAndOrderByDateThenTime().stream().map(e -> {
            ListedLogViewModel listedLogViewModel = new ListedLogViewModel();

            // Manual Mapping
            listedLogViewModel.setId(e.getId())
                    .setDateOfLog(e.getDateOfLog())
                    .setDepartureAerodrome(e.getDepartureAerodrome().toString())
                    .setDepartureTime(e.getDepartureTime().toString())
                    .setDepartureFlag(e.getDepartureAerodrome().getCountry())
                    .setArrivalAerodrome(e.getArrivalAerodrome().toString())
                    .setArrivalTime(e.getArrivalTime().toString())
                    .setArrivalFlag(e.getArrivalAerodrome().getCountry())
                    .setRegistration(e.getAircraft().getRegistration())
                    .setAircraftModel(e.getAircraft().getIcaoModelName());

            return listedLogViewModel;
        }).collect(Collectors.toList());
    }

    @Override
    public LogAddBindingModel findById(Long id) {
        // Will always map, since we take real id
        LogEntity logEntity = logRepository.findById(id).orElse(null);
        return modelMapper.map(logEntity, LogAddBindingModel.class);
    }

    private LogServiceModel createServiceModel(LogAddBindingModel logAddBindingModel) {
        // TODO Find current loged in user;
        //UserEntity user = userService.findBySomething()

        // TODO GPX
        AircraftEntity aircraft = aircraftService.findByRegistration(logAddBindingModel.getAircraft());
        AerodromeEntity depAerodrome = aerodromeService.findByName(logAddBindingModel.getDepartureAerodrome());
        AerodromeEntity arrAerodrome = aerodromeService.findByName(logAddBindingModel.getArrivalAerodrome());

        LogServiceModel logServiceModel = modelMapper.map(logAddBindingModel, LogServiceModel.class);

        logServiceModel.setDepartureAerodrome(depAerodrome)
                .setArrivalAerodrome(arrAerodrome)
                .setAircraft(aircraft);

        return logServiceModel;
    }

}
