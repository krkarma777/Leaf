package com.leaf.web.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RequestMapping("/projects")
@Controller
public class ProjectConroller {

    @GetMapping
    public String projects() {
        return "project/project-home";
    }

    @GetMapping("/create")
    public String createProjectForm(Model model) {
        String currentDateTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm"));
        model.addAttribute("currentDateTime", currentDateTime);
        return "project/create-project";
    }

    @GetMapping("/edit/{id}")
    public String editProjectForm(Model model, @PathVariable("id") Long id) {
        String currentDateTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm"));
        model.addAttribute("currentDateTime", currentDateTime);
        model.addAttribute("id", id);
        return "project/edit-project";
    }
}
