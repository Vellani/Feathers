package com.example.feathers.database.service;

import com.example.feathers.database.model.binding.ReviewBindingModel;
import com.example.feathers.database.model.entity.ReviewEntity;
import com.example.feathers.database.model.entity.UserEntity;

import java.security.Principal;

public interface ReviewService {
    void save(ReviewBindingModel reviewBindingModel, Principal principal);
}
