package com.example.feathers.web;

import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/user")
public class UserLoginController {

    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("invalid",
                model.getAttribute("invalid") == null
                        ? false
                        : model.getAttribute("invalid"));

        return "login";
    }

    @PostMapping("/error")
    public String failedLogin(@ModelAttribute(UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_USERNAME_KEY) String username,
                              RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("invalid", true)
                .addFlashAttribute("username", username);

        return "redirect:/user/login";
    }

}
