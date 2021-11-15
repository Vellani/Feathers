package com.example.feathers.service;

import com.example.feathers.model.binding.UserRegisterBindingModel;

public interface UserService {
    boolean userExists(String username, String email);

    void registerNewUser(UserRegisterBindingModel userRegisterBindingModel);

    void initialize();
}
