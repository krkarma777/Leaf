package com.leaf.web.controllers;

import com.leaf.domain.dtos.project.ProjectCreateRequestDTO;
import com.leaf.domain.services.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/projects")
public class ProjectController {

    private final ProjectService projectService;

    @PostMapping
    public ResponseEntity<?> createProject(@RequestBody @Validated ProjectCreateRequestDTO requestDTO, Principal principal, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            Map<String, String> errors = getErrors(bindingResult);
            String s = errors.values().stream().toList().get(0);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("message", s, "errors", errors));
        }

        return ResponseEntity.ok(projectService.save(requestDTO, principal));
    }

    private Map<String, String> getErrors(BindingResult bindingResult) {
        return bindingResult.getFieldErrors().stream()
                .collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage));
    }
}
