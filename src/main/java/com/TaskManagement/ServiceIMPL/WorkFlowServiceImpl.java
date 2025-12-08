package com.TaskManagement.ServiceIMPL;

import com.TaskManagement.Entity.WorkFlow;
import com.TaskManagement.Entity.WorkFlowTranscation;
import com.TaskManagement.Repository.WorkFlowRepository;
import com.TaskManagement.Service.WorkFlowService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WorkFlowServiceImpl implements WorkFlowService {

    @Autowired
    private WorkFlowRepository workFlowRepository;

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

}
