package com.example.feathers.service;

import com.example.feathers.model.binding.UserRegisterBindingModel;
import com.example.feathers.model.entity.UserEntity;
import com.example.feathers.model.view.ListedAccountsViewModel;

import java.util.List;

public interface UserService {
    boolean userExists(String username, String email);

    void registerNewUser(UserRegisterBindingModel userRegisterBindingModel);

    void initialize();

    UserEntity findById(int i);

    UserEntity findUserByUsername(String name);

    List<ListedAccountsViewModel> getAll();

    List<String> findUserForAdmin(String username);

    List<ListedAccountsViewModel> findUsersMatchingTheUsername(String username);

    void delete(Long id);
}
