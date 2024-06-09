package com.leaf.web.controllers.api;

import com.leaf.domain.dtos.project.ProjectCreateRequestDTO;
import com.leaf.domain.dtos.project.ProjectEditRequestDTO;
import com.leaf.domain.dtos.project.ProjectResponseDTO;
import com.leaf.domain.entities.Project;
import com.leaf.domain.entities.User;
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
import java.util.List;
import java.util.Map;

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
    public ResponseEntity<Map<String, Object>> getProjectsByUserEmail(
            Principal principal,
            @PageableDefault(sort = "startDate", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        Page<Project> projects = projectService.findByPrincipal(principal, pageable);
        List<ProjectResponseDTO> list = projects.getContent().stream().map(ProjectResponseDTO::new).toList();

        return ResponseEntity.ok(
                Map.of("content", list, "perPage", pageable.getPageNumber(), "totalPages", projects.getTotalPages())
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProjectResponseDTO> getProjectById(@PathVariable Long id, Principal principal) {
        Project project = projectService.findById(id);

        if (!project.getTeamMembers().contains(userService.findByEmail(principal.getName()))) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        ProjectResponseDTO responseDTO = new ProjectResponseDTO(project);
        return ResponseEntity.ok(responseDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> editProject(@PathVariable("id") Long id, @RequestBody ProjectEditRequestDTO requestDTO) {
        Project project = projectService.findById(id);
        project.edit(requestDTO);
        projectService.save(project);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProject(@PathVariable("id") Long id, Principal principal) {
        User user = userService.findByEmail(principal.getName());
        projectService.deleteById(id, user);
        return ResponseEntity.noContent().build();
    }
}
