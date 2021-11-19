package com.example.feathers.database.service.impl;

import com.example.feathers.database.model.view.FilesUploadedView;
import com.example.feathers.database.service.MonitoringService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class MonitoringServiceImpl implements MonitoringService {

    // TODO something nice
    private int filesUploaded;

    @Override
    public void onRequest() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if(authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            filesUploaded++;
        }

    }

    @Override
    public FilesUploadedView getNumber() {
        return new FilesUploadedView(filesUploaded);
    }

}
