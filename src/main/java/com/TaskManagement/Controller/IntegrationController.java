package com.TaskManagement.Controller;

import com.TaskManagement.Service.IntegrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/integration")
public class IntegrationController {

    @Autowired
    private IntegrationService integrationService;

    @PostMapping("/github")
    public ResponseEntity<?>processGithubEvent(@RequestHeader("GitHub_Event")String event, @RequestBody Map<String,Object> payload){
        System.out.println("Github Event Received: " + event);
        integrationService.processGithubEvent(event.toUpperCase(),payload);
        return ResponseEntity.ok("GitHub event processed successfully");
    }

    @PostMapping("/jenkins")
    public ResponseEntity<?>processJenkinsEvent(@RequestBody Map<String,Object> paylod){
        System.out.println("Jenkins Event Received");
        integrationService.processJenkinEvent(paylod);
        return ResponseEntity.ok("Jenkins event processed successfully");
    }

    @PostMapping("/Docker")
    public ResponseEntity<?>processDockerEvent(@RequestBody Map<String,Object> payload){
        System.out.println("Docker Event Received");
        integrationService.processDockerEvent(payload);
        return ResponseEntity.ok("Docker event processed successfully");
    }

    @PostMapping("/commit")
    public ResponseEntity<?>handleCommit(@RequestParam String message,String auther){
        integrationService.handelCommitMessage(message,auther);
        return ResponseEntity.ok("Commit message processed successfully");
    }

    @PostMapping("/pullRequest")
    public ResponseEntity<?>handlePullRequest(@RequestParam String title,String author){
        integrationService.handelPullRequest(title,author);
        return ResponseEntity.ok("Pull request processed successfully");
    }
}
