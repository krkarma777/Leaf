package com.leaf.domain.dtos.project;

import com.leaf.domain.enums.PriorityType;
import com.leaf.domain.enums.Status;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class ProjectCreateRequestDTO {
    private String projectName;
    private String description;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Long projectManagerId;
    private List<Long> teamMemberIds;
    private String category;
    private PriorityType priority;
    private Status status;
}
