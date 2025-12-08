package com.TaskManagement.Controller;

import com.TaskManagement.DTO.IssueDTO;
import com.TaskManagement.Entity.IssueComment;
import com.TaskManagement.Enum.IssueStatus;
import com.TaskManagement.Service.IssueService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/issue")
public class IssueController {
    @Autowired
    private IssueService issueService;

    @PostMapping("/create")
    public ResponseEntity<IssueDTO> createIssue(@RequestBody IssueDTO dto) {
        IssueDTO createissue = issueService.createIssue(dto);
        return ResponseEntity.ok(createissue);
    }

    @GetMapping("/{id}")
    public ResponseEntity<IssueDTO> getByissueId(@PathVariable Long id) {
        return ResponseEntity.ok(issueService.getById(id));
    }

    @GetMapping("/assignee/{assignedEmail}")
    public ResponseEntity<List<IssueDTO>> getByAssignedEmail(@PathVariable String assignedEmail){
        return ResponseEntity.ok(issueService.getByAssignedEmail(assignedEmail));
    }
//  @PutMapping - When you wanted to edit something ,when you want to write something new
//  @PatchMapping - When you wanted to update something, partially update something
    @PatchMapping("/{id}/status")
    public ResponseEntity<IssueDTO>updateIssue(@PathVariable Long id,
                                             @RequestParam IssueStatus issueStatus,
                                             @RequestHeader(value = "x-user_Email",required = false)String user){
       return ResponseEntity.ok(issueService.updateStatus(id,issueStatus,user));
    }

@PostMapping("/{id}/comment")
public ResponseEntity<IssueComment> addComment(@PathVariable Long id,
                                               @RequestBody Map<String, String> body,
                                               @RequestHeader(value = "x-user_Email",required = false)String user){
    String commentBody = body.get("body");
   String auther= user== null ? body.getOrDefault("autherEmail","user@gmail"):user;
   return ResponseEntity.ok(issueService.addComment(id,auther,commentBody));

}
}

//
//    @PostMapping("/api/test").mm
//    public String apiTesting(){
//        return "Issue API is Working";
//    }
//
//}
