package com.example.feathers.database.service;

import com.example.feathers.database.model.binding.LogBindingModel;
import com.example.feathers.database.model.entity.AircraftEntity;
import com.example.feathers.database.model.view.ListedLogViewModel;

import java.util.List;

public interface LogService {
    void createNewLog(LogBindingModel logBindingModel, String username);

    List<ListedLogViewModel> getAllLogs(String username);

    LogBindingModel findById(Long id);

    boolean isOwnerOfLog(Long logID, String name);

    void deleteById(Long id);

    Integer countAllFlightsWithAircraft(AircraftEntity aircraft);
}
