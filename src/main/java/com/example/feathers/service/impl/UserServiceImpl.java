package com.example.feathers.service.impl;

import com.example.feathers.model.binding.UserRegisterBindingModel;
import com.example.feathers.model.entity.UserEntity;
import com.example.feathers.model.entity.UserRoleEntity;
import com.example.feathers.model.entity.enums.UserRolesEnum;
import com.example.feathers.model.service.UserServiceModel;
import com.example.feathers.model.view.ListedAccountsViewModel;
import com.example.feathers.repository.UserRepository;
import com.example.feathers.repository.UserRoleRepository;
import com.example.feathers.service.UserService;
import com.example.feathers.util.UserRoleSetter;
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
    private final UserRoleSetter userRoleSetter;

    public UserServiceImpl(UserRepository userRepository, UserRoleRepository userRoleRepository, ModelMapper modelMapper, PasswordEncoder passwordEncoder, UserRoleSetter userRoleSetter) {
        this.userRepository = userRepository;
        this.userRoleRepository = userRoleRepository;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
        this.userRoleSetter = userRoleSetter;
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
        finalNewUser.setRoles(userRoleSetter.setUserRole());

        userRepository.save(finalNewUser);
    }

    @Override
    public void initialize() {
        HashMap<String, UserRoleEntity> roles = new HashMap<>();
        // TODO Better startup
        if (userRoleRepository.count() == 0) {
            roles.put("Admin", new UserRoleEntity().setRole(UserRolesEnum.ADMIN));
            roles.put("VIP", new UserRoleEntity().setRole(UserRolesEnum.VIP));
            roles.put("User", new UserRoleEntity().setRole(UserRolesEnum.USER));
            roles.put("Suspended", new UserRoleEntity().setRole(UserRolesEnum.SUSPENDED));

            roles.values().forEach(userRoleRepository::save);
        }

        if (userRepository.count() == 0 || !roles.isEmpty()) {
            List<UserEntity> users = new ArrayList<>();
            users.add(new UserEntity()
                    .setUsername("Admin") // Todo maybe make it case insensitive
                    .setPassword(passwordEncoder.encode("12345"))
                    .setEmail("admin@test.test")
                    .setRoles(userRoleSetter.setAdminRole()));
            users.add(new UserEntity()
                    .setUsername("Normal")
                    .setPassword(passwordEncoder.encode("12345"))
                    .setEmail("normal@test.test")
                    .setRoles(userRoleSetter.setUserRole()));
            users.add(new UserEntity()
                    .setUsername("Vippp")
                    .setPassword(passwordEncoder.encode("12345"))
                    .setEmail("vip@test.test")
                    .setRoles(userRoleSetter.setVIPRole()));
            users.add(new UserEntity() // Suspended VIP
                    .setUsername("Suspended")
                    .setPassword(passwordEncoder.encode("12345"))
                    .setEmail("suspended@test.test")
                    .setRoles(userRoleSetter.setSuspendedRole()));

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
    public List<ListedAccountsViewModel> getAll() {
        List<UserEntity> all = userRepository.findAll();
        return all.stream().map(e -> modelMapper.map(e, ListedAccountsViewModel.class)).collect(Collectors.toList());
    }

    @Override
    public List<String> findUserForAdmin(String username) {
        return userRepository.findUserForAdmin(username);
    }

    @Override
    public List<ListedAccountsViewModel> findUsersMatchingTheUsername(String username) {
        List<UserEntity> usersMatchingUsername = userRepository.findUsersMathingUsername(username);
        return usersMatchingUsername.stream().map(e -> modelMapper.map(e, ListedAccountsViewModel.class)).collect(Collectors.toList());
    }
}
