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

@Controller
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/signup")
    public String viewSignup(@ModelAttribute SignUpAttribute signUpAttribute) {
        return "signup";
    }

    @PostMapping("/signup")
    public String signUserUp(@ModelAttribute SignUpAttribute signUpAttribute, Model model) {
        try {
            userService.createUser(
                    signUpAttribute.getFirstName(),
                    signUpAttribute.getLastName(),
                    signUpAttribute.getUsername(),
                    signUpAttribute.getPassword()
            );
        } catch (UserException e) {
            model.addAttribute("errorMessage", e.getMessage());
        }
        model.addAttribute("success", true);

        return "signup";
    }
}
