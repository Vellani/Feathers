package com.example.feathers.database.service.impl;

import com.example.feathers.database.model.binding.LogBindingModel;
import com.example.feathers.database.model.entity.AerodromeEntity;
import com.example.feathers.database.model.entity.AircraftEntity;
import com.example.feathers.database.model.entity.LogEntity;
import com.example.feathers.database.model.view.ListedLogViewModel;
import com.example.feathers.database.service.LogService;
import com.example.feathers.database.model.service.LogServiceModel;
import com.example.feathers.database.repository.LogRepository;
import com.example.feathers.database.service.AerodromeService;
import com.example.feathers.database.service.AircraftService;
import com.example.feathers.database.service.UserService;
import com.example.feathers.util.ObjectConverter;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.spi.MappingContext;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class LogServiceImpl implements LogService {

    private final LogRepository logRepository;
    private final AircraftService aircraftService;
    private final AerodromeService aerodromeService;
    private final UserService userService;
    private final ModelMapper modelMapper;

    public LogServiceImpl(LogRepository logRepository,
                          AircraftService aircraftService,
                          AerodromeService aerodromeService,
                          UserService userService,
                          ModelMapper modelMapper) {
        this.logRepository = logRepository;
        this.aircraftService = aircraftService;
        this.aerodromeService = aerodromeService;
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @Override
    public void createNewLog(LogBindingModel logBindingModel, String username) {
        LogServiceModel serviceModel = createServiceModel(logBindingModel, username);
        LogEntity newLogEntity = modelMapper.map(serviceModel, LogEntity.class);
        logRepository.save(newLogEntity);
    }

    @Override
    public void updateLog(LogBindingModel logBindingModel) {
        LogEntity log = logRepository.findById(logBindingModel.getId()).orElseThrow();
        // A bit overkill since all fields but GPX log cannot be empty, it is good practice tho
        /*Stream.of(logBindingModel.getClass().getDeclaredFields()).filter(e -> !Objects.isNull(e)).forEach(field -> {
            System.out.println(field.getName());
        });*/
        // TODO ugly but works
        boolean empty = logBindingModel.getGpxLog().isEmpty();
        Byte[] gpxLog = log.getGpxLog();
        modelMapper.map(logBindingModel, log);

        if (empty) log.setGpxLog(gpxLog);
        logRepository.save(log);
    }

    private LogServiceModel createServiceModel(LogBindingModel logBindingModel, String username) {

        AircraftEntity aircraft = aircraftService.findByRegistration(logBindingModel.getAircraft());
        AerodromeEntity depAerodrome = aerodromeService.findByName(logBindingModel.getDepartureAerodrome());
        AerodromeEntity arrAerodrome = aerodromeService.findByName(logBindingModel.getArrivalAerodrome());

        LogServiceModel logServiceModel = modelMapper.map(logBindingModel, LogServiceModel.class);
        logServiceModel.setDepartureAerodrome(depAerodrome)
                .setArrivalAerodrome(arrAerodrome)
                .setAircraft(aircraft)
                .setCreator(userService.findUserByUsername(username));


        return logServiceModel;
    }

    @Override
    public void deleteById(Long id) {
        logRepository.deleteById(id);
    }

    @Override
    public Integer countAllFlightsWithAircraft(AircraftEntity aircraft) {
        return logRepository.countByCreator_Aircraft(aircraft);
    }

    @Override
    public Byte[] findSpecificGPXLog(Long id) {
        return logRepository.findSpecificGPXLogById(id);
    }




    @Override
    public List<ListedLogViewModel> getAllLogs(String username) {
        return logRepository.findAllAndOrderByDateThenTime(username).stream().map(e -> {
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
                    .setAircraftModel(e.getAircraft().getIcaoModelName())
                    .setHasGPX(e.getGpxLog().length != 0);

            return listedLogViewModel;
        }).collect(Collectors.toList());
    }

    @Override
    public LogBindingModel findById(Long id) {
        LogEntity logEntity = logRepository.findById(id).orElse(null);
        if (logEntity != null) {
            LogBindingModel map = modelMapper.map(logEntity, LogBindingModel.class);
            map.setHasGPX(logEntity.getGpxLog().length != 0);
            return map;
        } else {
            return null;
        }
    }

    @Override
    public boolean isOwnerOfLog(Long logID, String name) {

        if (logID == null) return true;

        Optional<LogEntity> exists = logRepository.findByIdAndCreator_Username(logID, name);
        return exists.isPresent();
    }


}
