package com.example.feathers.web;

import com.example.feathers.database.model.view.ReviewViewModel;
import com.example.feathers.database.service.ReviewService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class HomeController {

    private final ReviewService reviewService;

    public HomeController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @GetMapping("/")
    public String home(Model model) {
        List<ReviewViewModel> reviews = reviewService.findReviews();
        model.addAttribute("reviews", reviews);
        return "index";
    }


    @GetMapping("/gpx")
    public String gpx() {
        return "gpxVisuals";
    }



}
