package com.leaf.web.controllers;

import com.leaf.domain.dtos.project.ProjectCreateRequestDTO;
import com.leaf.domain.entities.Project;
import com.leaf.domain.services.ProjectService;
import com.leaf.web.utils.ValidationErrorHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/projects")
public class ProjectController {

    private final ProjectService projectService;

    @PostMapping
    public ResponseEntity<?> createProject(@RequestBody @Validated ProjectCreateRequestDTO requestDTO, Principal principal, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ValidationErrorHandler.handleValidationErrors(bindingResult);
        }

        return ResponseEntity.ok(projectService.save(requestDTO, principal));
    }

    @GetMapping("/my-projects")
    public ResponseEntity<Page<Project>> getProjectsByUserEmail(
            Principal principal,
            @PageableDefault(sort = "startDate", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        return ResponseEntity.ok(projectService.findByPrincipal(principal, pageable));
    }
}
