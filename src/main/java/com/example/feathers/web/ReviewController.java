package com.example.feathers.web;

import com.example.feathers.application.listener.event.ReviewEvent;
import com.example.feathers.database.model.binding.ReviewBindingModel;
import com.example.feathers.database.service.ReviewService;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.security.Principal;

@Controller
@RequestMapping("/profile")
public class ReviewController {

    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @ModelAttribute
    public ReviewBindingModel reviewBindingModel(){
        return new ReviewBindingModel();
    }

    @GetMapping("/review")
    public String review() {
        return "review";
    }

    @PostMapping("/review")
    public String updateReview(@Valid ReviewBindingModel reviewBindingModel,
                                 BindingResult bindingResult,
                                 RedirectAttributes redirectAttributes,
                                 Principal principal) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("reviewBindingModel", reviewBindingModel)
                    .addFlashAttribute("org.springframework.validation.BindingResult.reviewBindingModel", bindingResult);

            return "redirect:review";
        }

        reviewService.save(reviewBindingModel, principal.getName());

        return "redirect:/profile/logbook";
    }
}
