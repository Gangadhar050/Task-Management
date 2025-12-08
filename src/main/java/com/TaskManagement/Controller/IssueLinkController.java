package com.TaskManagement.Controller;

import com.TaskManagement.Entity.IssueLink;
import com.TaskManagement.Service.IssueLinkService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/issueLinks")
@RequiredArgsConstructor
public class IssueLinkController {

    @Autowired
    private IssueLinkService issueLinkService;
    @PostMapping("/create")
    public ResponseEntity<IssueLink>create(@RequestBody IssueLink issue){
        return ResponseEntity.ok(issueLinkService.createLink(issue.getSourceIssueId(),issue.getTargetIssueId(),issue.getLinkType()));
    }
    @GetMapping("/source/{issueSourceId}")
    public ResponseEntity<List<IssueLink>>getBySource(@PathVariable Long issueSourceId){
        return ResponseEntity.ok(issueLinkService.getLinkBySource(issueSourceId));
    }
    @GetMapping("/target/{targetIssueId}")
    public ResponseEntity<List<IssueLink>>getByTarget(@PathVariable Long targetIssueId){
        return ResponseEntity.ok(issueLinkService.getLinkByTarget(targetIssueId));
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteLink(@PathVariable Long id) {
        // Implementation for deleting the link would go here
        issueLinkService.deleteLink(id);

        return ResponseEntity.noContent().build();
    }
}
