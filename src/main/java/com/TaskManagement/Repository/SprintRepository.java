package com.TaskManagement.Repository;

import com.TaskManagement.Entity.Sprint;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SprintRepository extends JpaRepository<Sprint,Long> {

    List<Sprint>findByProjectId(Long projectId);
    List<Sprint>findByState(String state);


}
