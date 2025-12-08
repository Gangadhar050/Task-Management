package com.TaskManagement.Service;

import com.TaskManagement.Entity.WorkFlow;
import com.TaskManagement.Entity.WorkFlowTranscation;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface WorkFlowService {
    WorkFlow createWorkFlow(String name, List<WorkFlowTranscation> workFlowTranscations);

    WorkFlow getWorkFlow(String name);

    List<WorkFlow>getAllWorkFlow();
}
