package com.example.feathers.service;

import com.example.feathers.model.binding.LogAddBindingModel;
import com.example.feathers.model.view.ListedLogViewModel;

import java.security.Principal;
import java.util.List;

public interface LogService {
    void createNewLog(LogAddBindingModel logAddBindingModel, String username);

    List<ListedLogViewModel> getAllLogs(String username);

    LogAddBindingModel findById(Long id);

    void deleteById(Long id);
}
