package com.TaskManagement.Repository;

import com.TaskManagement.Entity.WorkFlow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface WorkFlowRepository extends JpaRepository<WorkFlow,Long> {

    Optional<WorkFlow>findByName(String name);

}
