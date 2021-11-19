package com.example.feathers.database.service;

import com.example.feathers.database.model.view.FilesUploadedView;

public interface MonitoringService {
    void onRequest();
    FilesUploadedView getNumber();
}
