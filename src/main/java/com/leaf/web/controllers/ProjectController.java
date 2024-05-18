package com.leaf.web.controllers;

import com.leaf.domain.dtos.project.ProjectCreateRequestDTO;
import com.leaf.domain.services.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/projects")
public class ProjectController {

    private final ProjectService projectService;

    @PostMapping
    public ResponseEntity<?> createProject(@RequestBody @Validated ProjectCreateRequestDTO requestDTO, Principal principal) {
        return ResponseEntity.ok(projectService.save(requestDTO, principal));
    }
}
