package com.example.feathers.service.impl;

import com.example.feathers.model.binding.UserRegisterBindingModel;
import com.example.feathers.model.entity.UserEntity;
import com.example.feathers.model.entity.UserRoleEntity;
import com.example.feathers.model.entity.enums.UserRolesEnum;
import com.example.feathers.model.service.UserServiceModel;
import com.example.feathers.model.view.ListedAccountsViewModel;
import com.example.feathers.repository.UserRepository;
import com.example.feathers.repository.UserRoleRepository;
import com.example.feathers.service.AircraftService;
import com.example.feathers.service.LogService;
import com.example.feathers.service.UserService;
import com.example.feathers.util.UserRoleUtil;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final UserRoleUtil userRoleUtil;

    public UserServiceImpl(UserRepository userRepository,
                           UserRoleRepository userRoleRepository,
                           ModelMapper modelMapper,
                           PasswordEncoder passwordEncoder,
                           UserRoleUtil userRoleUtil) {
        this.userRepository = userRepository;
        this.userRoleRepository = userRoleRepository;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
        this.userRoleUtil = userRoleUtil;
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
    public void initialize() {

        if (userRepository.count() == 0) {
            List<UserEntity> users = new ArrayList<>();
            users.add(new UserEntity()
                    .setUsername("Admin")
                    .setPassword(passwordEncoder.encode("12345"))
                    .setEmail("admin@test.test")
                    .setRoles(userRoleUtil.setAdminRole()));
            users.add(new UserEntity()
                    .setUsername("Normal")
                    .setPassword(passwordEncoder.encode("12345"))
                    .setEmail("normal@test.test")
                    .setRoles(userRoleUtil.setUserRole()));
            users.add(new UserEntity()
                    .setUsername("Vippp")
                    .setPassword(passwordEncoder.encode("12345"))
                    .setEmail("vip@test.test")
                    .setRoles(userRoleUtil.setVipRole()));
            users.add(new UserEntity() // Suspended VIP
                    .setUsername("Suspended")
                    .setPassword(passwordEncoder.encode("12345"))
                    .setEmail("suspended@test.test")
                    .setRoles(userRoleUtil.setSuspendedRole()));

            users.forEach(userRepository::save);
        }


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
    public List<String> findUserForAdmin(String username) {
        return userRepository.findUserForAdmin(username);
    }

    @Override
    public List<ListedAccountsViewModel> getAll() {
        return mapUserEntityListToListedAccountViewList(userRepository.findAll());
    }

    @Override
    public List<ListedAccountsViewModel> findUsersMatchingTheUsername(String username) {
        return mapUserEntityListToListedAccountViewList(userRepository.findUsersMathingUsername(username));
    }

    @Override
    public void delete(Long id) {
        userRepository.deleteById(id);
    }

    private List<ListedAccountsViewModel> mapUserEntityListToListedAccountViewList(List<UserEntity> list) {
        return list.stream().map(e -> {
            ListedAccountsViewModel temp = modelMapper.map(e, ListedAccountsViewModel.class);

            temp.setAccountLevel(userRoleUtil.findHighestRole(e.getRoles()));

            return temp;
        }).collect(Collectors.toList());
    }




}
