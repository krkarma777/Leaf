package com.leaf.domain.dtos.project;

import com.leaf.domain.enums.PriorityType;
import com.leaf.domain.enums.Status;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ProjectEditRequestDTO {

    private String projectName;
    private String description;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String category;
    private PriorityType priority;
    private Status status;

    public ProjectEditRequestDTO(String projectName, String description, LocalDateTime startDate, LocalDateTime endDate, String category, PriorityType priority, Status status) {
        this.projectName = projectName;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.category = category;
        this.priority = priority;
        this.status = status;
    }
}
