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

    public Project saveProject(Project project) {
        return projectRepository.save(project);
    }

    public Project createProject(ProjectCreateRequestDTO requestDTO, Principal principal) {
        User projectManager = getUserFromPrincipal(principal);
        Set<User> teamMembers = getTeamMembersFromRequest(requestDTO, projectManager);

        return projectRepository.save(new Project(requestDTO, projectManager, teamMembers));
    }

    public Page<Project> findProjectsByPrincipal(Principal principal, Pageable pageable) {
        User user = getUserFromPrincipal(principal);
        return projectRepository.findProjectsByUser(user, pageable);
    }

    public Project findProjectById(Long id) {
        return projectRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Project not found"));
    }

    public void deleteProjectById(Long id, User user) {
        Project project = findProjectById(id);
        if (!project.getProjectManager().equals(user)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "You are not authorized to delete this project.");
        }
        projectRepository.deleteById(id);
    }

    private User getUserFromPrincipal(Principal principal) {
        if (principal == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "You are not logged in");
        }
        return userService.findByEmail(principal.getName());
    }

    private Set<User> getTeamMembersFromRequest(ProjectCreateRequestDTO requestDTO, User projectManager) {
        Set<User> teamMembers = requestDTO.getTeamMemberIds().stream()
                .map(userService::findById)
                .collect(Collectors.toSet());
        teamMembers.add(projectManager);
        return teamMembers;
    }
}
