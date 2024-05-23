package com.leaf.domain.dtos.project;

import com.leaf.domain.dtos.user.UserResponseDTO;
import com.leaf.domain.entities.Project;
import com.leaf.domain.enums.PriorityType;
import com.leaf.domain.enums.Status;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
public class ProjectResponseDTO {

    private Long id;
    private String projectName;
    private String description;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private UserResponseDTO projectManager;
    private Set<UserResponseDTO> teamMembers;
    private String category;
    private PriorityType priority;
    private Status status;

    // Constructor to convert Project entity to ProjectResponseDTO
    public ProjectResponseDTO(Project project) {
        this.id = project.getId();
        this.projectName = project.getProjectName();
        this.description = project.getDescription();
        this.startDate = project.getStartDate();
        this.endDate = project.getEndDate();
        this.projectManager = new UserResponseDTO(project.getProjectManager());
        this.teamMembers = project.getTeamMembers().stream().map(UserResponseDTO::new).collect(Collectors.toSet());
        this.category = project.getCategory();
        this.priority = project.getPriority();
        this.status = project.getStatus();
    }
}
