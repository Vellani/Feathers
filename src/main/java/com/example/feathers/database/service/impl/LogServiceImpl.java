package com.example.feathers.database.service.impl;

import com.example.feathers.database.model.binding.LogBindingModel;
import com.example.feathers.database.model.common.CommonLogInterface;
import com.example.feathers.database.model.entity.AircraftEntity;
import com.example.feathers.database.model.entity.LogEntity;
import com.example.feathers.database.model.seed.FlightLogSeed;
import com.example.feathers.database.model.view.ListedLogViewModel;
import com.example.feathers.database.service.LogService;
import com.example.feathers.database.model.service.LogServiceModel;
import com.example.feathers.database.repository.LogRepository;
import com.example.feathers.database.service.AerodromeService;
import com.example.feathers.database.service.AircraftService;
import com.example.feathers.database.service.UserService;
import com.example.feathers.util.SimplePair;
import com.example.feathers.web.exception.impl.FlightLogNotFoundException;
import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

import static com.example.feathers.global.Constants.FLIGHT_LOGS_PATH;

@Service
public class LogServiceImpl implements LogService {

    private final LogRepository logRepository;
    private final AircraftService aircraftService;
    private final AerodromeService aerodromeService;
    private final UserService userService;
    private final ModelMapper modelMapper;
    private final Gson gson;

    public LogServiceImpl(LogRepository logRepository,
                          AircraftService aircraftService,
                          AerodromeService aerodromeService,
                          UserService userService,
                          ModelMapper modelMapper,
                          Gson gson) {
        this.logRepository = logRepository;
        this.aircraftService = aircraftService;
        this.aerodromeService = aerodromeService;
        this.userService = userService;
        this.modelMapper = modelMapper;
        this.gson = gson;
    }

    @Override
    public void createNewLog(LogBindingModel logBindingModel, String username) {
        LogServiceModel serviceModel = createServiceModel(logBindingModel, username);
        LogEntity newLogEntity = modelMapper.map(serviceModel, LogEntity.class);
        logRepository.save(newLogEntity);
    }

    @Override
    public void updateLog(LogBindingModel logBindingModel) {
        LogEntity log = logRepository.findById(logBindingModel.getId()).orElseThrow(FlightLogNotFoundException::new);

        // All this mess is a result of the ModelMapper fiasco of 2021, couldn't get it to have a
        // conditional where if gpxLog.isEmpty().skip the mapping
        LogServiceModel serviceModel = createServiceModel(logBindingModel, log.getCreator().getUsername());
        boolean empty = logBindingModel.getGpxLog().isEmpty();
        Byte[] gpxLog = log.getGpxLog();

        modelMapper.map(serviceModel, log);

        if (empty) log.setGpxLog(gpxLog);
        logRepository.save(log);
    }

    private <T extends CommonLogInterface> LogServiceModel createServiceModel(T inputModel, String username) {
        return modelMapper.map(inputModel, LogServiceModel.class)
                .setDepartureAerodrome(aerodromeService.findByName(inputModel.getDepartureAerodrome()))
                .setArrivalAerodrome(aerodromeService.findByName(inputModel.getArrivalAerodrome()))
                .setAircraft(aircraftService.findByRegistration(inputModel.getAircraft()))
                .setCreator(userService.findUserByUsername(username));
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
        LogEntity logEntity = logRepository.findById(id).orElseThrow(FlightLogNotFoundException::new);
        return modelMapper.map(logEntity, LogBindingModel.class)
                .setHasGPX(logEntity.getGpxLog().length != 0);
    }

    @Override
    public boolean isOwnerOfLog(Long logID, String name) {
        if (logID == null) return true;
        return logRepository.findByIdAndCreator_Username(logID, name).isPresent();
    }

    @Override
    public List<Byte[]> findAllGpxFilesForUsername(String username) {
        return logRepository.findAllGpxFilesForUsername(username);
    }

    @Override
    public SimplePair<String, Integer> findMostUsedAircraft() {
        // May be  good idea to put a limit in the query itself
        List<SimplePair<String, Integer>> list =
                logRepository.findMostUsedAircraft();
        if (list.isEmpty()) return new SimplePair<>("", null);
        return list.get(0);
    }

    @Override
    public SimplePair<SimplePair<String, Integer>, SimplePair<String, Integer>> findMostUsedAirport() {
        List<SimplePair<String, Integer>> depList =
                logRepository.findMostUsedDepAirport();
        List<SimplePair<String, Integer>> arrList =
                logRepository.findMostUsedArrAirport();
        if (depList.isEmpty() || arrList.isEmpty()) return null;
        return new SimplePair<>(depList.get(0), arrList.get(0));
    }

    @Override
    public void cleanUp() {
        logRepository.cleanUpDatabase();
    }

    @Override
    public void startDebugMode() throws IOException {
        Set<FlightLogSeed> collect = Arrays.stream(
                gson.fromJson(
                        Files.readString(Path.of(FLIGHT_LOGS_PATH)),
                        FlightLogSeed[].class))
                .collect(Collectors.toSet());

        collect.stream()
                .map(e -> createServiceModel(e, e.getCreator()))
                .map(m -> modelMapper.map(m, LogEntity.class))
                .forEach(logRepository::save);

    }

}
