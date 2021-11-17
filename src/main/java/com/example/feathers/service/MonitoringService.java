package com.example.feathers.service;

import com.example.feathers.model.view.FilesUploadedView;

public interface MonitoringService {
    void onRequest();
    FilesUploadedView getNumber();
}
