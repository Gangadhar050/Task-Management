package com.TaskManagement.Service;

import com.TaskManagement.DTO.IssueDTO;
import com.TaskManagement.Entity.IssueComment;
import com.TaskManagement.Entity.Sprint;
import com.TaskManagement.Enum.IssueStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface IssueService {
    IssueDTO createIssue(IssueDTO dto);
    IssueDTO getById(Long id);
    List<IssueDTO>getByAssignedEmail(String assignedEmail);
    List<IssueDTO>getBySprintId(Long sprintId);
    List<IssueDTO>getByEpicId(Long epicId);
     IssueComment addComment(Long issueId, String authorEmail, String body);
     IssueDTO updateStatus(Long id,IssueStatus status,String assignedEmail);
    Sprint createSprint(Sprint sprint);
    List<IssueDTO> search(Map<String, String> filter);
}
