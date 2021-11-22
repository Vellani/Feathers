package com.example.feathers.web;

import com.example.feathers.application.listener.event.UserCreatedEvent;
import com.example.feathers.database.model.binding.UserRegisterBindingModel;
import com.example.feathers.database.service.UserService;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/user")
public class UserRegisterController {

    private final UserService userService;
    private final ApplicationEventPublisher applicationEventPublisher;


    public UserRegisterController(UserService userService, ApplicationEventPublisher applicationEventPublisher) {
        this.userService = userService;
        this.applicationEventPublisher = applicationEventPublisher;
    }

    @ModelAttribute
    public UserRegisterBindingModel userRegisterBindingModel() {
        return new UserRegisterBindingModel();
    }

    @GetMapping("/register")
    public String registerHTML(Model model) {

        model.addAttribute("exists",
                model.getAttribute("exists") == null ? false : model.getAttribute("exists"))
                .addAttribute("passwordsMatch",
                        model.getAttribute("passwordsMatch") == null ? true : model.getAttribute("passwordsMatch"));

        return "register";
    }

    @PostMapping("/register")
    public String registerNewUser(@Valid UserRegisterBindingModel userRegisterBindingModel,
                                  BindingResult bindingResult,
                                  RedirectAttributes redirectAttributes) {

        // Could be done frontend to reduce server time but vulnerable to attacks, however, it is user-centered so no real damage done
        boolean passwordsMatch = userRegisterBindingModel.getPassword().equals(userRegisterBindingModel.getConfirmPassword());
        // Should be done after the checking the rest of the predicates to reduce unnecessary database queries
        boolean userAlreadyExists = userService.userExists(userRegisterBindingModel.getUsername(), userRegisterBindingModel.getEmail());

        if (bindingResult.hasErrors() || !passwordsMatch || userAlreadyExists) {
            redirectAttributes
                    .addFlashAttribute("userRegisterBindingModel", userRegisterBindingModel)
                    .addFlashAttribute("org.springframework.validation.BindingResult.userRegisterBindingModel", bindingResult)
                    .addFlashAttribute("passwordsMatch", passwordsMatch)
                    .addFlashAttribute("exists", userAlreadyExists);

            return "redirect:register";
        }

        userService.registerNewUser(userRegisterBindingModel);

        ApplicationEvent event = new UserCreatedEvent(this, userRegisterBindingModel.getUsername());
        applicationEventPublisher.publishEvent(event);

        return "redirect:/";
    }

}
