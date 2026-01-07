package com.TaskManagement.Controller;

import com.TaskManagement.Entity.WorkFlow;
import com.TaskManagement.Entity.WorkFlowTranscation;
import com.TaskManagement.Service.WorkFlowService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/workFlows")
@RequiredArgsConstructor
public class WorkFlowController {

    @Autowired
    private WorkFlowService workFlowService;

    @PostMapping("/create")
    public ResponseEntity<WorkFlow>create(@RequestParam String name, @RequestBody WorkFlowTranscation workFlowTranscation){
        WorkFlow wf = workFlowService.createWorkFlow(name, (List<WorkFlowTranscation>) workFlowTranscation);
        return ResponseEntity.ok(wf);
    }

    @GetMapping("/all")
    public ResponseEntity <List<WorkFlow>>getAll(){
       return ResponseEntity.ok(workFlowService.getAllWorkFlow());
    }

    @GetMapping("/{name}")
    public ResponseEntity<WorkFlow>getWorkFlow(@PathVariable String name){
        return ResponseEntity.ok(workFlowService.getWorkFlow(name));
    }
}
