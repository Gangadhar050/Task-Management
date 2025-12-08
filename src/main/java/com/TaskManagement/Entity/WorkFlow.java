package com.TaskManagement.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name="workFlows")

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WorkFlow {//for microservice write workflow and workflowtranscation in one code base

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToMany(mappedBy = "workFlow",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<WorkFlowTranscation>transcations=new ArrayList<>();

}
