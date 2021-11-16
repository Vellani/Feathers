package com.example.feathers.web;

import com.example.feathers.model.view.ListedAccountsViewModel;
import com.example.feathers.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/profile")
public class AdminController {

    private final UserService userService;

    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/admin")
    public String profileAdmin(Model model) {

        List<ListedAccountsViewModel> accounts = userService.getAll();
        model.addAttribute("accounts", accounts);

        return "admin";
    }

    // TODO Change of User Role

}
