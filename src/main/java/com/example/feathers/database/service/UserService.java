package com.example.feathers.database.service;

import com.example.feathers.database.model.binding.UpdateUserPasswordBindingModel;
import com.example.feathers.database.model.binding.UpdateUserDetailsBindingModel;
import com.example.feathers.database.model.binding.UserRegisterBindingModel;
import com.example.feathers.database.model.entity.UserEntity;
import com.example.feathers.database.model.view.ListedAccountsViewModel;

import java.util.List;

public interface UserService {
    boolean userExists(String username, String email);

    void registerNewUser(UserRegisterBindingModel userRegisterBindingModel);

    void initialize();

    UserEntity findById(int i);

    UserEntity findUserByUsername(String name);

    List<ListedAccountsViewModel> getAll();

    List<String> findUsersForAdmin(String username);

    List<ListedAccountsViewModel> findUsersMatchingTheUsername(String username);

    void delete(Long id);

    UpdateUserDetailsBindingModel findAccountDetailsByUsername(String name);

    void updateUserDetails(UpdateUserDetailsBindingModel updateUserDetailsBindingModel);
    void updatePassword(UpdateUserPasswordBindingModel updateUserPasswordBindingModel);
}
