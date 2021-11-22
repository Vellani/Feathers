package com.example.feathers.database.service;

import com.example.feathers.database.model.binding.ReviewBindingModel;
import com.example.feathers.database.model.view.ReviewViewModel;

import java.security.Principal;
import java.util.List;

public interface ReviewService {
    void save(ReviewBindingModel reviewBindingModel, Principal principal);

    List<ReviewViewModel> findReviews();
}
