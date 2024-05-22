package com.leaf.domain.dtos.project;

import com.leaf.domain.enums.PriorityType;
import com.leaf.domain.enums.Status;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class ProjectCreateRequestDTO {

    @NotBlank(message = "Project name is required")
    @Size(max = 100, message = "Project name must be less than 100 characters")
    private String projectName;

    @NotBlank(message = "Description is required")
    @Size(max = 500, message = "Description must be less than 500 characters")
    private String description;

    @NotNull(message = "Start date is required")
    @Future(message = "Start date must be in the future")
    private LocalDateTime startDate;

    @NotNull(message = "End date is required")
    @Future(message = "End date must be in the future")
    private LocalDateTime endDate;

    @NotEmpty(message = "At least one team member is required")
    private List<Long> teamMemberIds;

    @NotBlank(message = "Category is required")
    @Size(max = 50, message = "Category must be less than 50 characters")
    private String category;

    @NotNull(message = "Priority is required")
    private PriorityType priority;

    @NotNull(message = "Status is required")
    private Status status;

    public ProjectCreateRequestDTO(String projectName, String description, LocalDateTime startDate, LocalDateTime endDate, List<Long> teamMemberIds, String category, PriorityType priority, Status status) {
        this.projectName = projectName;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.teamMemberIds = teamMemberIds;
        this.category = category;
        this.priority = priority;
        this.status = status;
    }

    public ProjectCreateRequestDTO() {
    }
}
