package com.leaf.web.controllers.api;

import com.leaf.domain.dtos.project.ProjectCreateRequestDTO;
import com.leaf.domain.entities.Project;
import com.leaf.domain.services.ProjectService;
import com.leaf.domain.services.UserService;
import com.leaf.web.utils.ValidationErrorHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/projects")
public class ProjectAPIController {

    private final ProjectService projectService;
    private final UserService userService;

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

    @GetMapping("/{id}")
    public ResponseEntity<Project> getProjectById(@PathVariable Long id, Principal principal) {
        Project project = projectService.findById(id);

        if (!project.getTeamMembers().contains(userService.findByEmail(principal.getName()))) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        return ResponseEntity.ok(project);
    }
}
