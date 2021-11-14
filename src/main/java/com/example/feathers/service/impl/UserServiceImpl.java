package com.example.feathers.service.impl;

import com.example.feathers.model.binding.UserRegisterBindingModel;
import com.example.feathers.model.entity.UserEntity;
import com.example.feathers.model.entity.enums.UserRolesEnum;
import com.example.feathers.model.service.UserServiceModel;
import com.example.feathers.repository.UserRepository;
import com.example.feathers.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public boolean userExists(String username, String email) {
        return userRepository.existsByUsernameOrEmail(username, email);
    }

    @Override
    public void registerNewUser(UserRegisterBindingModel userRegisterBindingModel) {

        UserServiceModel middleMan = modelMapper.map(userRegisterBindingModel, UserServiceModel.class);
        middleMan.setAccountLevel(UserRolesEnum.USER);

        UserEntity finalNewUser = modelMapper.map(middleMan, UserEntity.class);
        userRepository.save(finalNewUser);
    }
}
