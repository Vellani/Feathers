package com.example.feathers.database.service;

import com.example.feathers.database.model.binding.UpdateUserDetailsBindingModel;
import com.example.feathers.database.model.binding.UserRegisterBindingModel;
import com.example.feathers.database.model.entity.UserEntity;
import com.example.feathers.database.model.view.ListedAccountsViewModel;

import java.util.List;

public interface UserService {
    boolean userExists(String username, String email);

    void registerNewUser(UserRegisterBindingModel userRegisterBindingModel);

    void initialize();

    UserEntity findUserByUsername(String name);

    List<ListedAccountsViewModel> getAll();

    List<String> findUsersForAdmin(String username);

    List<ListedAccountsViewModel> findUsersMatchingTheUsername(String username);

    boolean delete(Long id, String name);

    UpdateUserDetailsBindingModel findAccountDetailsByUsername(String name);

    <T>  void updateUserDetails(T bindingModel, String name);

    boolean setNewAccountLevel(String json, String name);

    void startDebugMode();

}
