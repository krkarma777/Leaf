package com.leaf.domain.entities;

import com.leaf.domain.dtos.project.ProjectCreateRequestDTO;
import com.leaf.domain.dtos.project.ProjectEditRequestDTO;
import com.leaf.domain.enums.PriorityType;
import com.leaf.domain.enums.Status;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "projects")
@Getter
@Setter
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String projectName;

    @Column(nullable = false, length = 500)
    private String description;

    @Column(nullable = false)
    private LocalDateTime startDate;

    @Column(nullable = false)
    private LocalDateTime endDate;

    @ManyToOne
    @JoinColumn(name = "project_manager_id", nullable = false)
    private User projectManager;

    @ManyToMany
    @JoinTable(
            name = "project_team_members",
            joinColumns = @JoinColumn(name = "project_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private Set<User> teamMembers = new HashSet<>();

    @Column(nullable = false, length = 50)
    private String category;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private PriorityType priority;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private Status status;

    public Project(ProjectCreateRequestDTO requestDTO, User projectManager, Set<User> teamMembers) {
        this.projectName = requestDTO.getProjectName();
        this.description = requestDTO.getDescription();
        this.startDate = requestDTO.getStartDate();
        this.endDate = requestDTO.getEndDate();
        this.projectManager = projectManager;
        this.teamMembers = teamMembers;
        this.category = requestDTO.getCategory();
        this.priority = requestDTO.getPriority();
        this.status = requestDTO.getStatus();
    }

    public Project() {
    }

    public Project(String projectName, String description, LocalDateTime startDate, LocalDateTime endDate, User projectManager, String category, PriorityType priority, Status status) {
        this.projectName = projectName;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.projectManager = projectManager;
        this.category = category;
        this.priority = priority;
        this.status = status;
    }

    public void edit(ProjectEditRequestDTO requestDTO) {
        LocalDateTime now = LocalDateTime.now();
        if (!requestDTO.getStartDate().isAfter(now)) {
            this.startDate = requestDTO.getStartDate();
        }
        this.projectName = requestDTO.getProjectName();
        this.description = requestDTO.getDescription();
        this.endDate = requestDTO.getEndDate();
        this.category = requestDTO.getCategory();
        this.priority = requestDTO.getPriority();
        this.status = requestDTO.getStatus();
    }
}
