package com.TaskManagement.Entity;

import com.TaskManagement.Enum.TaskPriority;
import com.TaskManagement.Enum.TaskStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "tasks")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @Column( nullable = false)
    private String taskTitle;

    @Column(length = 2000)
    private String taskDescription;

    @Column(nullable = false)
    private String assignedToEmail;
    private LocalDateTime assignedAt=LocalDateTime.now();
    private LocalDateTime dueDate;

    @Enumerated(EnumType.STRING)
    private TaskStatus taskStatus;

    @Enumerated(EnumType.STRING)
    private TaskPriority taskPriority;


//    private String createdByEmail;


}
