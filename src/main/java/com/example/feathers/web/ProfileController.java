package com.example.feathers.web;

import com.example.feathers.model.view.ListedLogViewModel;
import com.example.feathers.service.LogService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/profile")
public class ProfileController {

    private final LogService logService;

    public ProfileController(LogService logService) {
        this.logService = logService;
    }

    @GetMapping("/logbook")
    public String profileLogbook(Model model) {

        // TODO Make this find logs for user only
        List<ListedLogViewModel> allUserLogs = logService.getAllLogs();
        model.addAttribute("allUserLogs", allUserLogs);

        return "logbook";
    }

    @GetMapping("/dashboard")
    public String profileDashboard() {
        return "dashboard";
    }

    @GetMapping("/settings")
    public String profileSettings() {
        return "account";
    }

    @GetMapping("/admin")
    public String profileAdmin() {
        return "about";
    }


}
