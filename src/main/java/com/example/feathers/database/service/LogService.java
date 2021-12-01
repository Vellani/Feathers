package com.example.feathers.database.service;

import com.example.feathers.database.model.binding.LogBindingModel;
import com.example.feathers.database.model.entity.AircraftEntity;
import com.example.feathers.database.model.view.ListedLogViewModel;
import com.example.feathers.util.SimplePair;

import java.io.IOException;
import java.util.List;

public interface LogService {
    void createNewLog(LogBindingModel logBindingModel, String username);

    List<ListedLogViewModel> getAllLogs(String username);

    LogBindingModel findById(Long id);

    boolean isOwnerOfLog(Long logID, String name);

    void deleteById(Long id);

    Integer countAllFlightsWithAircraft(AircraftEntity aircraft);

    Byte[] findSpecificGPXLog(Long id);

    void updateLog(LogBindingModel logBindingModel);

    void cleanUp();

    void startDebugMode() throws IOException;

    List<Byte[]> findAllGpxFilesForUsername(String username);

    SimplePair<String, Integer> findMostUsedAircraft();

    SimplePair<SimplePair<String, Integer>, SimplePair<String, Integer>>findMostUsedAirport();
}
