package com.udacity.jwdnd.course1.cloudstorage.application.controller;

import com.udacity.jwdnd.course1.cloudstorage.application.form_attribute.SignUpAttribute;
import com.udacity.jwdnd.course1.cloudstorage.domain.service.user.UserException;
import com.udacity.jwdnd.course1.cloudstorage.domain.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;

    @GetMapping("/login")
    public String login(
            Model model,
            @RequestParam(required = false) String error,
            @RequestParam(required = false) String logout
    ) {
        model.addAttribute("error", error);
        model.addAttribute("logout", logout);

        return "login";
    }

    @GetMapping("/signup")
    public String viewSignup(@ModelAttribute SignUpAttribute signUpAttribute) {
        return "signup";
    }

    @PostMapping("/signup")
    public String signUserUp(@ModelAttribute SignUpAttribute signUpAttribute, Model model) {
        model.addAttribute("success", true);

        try {
            userService.createUser(
                    signUpAttribute.getFirstName(),
                    signUpAttribute.getLastName(),
                    signUpAttribute.getUsername(),
                    signUpAttribute.getPassword()
            );
        } catch (UserException e) {
            model.addAttribute("errorMessage", e.getMessage());
            model.addAttribute("success", false);
        }

        return "signup";
    }
}
