package com.example.feathers.web;

import com.example.feathers.database.model.view.ListedLogViewModel;
import com.example.feathers.database.service.LogService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/profile")
public class ProfileController {

    private final LogService logService;

    public ProfileController(LogService logService) {
        this.logService = logService;
    }

    @GetMapping("/")
    public String profile() {
        return "redirect:logbook";
    }

    @GetMapping("/logbook")
    public String profileLogbook(Model model, Principal principal) {

        List<ListedLogViewModel> allUserLogs = logService.getAllLogs(principal.getName());
        model.addAttribute("allUserLogs", allUserLogs);

        return "logbook";
    }

    @GetMapping("/settings")
    public String profileSettings() {
        return "account";
    }


}
