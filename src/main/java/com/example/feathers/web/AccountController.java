package com.example.feathers.web;

import com.example.feathers.database.model.binding.ReviewBindingModel;
import com.example.feathers.database.model.binding.UpdateUserDetailsBindingModel;
import com.example.feathers.database.model.binding.UpdateUserPasswordBindingModel;
import com.example.feathers.database.model.view.ListedLogViewModel;
import com.example.feathers.database.service.LogService;
import com.example.feathers.database.service.ReviewService;
import com.example.feathers.database.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/profile")
public class AccountController {

    private final LogService logService;
    private final UserService userService;

    public AccountController(LogService logService, UserService userService) {
        this.logService = logService;
        this.userService = userService;
    }

    @ModelAttribute
    public List<ListedLogViewModel> listedLogViewModelList(Principal principal) {
        return logService.getAllLogs(principal.getName());
    }

    @ModelAttribute
    public UpdateUserPasswordBindingModel updateUserPasswordBindingModel() {
        return new UpdateUserPasswordBindingModel();
    }

    @ModelAttribute
    public UpdateUserDetailsBindingModel updateUserDetailsBindingModel(Principal principal) {
        return userService.findAccountDetailsByUsername(principal.getName());
    }

    @GetMapping("/")
    public String profile() {
        return "redirect:logbook";
    }

    @GetMapping("/logbook")
    public String profileLogbook() {
        return "logbook";
    }

    @GetMapping("/details")
    public String profileSettings() {
        return "account";
    }

    @PostMapping("/details")
    public String updateSettings(@Valid UpdateUserDetailsBindingModel updateUserDetailsBindingModel,
                                 BindingResult bindingResult,
                                 RedirectAttributes redirectAttributes,
                                 Principal principal) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("updateUserDetailsBindingModel", updateUserDetailsBindingModel)
                    .addFlashAttribute("org.springframework.validation.BindingResult.updateUserDetailsBindingModel", bindingResult);

            return "redirect:/profile/details";
        }

        userService.updateUserDetails(updateUserDetailsBindingModel, principal.getName());
        return "account";
    }

    @GetMapping("/details/password")
    public String changePassword(Model model) {
        model.addAttribute("nomatch",
                model.getAttribute("nomatch") != null
                        ? model.getAttribute("nomatch")
                        : false);
        return "change-password";
    }

    @PostMapping("/details/password")
    public String updatePassword(@Valid UpdateUserPasswordBindingModel updateUserPasswordBindingModel,
                                 BindingResult bindingResult,
                                 RedirectAttributes redirectAttributes,
                                 Principal principal) {

        if (!updateUserPasswordBindingModel.getPassword().equals(updateUserPasswordBindingModel.getConfirmPassword())) {
            bindingResult.reject("Passwords-do-not-match");
            redirectAttributes.addFlashAttribute("nomatch", true);
        }

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("updateUserPasswordBindingModel", updateUserPasswordBindingModel)
                    .addFlashAttribute("org.springframework.validation.BindingResult.updateUserPasswordBindingModel", bindingResult);
            return "redirect:password";
        }

        userService.updateUserDetails(updateUserPasswordBindingModel, principal.getName());
        return "account";
    }

}
