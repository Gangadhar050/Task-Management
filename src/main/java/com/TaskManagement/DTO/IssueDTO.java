package com.TaskManagement.DTO;

import com.TaskManagement.Enum.IssuePriority;
import com.TaskManagement.Enum.IssueStatus;
import com.TaskManagement.Enum.IssueType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class IssueDTO {

    private Long Id;
    private String issueKey;
    private String issueTitle;
    private String issueDescription;
    private IssueType issueType;
    private IssuePriority issuePriority;
    private IssueStatus issueStatus;
    private String assignedEmail;
    private String reporterEmail;
    private Long epicId;
    private Long sprintId;
    private LocalDateTime createdAt=LocalDateTime.now();
    private LocalDateTime updatedAt=LocalDateTime.now();
    private LocalDateTime dueDate;

    // Instead of Set<Label>, use List<String> to simplify API communication
//    private List<String> labels;
    private Set<String> labels;
}
