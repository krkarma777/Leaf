package com.leaf.domain.services;

import com.leaf.domain.dtos.project.ProjectCreateRequestDTO;
import com.leaf.domain.entities.Project;
import com.leaf.domain.entities.User;
import com.leaf.domain.repositories.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
}
