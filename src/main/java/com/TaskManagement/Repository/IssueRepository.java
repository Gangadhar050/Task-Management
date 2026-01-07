package com.TaskManagement.Repository;

import com.TaskManagement.Entity.Issue;
import com.TaskManagement.Enum.IssueStatus;
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
    List<Issue>findByIssueStatus(IssueStatus status);
    Optional<Issue>findById(Long id);


    List<Issue> findByProjectIdAndSprintIdOrderByBacklogPosition(Long projectId, Long sprintId);



}
