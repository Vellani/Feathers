package com.example.feathers.service;

import com.example.feathers.model.binding.LogAddBindingModel;
import com.example.feathers.model.view.ListedLogViewModel;

import java.util.List;

public interface LogService {
    void createNewLog(LogAddBindingModel logAddBindingModel);

    List<ListedLogViewModel> getAllLogs();

    LogAddBindingModel findById(Long id);

    void updateLog(LogAddBindingModel logAddBindingModel);

    void deleteById(Long id);
}
