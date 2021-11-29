package com.example.feathers.database.service.impl;

import com.example.feathers.database.model.binding.ReviewBindingModel;
import com.example.feathers.database.model.entity.ReviewEntity;
import com.example.feathers.database.model.entity.UserEntity;
import com.example.feathers.database.model.service.ReviewServiceModel;
import com.example.feathers.database.model.view.ReviewViewModel;
import com.example.feathers.database.repository.ReviewRepository;
import com.example.feathers.database.service.ReviewService;
import com.example.feathers.database.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final ModelMapper modelMapper;
    private final UserService userService;

    public ReviewServiceImpl(ReviewRepository reviewRepository,
                             ModelMapper modelMapper,
                             UserService userService) {
        this.reviewRepository = reviewRepository;
        this.modelMapper = modelMapper;
        this.userService = userService;
    }

    @Override
    public void save(ReviewBindingModel reviewBindingModel, String currentAccountName) {
        UserEntity creator = userService.findUserByUsername(currentAccountName);
        ReviewEntity review = reviewRepository.findByCreator(creator).orElse(new ReviewEntity().setCreator(creator));

        modelMapper.map(modelMapper.map(reviewBindingModel, ReviewServiceModel.class), review);
        reviewRepository.save(review);
    }

    @Cacheable("reviews")
    @Override
    public List<ReviewViewModel> findReviews() {
        return reviewRepository.findReviewsToDisplay().stream().limit(3).map(e ->
                new ReviewViewModel()
                        .setContent(e.getContent())
                        .setFirstName(e.getCreator().getFirstName())
                        .setRating(e.getRating()))
                .collect(Collectors.toList());
    }

    @Override
    public void cleanUp() {
        reviewRepository.cleanUpDatabase();
    }


}
