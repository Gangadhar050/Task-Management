package com.TaskManagement.Entity;

import com.TaskManagement.Enum.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "workFlow_Transcation")

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WorkFlowTranscation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String fromStatus;
    private String toStatus;
    private String actionName;
    private Role roles;

    @ManyToOne
    @JoinColumn(name="workFlow_id")
    private WorkFlow workFlow;


}
