package com.TaskManagement.Entity;

import com.TaskManagement.Enum.SprintState;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name="sprints")
public class Sprint {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String sprintName;
    private LocalDateTime startDate;
    private LocalDateTime endDate;

    private Long projectId;


    @Enumerated(EnumType.STRING)
    private SprintState state=SprintState.PLANNED;

    @Column(length = 5000)
    private String goal;

    private LocalDateTime createdAt=LocalDateTime.now();

//    private LocalDateTime updatedAt=LocalDateTime.now();
}
