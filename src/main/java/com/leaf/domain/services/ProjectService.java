package com.leaf.domain.services;

import com.leaf.domain.dtos.project.ProjectCreateRequestDTO;
import com.leaf.domain.entities.Project;
import com.leaf.domain.entities.User;
import com.leaf.domain.repositories.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final UserService userService;

    public Project save(Project project) {
        return projectRepository.save(project);
    }

    public Project save(ProjectCreateRequestDTO requestDTO, Principal principal) {
        User projectManager = userService.findByEmail(principal.getName());

        Set<User> teamMembers = requestDTO.getTeamMemberIds().stream().map(userService::findById).collect(Collectors.toSet());
        teamMembers.add(projectManager);

        return projectRepository.save(new Project(requestDTO, projectManager, teamMembers));
    }

    public Page<Project> findByPrincipal(Principal principal, Pageable pageable) {
        if (principal == null) throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "You are not logged in");
        User user = userService.findByEmail(principal.getName());
        return projectRepository.findProjectsByUser(user, pageable);
    }

    public Project findById(Long id) {
        return projectRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Project not found"));
    }
}
