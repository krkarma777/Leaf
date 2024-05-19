package com.leaf.web.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Controller
public class HomeController {

    @GetMapping
    public String home() {
        return "home";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/register")
    public String register() {
        return "register";
    }

    @GetMapping("/project/create")
    public String createProjectForm(Model model) {
        String currentDateTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm"));
        model.addAttribute("currentDateTime", currentDateTime);
        return "project/create-project";
    }
}
