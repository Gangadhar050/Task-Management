package com.TaskManagement.Repository;

import com.TaskManagement.Entity.WorkFlowTranscation;
import com.TaskManagement.Enum.IssueStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WorkFlowTransactionRepository extends JpaRepository<WorkFlowTranscation,Long > {
Optional<WorkFlowRepository>findByWorkFlowIdAndFromStatusAndToStatus(Long workFlowId, IssueStatus fromStatus, IssueStatus toStatus);

}
