package com.TaskManagement.DTO;

import com.TaskManagement.Enum.TaskPriority;
import com.TaskManagement.Enum.TaskStatus;
import lombok.*;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class TaskDTO {
    private Long id;
    private String taskTitle;
    private String taskDescription;
    private String assignedToEmail;
    private LocalDateTime assignedAt;
    private LocalDateTime dueDate;
    private TaskStatus taskStatus;
    private TaskPriority taskPriority;

}
