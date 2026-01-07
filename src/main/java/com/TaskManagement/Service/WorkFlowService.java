package com.TaskManagement.Service;

import com.TaskManagement.Entity.WorkFlow;
import com.TaskManagement.Entity.WorkFlowTranscation;
import com.TaskManagement.Enum.IssueStatus;
import com.TaskManagement.Enum.Role;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public interface WorkFlowService {
    WorkFlow createWorkFlow(String name, List<WorkFlowTranscation> workFlowTranscations);

    WorkFlow getWorkFlow(String name);

    List<WorkFlow>getAllWorkFlow();

    boolean isTransactionAllowed(Long workFlowId, IssueStatus fromStatus, IssueStatus toStatus, Set<Role> userRole);
}
