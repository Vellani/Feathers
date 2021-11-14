package com.example.feathers.web;

import com.example.feathers.model.binding.UserRegisterBindingModel;
import com.example.feathers.service.UserService;
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

    public UserRegisterController(UserService userService) {
        this.userService = userService;
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
        return "redirect:/";
    }

}
