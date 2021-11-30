package com.example.feathers.database.service.impl;

import com.example.feathers.database.model.binding.UpdateUserDetailsBindingModel;
import com.example.feathers.database.model.binding.UpdateUserRoleBindingModel;
import com.example.feathers.database.model.binding.UserRegisterBindingModel;
import com.example.feathers.database.model.entity.UserEntity;
import com.example.feathers.database.model.view.ListedAccountsViewModel;
import com.example.feathers.database.model.service.UserServiceModel;
import com.example.feathers.database.repository.UserRepository;
import com.example.feathers.database.service.UserService;
import com.example.feathers.util.UserRoleUtil;
import com.example.feathers.web.exception.impl.UserNotFoundException;
import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
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
        UserServiceModel serviceModel = modelMapper.map(userRegisterBindingModel, UserServiceModel.class);
        serviceModel.setPassword(passwordEncoder.encode(userRegisterBindingModel.getPassword()));

        UserEntity finalNewUser = modelMapper.map(serviceModel, UserEntity.class);
        finalNewUser.setRoles(userRoleUtil.setUserRole());

        userRepository.save(finalNewUser);
    }

    @Override
    public UserEntity findUserByUsername(String name) {
        return userRepository.findByUsername(name).orElseThrow(UserNotFoundException::new);
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

    private List<ListedAccountsViewModel> mapUserEntityListToListedAccountViewList(List<UserEntity> list) {
        return list.stream().map(e -> modelMapper.map(e, ListedAccountsViewModel.class)).collect(Collectors.toList());
    }

    @Transactional
    @Override
    public boolean delete(Long id, String adminName) {
        UserEntity userToDelete = userRepository.findById(id).orElseThrow(UserNotFoundException::new);
        if (userToDelete.getUsername().equals(adminName)) return true;

        userRepository.delete(userToDelete);
        return false;
    }

    @Override
    public UpdateUserDetailsBindingModel findAccountDetailsByUsername(String name) {
        return modelMapper.map(this.findUserByUsername(name), UpdateUserDetailsBindingModel.class);
    }

    @Override
    public <T> void updateUserDetails(T bindingModel, String accountName) {
        UserServiceModel currentServiceModel = modelMapper.map(bindingModel, UserServiceModel.class);
        if (currentServiceModel.getPassword() != null) {
            currentServiceModel.setPassword(passwordEncoder.encode(currentServiceModel.getPassword()));
        }
        UserEntity currentUser = userRepository.findByUsername(accountName).orElseThrow(UserNotFoundException::new);
        modelMapper.map(currentServiceModel, currentUser);
        userRepository.save(currentUser);
    }

    @Override
    public boolean setNewAccountLevel(String json, String currentAdminName) {

        UpdateUserRoleBindingModel uurbm = gson.fromJson(json, UpdateUserRoleBindingModel.class);
        UserEntity currentAdminUser = userRepository.findByUsername(currentAdminName).orElse(null);

        if (Objects.requireNonNull(currentAdminUser).getId().equals(Long.parseLong(uurbm.getId()))) return true;

        UserEntity userToChange = userRepository.findById(Long.parseLong(uurbm.getId())).orElseThrow(UserNotFoundException::new);

        userToChange.setRoles(userRoleUtil.setRoleFromString(uurbm.getRoles()));
        userRepository.save(userToChange);
        return false;
    }

    @Override
    public void initialize() {
        if (userRepository.count() == 0) {
            UserEntity user = new UserEntity();
            user.setUsername("Admin")
                    .setRoles(userRoleUtil.setAdminRole())
                    .setEmail("admin@test.test")
                    .setPassword(passwordEncoder.encode("12345"));
            userRepository.save(user);
        }
    }

    @Override
    public void startDebugMode() {
        for (int i = 0; i < 3; i++) {
            UserEntity user = new UserEntity();
            switch (i) {
                case 0: user.setUsername("Normal");
                    user.setRoles(userRoleUtil.setUserRole());
                    break;
                case 1: user.setUsername("Vippp");
                    user.setRoles(userRoleUtil.setVipRole());
                    break;
                case 2: user.setUsername("Suspended");
                    user.setRoles(userRoleUtil.setSuspendedRole());
                    break;
                default: break;
            }
            user.setEmail(user.getUsername() + "@test.test")
                    .setPassword(passwordEncoder.encode("12345"));
            userRepository.save(user);
        }

    }


}
