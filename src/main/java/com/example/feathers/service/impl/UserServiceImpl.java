package com.example.feathers.service.impl;

import com.example.feathers.model.binding.UserRegisterBindingModel;
import com.example.feathers.model.entity.UserEntity;
import com.example.feathers.model.entity.UserRoleEntity;
import com.example.feathers.model.entity.enums.UserRolesEnum;
import com.example.feathers.model.service.UserServiceModel;
import com.example.feathers.repository.UserRepository;
import com.example.feathers.repository.UserRoleRepository;
import com.example.feathers.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, UserRoleRepository userRoleRepository, ModelMapper modelMapper, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userRoleRepository = userRoleRepository;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
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
        finalNewUser.setRoles(Set.of(userRoleRepository.findByRole(UserRolesEnum.USER)));

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
                    .setPassword(passwordEncoder.encode("admin"))
                    .setEmail("admin@test.test")
                    .setRoles(Set.of(roles.get("User"), roles.get("Admin"))));
            users.add(new UserEntity()
                    .setUsername("Normal")
                    .setPassword(passwordEncoder.encode("normal"))
                    .setEmail("normal@test.test")
                    .setRoles(Set.of(roles.get("User"))));
            users.add(new UserEntity()
                    .setUsername("Vip")
                    .setPassword(passwordEncoder.encode("vip"))
                    .setEmail("vip@test.test")
                    .setRoles(Set.of(roles.get("User"), roles.get("VIP"))));
            users.add(new UserEntity() // Suspended VIP
                    .setUsername("Suspended")
                    .setPassword(passwordEncoder.encode("suspended"))
                    .setEmail("suspended@test.test")
                    .setRoles(Set.of(roles.get("User"), roles.get("VIP"), roles.get("Suspended"))));

            users.forEach(userRepository::save);
        }


    }
}
