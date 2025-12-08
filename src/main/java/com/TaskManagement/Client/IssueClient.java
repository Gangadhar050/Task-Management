//package com.TaskManagement.Client;
//
//import com.TaskManagement.Enum.IssueStatus;
//import org.springframework.cloud.openfeign.FeignClient;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.PutMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//
//@FeignClient(name = "issue-service", url = "http://issueservice.local")
//public interface IssueClient {
//
//    @PutMapping("/{id}/status")
//    void status(@PathVariable Long id, @RequestParam String status,@RequestParam String updatedBy);
//
//    @PostMapping("/{id}/commmit")
//    void commit(@PathVariable Long id,@RequestParam String author,@RequestParam String body);
//
//    void updateStatus(Long issueid, IssueStatus issueStatus, String docker);
//
//    void addCommit(Long issueid, String docker, String s);
//
//
//}
package com.TaskManagement.Client;

import com.TaskManagement.Enum.IssueStatus;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "issue-service", url = "http://issueservice.local")
public interface IssueClient {

    @PutMapping("/{id}/status")
    void status(
            @PathVariable("id") Long id,
            @RequestParam("status") String status,
            @RequestParam("updatedBy") String updatedBy
    );

    @PostMapping("/{id}/commit")
    void commit(
            @PathVariable("id") Long id,
            @RequestParam("author") String author,
            @RequestParam("body") String body
    );

    // Added proper HTTP annotation
    @PutMapping("/{id}/updateStatus")
    void updateStatus(
            @PathVariable("id") Long issueId,
            @RequestParam("status") IssueStatus issueStatus,
            @RequestParam("updatedBy") String updatedBy
    );

    // Added proper HTTP annotation
    @PostMapping("/{id}/addCommit")
    void addCommit(
            @PathVariable("id") Long issueId,
            @RequestParam("author") String author,
            @RequestParam("body") String body
    );
}

