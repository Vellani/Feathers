package com.example.feathers.database.service.impl;

import com.example.feathers.application.listener.event.ReviewEvent;
import com.example.feathers.database.model.binding.ReviewBindingModel;
import com.example.feathers.database.model.entity.ReviewEntity;
import com.example.feathers.database.model.entity.UserEntity;
import com.example.feathers.database.model.seed.ReviewSeed;
import com.example.feathers.database.model.service.ReviewServiceModel;
import com.example.feathers.database.model.view.ReviewViewModel;
import com.example.feathers.database.repository.ReviewRepository;
import com.example.feathers.database.service.ReviewService;
import com.example.feathers.database.service.UserService;
import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.example.feathers.global.Constants.REVIEWS_PATH;

@Service
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final ModelMapper modelMapper;
    private final UserService userService;
    private final Gson gson;
    private final ApplicationEventPublisher applicationEventPublisher;

    public ReviewServiceImpl(ReviewRepository reviewRepository,
                             ModelMapper modelMapper,
                             UserService userService, Gson gson, ApplicationEventPublisher applicationEventPublisher) {
        this.reviewRepository = reviewRepository;
        this.modelMapper = modelMapper;
        this.userService = userService;
        this.gson = gson;
        this.applicationEventPublisher = applicationEventPublisher;
    }

    @Override
    public void save(ReviewBindingModel reviewBindingModel, String currentAccountName) {
        UserEntity creator = userService.findUserByUsername(currentAccountName);
        ReviewEntity review = reviewRepository.findByCreator(creator).orElse(new ReviewEntity().setCreator(creator));

        modelMapper.map(modelMapper.map(reviewBindingModel, ReviewServiceModel.class), review);
        reviewRepository.save(review);
        publishCacheInvalidation();
    }

    @Cacheable("reviews")
    @Override
    public List<ReviewViewModel> findReviews() {
        publishCacheInvalidation();
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

    @Override
    public void startDebugMode() throws IOException {
        Set<ReviewSeed> collect = Arrays.stream(
                gson.fromJson(
                        Files.readString(Path.of(REVIEWS_PATH)),
                        ReviewSeed[].class))
                .collect(Collectors.toSet());

        collect.stream()
                .map(e -> {
                    ReviewEntity entity = modelMapper.map(e, ReviewEntity.class);
                    entity.setCreator(userService.findUserByUsername(e.getCreator()));
                    return entity;
                })
                .forEach(reviewRepository::save);

        publishCacheInvalidation();
    }

    private void publishCacheInvalidation() {
        ApplicationEvent event = new ReviewEvent(this);
        applicationEventPublisher.publishEvent(event);
    }

}
