package com.example.feathers.web;

import com.example.feathers.model.binding.SimpleBindingModel;
import com.example.feathers.model.view.ListedAccountsViewModel;
import com.example.feathers.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/profile")
public class AdminController {

    private final UserService userService;

    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @ModelAttribute
    public SimpleBindingModel simpleBindingModel() {
        return new SimpleBindingModel();
    }

    @GetMapping("/admin")
    public String profileAdmin(Model model, @RequestParam(value = "username", required = false) String username) {
        List<ListedAccountsViewModel> accounts;

        if (username == null) {
            accounts = userService.getAll();
        } else {
            accounts = userService.findUsersMatchingTheUsername(username);
        }

        model.addAttribute("accounts", accounts);
        return "admin";
    }

    // TODO Change of User Role

}
