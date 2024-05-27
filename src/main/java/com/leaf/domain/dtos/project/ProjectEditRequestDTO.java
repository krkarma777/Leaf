package com.leaf.domain.dtos.project;

import com.leaf.domain.enums.PriorityType;
import com.leaf.domain.enums.Status;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ProjectEditRequestDTO {

    private String projectName;
    private String description;
    private LocalDateTime endDate;
    private String category;
    private PriorityType priority;
    private Status status;
}
