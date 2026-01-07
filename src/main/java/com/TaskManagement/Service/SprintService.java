package com.TaskManagement.Service;

import com.TaskManagement.Entity.Issue;
import com.TaskManagement.Entity.Sprint;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
@Service
public interface SprintService {
    Sprint creatteSprint(Sprint sprint);

    @Transactional
    Issue assignIssueToSprint(Long sprintId, Long issueId);

    @Transactional
    Sprint startSprint(Long sprintId);

    @Transactional
    Sprint closingSprint(Long sprintId);

    Map<String, Object> getBurnDownData(Long sprintId);

}
