package com.example.feathers.database.service.impl;

import com.example.feathers.database.model.binding.UpdateUserPasswordBindingModel;
import com.example.feathers.database.model.binding.UpdateUserDetailsBindingModel;
import com.example.feathers.database.model.binding.UpdateUserRoleBindingModel;
import com.example.feathers.database.model.binding.UserRegisterBindingModel;
import com.example.feathers.database.model.entity.UserEntity;
import com.example.feathers.database.model.view.ListedAccountsViewModel;
import com.example.feathers.database.model.service.UserServiceModel;
import com.example.feathers.database.repository.UserRepository;
import com.example.feathers.database.service.UserService;
import com.example.feathers.util.UserRoleUtil;
import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final UserRoleUtil userRoleUtil;
    private final Gson gson;

    public UserServiceImpl(UserRepository userRepository,
                           ModelMapper modelMapper,
                           PasswordEncoder passwordEncoder,
                           UserRoleUtil userRoleUtil,
                           Gson gson) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
        this.userRoleUtil = userRoleUtil;
        this.gson = gson;
    }

    @Override
    public boolean userExists(String username, String email) {
        return userRepository.existsByUsernameOrEmail(username, email);
    }

    @Override
    public void registerNewUser(UserRegisterBindingModel userRegisterBindingModel) {
        UserServiceModel middleMan = modelMapper.map(userRegisterBindingModel, UserServiceModel.class);
        // Re-sets the password (so i dont have to type every field manually
        String password = userRegisterBindingModel.getPassword();
        String encode = passwordEncoder.encode(password);
        middleMan.setPassword(encode);

        UserEntity finalNewUser = modelMapper.map(middleMan, UserEntity.class);
        finalNewUser.setRoles(userRoleUtil.setUserRole());

        userRepository.save(finalNewUser);
    }

    @Override
    public UserEntity findById(int i) {
        return userRepository.findById(2L).orElse(null);
    }

    @Override
    public UserEntity findUserByUsername(String name) {
        return userRepository.findByUsername(name).orElse(null);
    }

    @Override
    public List<String> findUsersForAdmin(String username) {
        return userRepository.findUsersForAdmin(username);
    }

    @Override
    public List<ListedAccountsViewModel> getAll() {
        return mapUserEntityListToListedAccountViewList(userRepository.findAll());
    }

    @Override
    public List<ListedAccountsViewModel> findUsersMatchingTheUsername(String username) {
        return mapUserEntityListToListedAccountViewList(userRepository.findUsersMatchingUsername(username));
    }

    @Override
    public boolean delete(Long id, String adminName) {
        UserEntity userToDelete = userRepository.findById(id).orElseThrow();
        boolean selfDelete = userToDelete.getUsername().equals(adminName);
        if (selfDelete) return true;

        userToDelete.setRoles(null);
        userRepository.save(userToDelete);
        userRepository.delete(userToDelete);
        return false;
    }

    @Override
    public UpdateUserDetailsBindingModel findAccountDetailsByUsername(String name) {
        UserEntity user = this.findUserByUsername(name);
        return modelMapper.map(user, UpdateUserDetailsBindingModel.class);
    }

    @Override
    public void updateUserDetails(UpdateUserDetailsBindingModel updateUserDetailsBindingModel) {
        // Useless service model tbh
        UserServiceModel u = modelMapper.map(updateUserDetailsBindingModel, UserServiceModel.class);
        userRepository.updateUserDetails(u.getFirstName(), u.getLastName(), u.getAddress(), u.getLicenseNumber(), u.getEmail(), u.getId());
    }

    @Override
    public void updatePassword(UpdateUserPasswordBindingModel updateUserPasswordBindingModel) {
        String encryptedPassword = passwordEncoder.encode(updateUserPasswordBindingModel.getPassword());
        userRepository.updateUserPassword(encryptedPassword, updateUserPasswordBindingModel.getId());
    }

    @Override
    public boolean setNewAccountLevel(String json, String currentAdminName) {

        UpdateUserRoleBindingModel uurbm = gson.fromJson(json, UpdateUserRoleBindingModel.class);
        UserEntity currentAdminUser = userRepository.findByUsername(currentAdminName).orElse(null);

        if (Objects.requireNonNull(currentAdminUser).getId().equals(Long.parseLong(uurbm.getId()))) return true;

        UserEntity userToChange = userRepository.findById(Long.parseLong(uurbm.getId())).orElseThrow();

        userToChange.setRoles(userRoleUtil.setRoleFromString(uurbm.getRoles()));
        userRepository.save(userToChange);
        return false;
    }

    private List<ListedAccountsViewModel> mapUserEntityListToListedAccountViewList(List<UserEntity> list) {
        return list.stream().map(e -> modelMapper.map(e, ListedAccountsViewModel.class)).collect(Collectors.toList());
    }

    @Override
    public void initialize() {

        if (userRepository.count() == 0) {
            for (int i = 0; i < 4; i++) {
                UserEntity user = new UserEntity();
                switch (i) {
                    case 0: user.setUsername("Admin");
                        user.setRoles(userRoleUtil.setAdminRole());
                        break;
                    case 1: user.setUsername("Normal");
                        user.setRoles(userRoleUtil.setUserRole());
                        break;
                    case 2: user.setUsername("Vippp");
                        user.setRoles(userRoleUtil.setVipRole());
                        break;
                    case 3: user.setUsername("Suspended");
                        user.setRoles(userRoleUtil.setSuspendedRole());
                        break;
                    default: break;
                }
                user.setEmail("account_email(" + i + ")@test.test")
                        .setPassword(passwordEncoder.encode("12345"));
                userRepository.save(user);
            }
        }
    }


}
