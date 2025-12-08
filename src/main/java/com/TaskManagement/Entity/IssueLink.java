package com.TaskManagement.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.service.annotation.GetExchange;

@Entity
@Table(name = "issueLink")

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class IssueLink {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long sourceIssueId;
    private  Long targetIssueId;
    private String linkType;

//This one is going to connect to issue By mapping in Issue Entity



}
