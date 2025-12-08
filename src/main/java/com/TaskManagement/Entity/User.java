package com.TaskManagement.Entity;

import com.TaskManagement.Enum.Role;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder  // Builder pattern for easier object creation
public class User {
    @Id // Primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-increment ID
    private Long id;
    @Column(nullable = false)
    private String userName;
    @Column(nullable = false, unique = true)
    private String userEmail;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING) // Store enum as string in DB
    private Role role;
    @Column(nullable = false)
    private boolean active = true; // Default to active user
    @Column(nullable = false)
    private LocalDateTime createdAt=LocalDateTime.now();

}