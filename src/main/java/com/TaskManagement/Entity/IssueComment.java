package com.TaskManagement.Entity;

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
@Table(name="issueComments")

public class IssueComment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

   //These are forenkey references
    private Long issueId;

    // who is providing the comment
    private String authorEmail;

    @Column(length = 2000)
    private String body;

    private LocalDateTime createdAt=LocalDateTime.now();
}
