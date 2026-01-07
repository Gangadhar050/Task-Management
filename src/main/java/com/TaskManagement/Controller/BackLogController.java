package com.TaskManagement.Controller;


import java.util.List;
import java.util.Map;

import com.TaskManagement.Entity.Issue;
import com.TaskManagement.Service.BackLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/backLogs")
@RequiredArgsConstructor
public class BackLogController {


    @Autowired
    private BackLogService backLogService;


    @GetMapping("/{projectId}")
    public ResponseEntity<List<Issue>>getBackLogs(@PathVariable(required=false)Long projectId,  @RequestParam Long sprintId){
        return ResponseEntity.ok(backLogService.getByBackLog(projectId,sprintId));
    }

    @GetMapping("/{projectId}/hierarchy")
    public ResponseEntity<Map<String,Object>>getHierachry(@PathVariable(required=false) Long projectId ){
        return ResponseEntity.ok(backLogService.getBacklogHierarchy(projectId));
    }
    @PostMapping("/{projectId}/record")
    public ResponseEntity<String>record(@PathVariable(required=false) Long projectId,@RequestBody List<Long> orderIds){
        backLogService.recordBackLog(projectId, orderIds);
        return ResponseEntity.ok("BackLog Recored");
    }
    @PutMapping("add-to_sprint/{issueId}")
    public ResponseEntity addToSprint(@PathVariable Long issueId, @PathVariable Long sprintId){
        backLogService.addIssueToSprint(issueId, sprintId);
        return ResponseEntity.ok("Issue added to sprint");
    }

}

