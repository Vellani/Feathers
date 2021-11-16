package com.example.feathers.service;

import com.example.feathers.model.binding.UserRegisterBindingModel;
import com.example.feathers.model.entity.UserEntity;

public interface UserService {
    boolean userExists(String username, String email);

    void registerNewUser(UserRegisterBindingModel userRegisterBindingModel);

    void initialize();

    UserEntity findById(int i);

    UserEntity findUserByUsername(String name);
}
