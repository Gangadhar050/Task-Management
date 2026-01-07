package com.TaskManagement.ServiceIMPL;

import com.TaskManagement.Entity.WorkFlow;
import com.TaskManagement.Entity.WorkFlowTranscation;
import com.TaskManagement.Enum.IssueStatus;
import com.TaskManagement.Enum.Role;
import com.TaskManagement.Repository.WorkFlowRepository;
import com.TaskManagement.Repository.WorkFlowTransactionRepository;
import com.TaskManagement.Service.WorkFlowService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class WorkFlowServiceImpl implements WorkFlowService {

    @Autowired
    private WorkFlowRepository workFlowRepository;

    @Autowired
    private WorkFlowTransactionRepository workFlowTransactionRepository;


    @Override
    public WorkFlow createWorkFlow(String name, List<WorkFlowTranscation> workFlowTranscations){

        WorkFlow wf= new WorkFlow();
        wf.setName(name);
        for(WorkFlowTranscation t:workFlowTranscations){
            t.setWorkFlow(wf);
        }
        wf.setTranscations(workFlowTranscations);
        return workFlowRepository.save(wf);
    }
    @Override
    public WorkFlow getWorkFlow(String name){
        return workFlowRepository.findByName(name)
                .orElseThrow(()-> new RuntimeException("WorkFloe Not Found"));
    }
    @Override
    public List<WorkFlow>getAllWorkFlow(){
        return workFlowRepository.findAll();
    }
    @Override
    public boolean isTransactionAllowed(Long workFlowId, IssueStatus fromStatus, IssueStatus toStatus, Set<Role> userRoles) {
//        WorkFlowTranscation wft = (WorkFlowTranscation) workFlowTransactionRepository.findByWorkFlowIdAndFromStatusandToStatus(workFlowId,fromStatus,toStatus)
//                .orElseThrow(()-> new RuntimeException("Trasaction not defined:"+fromStatus+"->"+toStatus));
//        boolean allowed = userRoles.stream().anyMatch(wft.getRoles()::contains);
//
//        if(!allowed){
//            throw  new RuntimeException("user roles"+userRoles+"mot allowed forTransaction"+fromStatus+"->"+toStatus)
//        }
        WorkFlowTranscation wft =
                (WorkFlowTranscation) workFlowTransactionRepository
                        .findByWorkFlowIdAndFromStatusAndToStatus(
                                workFlowId, fromStatus, toStatus)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Transaction not defined: "
                                                + fromStatus + " -> " + toStatus));

        if (!userRoles.contains(wft.getRoles())) {
            throw new RuntimeException(
                    "User roles " + userRoles +
                            " not allowed for transaction "
                            + fromStatus + " -> " + toStatus);
        }

        return true;
    }

}

