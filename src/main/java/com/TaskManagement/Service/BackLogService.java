package com.TaskManagement.Service;

import com.TaskManagement.Entity.Issue;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

public interface BackLogService {

    List<Issue>getByBackLog(Long projectId,Long sprintId);

    @Transactional
    void recordBackLog(Long projectId, List<Long> orderedIssueIds);

    @Transactional
    void addIssueToSprint(Long issueId, Long sprintId);

    Map<String,Object> getBacklogHierarchy(Long projectId);
}
