package com.example.feathers.database.service.impl;

import com.example.feathers.database.model.binding.ReviewBindingModel;
import com.example.feathers.database.model.entity.ReviewEntity;
import com.example.feathers.database.model.entity.UserEntity;
import com.example.feathers.database.model.service.ReviewServiceModel;
import com.example.feathers.database.repository.ReviewRepository;
import com.example.feathers.database.service.ReviewService;
import com.example.feathers.database.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.Optional;

@Service
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final ModelMapper modelMapper;
    private final UserService userService;

    public ReviewServiceImpl(ReviewRepository reviewRepository, ModelMapper modelMapper, UserService userService) {
        this.reviewRepository = reviewRepository;
        this.modelMapper = modelMapper;
        this.userService = userService;
    }

    @Override
    public void save(ReviewBindingModel reviewBindingModel, Principal principal) {
        ReviewServiceModel serviceModel = modelMapper.map(reviewBindingModel, ReviewServiceModel.class);
        UserEntity creator = userService.findUserByUsername(principal.getName());
        ReviewEntity exists = reviewRepository.findByCreator(creator).orElse(null);
        if (exists != null) {
            exists.setContent(serviceModel.getContent());
            exists.setRating(serviceModel.getRating());
            reviewRepository.save(exists);
        } else {
            serviceModel.setCreator(creator);
            reviewRepository.save(modelMapper.map(serviceModel, ReviewEntity.class));
        }
    }
}
