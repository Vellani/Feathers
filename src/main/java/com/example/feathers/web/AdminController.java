package com.example.feathers.web;

import com.example.feathers.database.model.view.ListedAccountsViewModel;
import com.example.feathers.database.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/profile")
public class AdminController {

    private final UserService userService;

    public AdminController(UserService userService) {
        this.userService = userService;
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
        // TODO show "cannot delete self"
        model.addAttribute("selfDelete", model.containsAttribute("selfDelete"));

        return "admin";
    }

    @PostMapping("/admin/save")
    @ResponseBody
    public ResponseEntity<String> updateUserLevel(@RequestBody String json,
                                                  Principal principal) {

        boolean selfEdit = userService.setNewAccountLevel(json, principal.getName());
        if (selfEdit) return ResponseEntity.unprocessableEntity().build();

        return ResponseEntity.accepted().build();
    }

    @PostMapping("/admin/delete")
    public String deleteUser(@RequestParam(value = "id") Long id,
                             RedirectAttributes redirectAttributes,
                             Principal principal) {

        boolean selfDelete = userService.delete(id, principal.getName());

        if (selfDelete) {
            redirectAttributes.addFlashAttribute("selfDelete", true);
            return "redirect:/profile/admin";
        }

        return "redirect:/profile/admin";
    }

}
