package com.TaskManagement.Controller;

import com.TaskManagement.Entity.Issue;
import com.TaskManagement.Entity.Sprint;
import com.TaskManagement.Service.SprintService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/sprints")
public class SprintController {

    @Autowired
    private SprintService sprintService;

    @PostMapping("/create")
    public ResponseEntity<Sprint> create(@RequestBody Sprint sprint){
        return ResponseEntity.ok(sprintService.creatteSprint(sprint));
    }

    @PutMapping("/assign/{sprintId}/{issueId}")
    public ResponseEntity<Issue>assignIssueToSprint(@PathVariable Long sprintId, @PathVariable Long issueId){
        return ResponseEntity.ok(sprintService.assignIssueToSprint(sprintId,issueId));
    }

    @PutMapping("/{sprintId}/start")
    public ResponseEntity<Sprint>startSprint(@PathVariable Long springId){
        return ResponseEntity.ok(sprintService.startSprint(springId));
    }

    @PutMapping("/{sprintId}/close")
    public ResponseEntity<Sprint>endSprint(@PathVariable Long springId){
        return ResponseEntity.ok(sprintService.startSprint(springId));
    }

    @GetMapping("{sprintId}/burnDown")
    public ResponseEntity<Object>getBurnDownData(@PathVariable Long sprintId) {
        return ResponseEntity.ok(sprintService.getBurnDownData(sprintId));
    }

}

