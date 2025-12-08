package com.TaskManagement.Repository;

import com.TaskManagement.Entity.Issue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface IssueRepository extends JpaRepository<Issue,Long> {
    Optional<Issue>findByIssueKey(String issueKey);
    List<Issue> findAllByAssignedEmail( String assignedEmail);
    List<Issue>findByEpicId(Long epicId);

    List<Issue>findBySprintId(Long sprintId);
    List<Issue>findByIssueStatus(String status);

    Optional <Issue>findByAssignedEmail(String assignedEmail);
}
