package com.TaskManagement.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserProfileDTO {
    private String username;
    private String userOrganizationEmail;
    private String department;
    private String designation;
    private String organizationName;
    private LocalDateTime createdAt;
    private boolean active;
}
