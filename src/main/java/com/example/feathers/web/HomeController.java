package com.example.feathers.web;

import com.example.feathers.application.initialize.InitialSetup;
import com.example.feathers.database.model.view.ReviewViewModel;
import com.example.feathers.database.service.ReviewService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.IOException;
import java.util.List;

@Controller
public class HomeController {

    private final ReviewService reviewService;
    private final InitialSetup initialSetup;

    public HomeController(ReviewService reviewService, InitialSetup initialSetup) {
        this.reviewService = reviewService;
        this.initialSetup = initialSetup;
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

    @GetMapping("/debug")
    @ResponseStatus(value = HttpStatus.OK)
    public void startDebug() throws IOException {
        initialSetup.loadEasyDebug();
    }



}
