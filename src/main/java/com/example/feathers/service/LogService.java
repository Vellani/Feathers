package com.example.feathers.service;

import com.example.feathers.model.binding.LogBindingModel;
import com.example.feathers.model.entity.AircraftEntity;
import com.example.feathers.model.entity.UserEntity;
import com.example.feathers.model.view.ListedLogViewModel;

import java.util.List;

public interface LogService {
    void createNewLog(LogBindingModel logBindingModel, String username);

    List<ListedLogViewModel> getAllLogs(String username);

    LogBindingModel findById(Long id);

    void deleteById(Long id);

    Integer countAllFlightsWithAircraft(AircraftEntity aircraft);
}
