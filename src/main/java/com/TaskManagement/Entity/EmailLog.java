package com.TaskManagement.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.Length;

import java.time.LocalDateTime;

@Entity
@Table(name = "notification")

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EmailLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String recipientEmail;
    private String subject;

    @Column (length=5000)
    private String body;
    private boolean sentStatus;
    private LocalDateTime sentAt=LocalDateTime.now();
}
