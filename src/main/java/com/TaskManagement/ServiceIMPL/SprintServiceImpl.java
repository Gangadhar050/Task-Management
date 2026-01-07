package com.TaskManagement.ServiceIMPL;

import com.TaskManagement.Entity.Issue;
import com.TaskManagement.Entity.Sprint;
import com.TaskManagement.Enum.IssueStatus;
import com.TaskManagement.Enum.SprintState;
import com.TaskManagement.Repository.IssueRepository;
import com.TaskManagement.Repository.SprintRepository;
import com.TaskManagement.Service.SprintService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class SprintServiceImpl implements SprintService {

    @Autowired
    private SprintRepository sprintRepository;

    @Autowired
    private IssueRepository issueRepository;

    @Override
    public Sprint creatteSprint(Sprint sprint){
        sprint.setState(SprintState.PLANNED);
        return sprintRepository.save(sprint);
    }

    @Transactional
    @Override
    public Issue assignIssueToSprint(Long sprintId, Long issueId){
        Sprint sprint = sprintRepository.findById(sprintId).orElseThrow(() -> new RuntimeException("sprint not found"));
        Issue issue = issueRepository.findById(issueId).orElseThrow(() -> new RuntimeException("issue not found"));

        if (sprint.getState() == SprintState.COMPLETED) {
            throw new RuntimeException("Cannot add task to completed sprint");
        }

        issue.setSprintId(sprintId);
        issueRepository.save(issue);
        return issue;

    }

    @Transactional
    @Override
    public Sprint startSprint(Long sprintId){
        Sprint sprint = sprintRepository.findById(sprintId).orElseThrow(() -> new RuntimeException("sprint not found"));

        if (sprint.getState()== SprintState.PLANNED) {
            throw  new RuntimeException("only planned sprint can be started");
        }

        sprint.setState(SprintState.ACTIVE);

        if (sprint.getStartDate() == null) {
            sprint.setStartDate(java.time.LocalDateTime.now());
        }

        return sprintRepository.save(sprint);
    }

    @Transactional
    @Override
    public Sprint closingSprint(Long sprintId) {
        Sprint sprint = sprintRepository.findById(sprintId).orElseThrow(() -> new RuntimeException("sprint not found"));

        sprint.setState(SprintState.COMPLETED);

        if (sprint.getEndDate()== null) {
            sprint.setEndDate(LocalDateTime.now());

        }

        List<Issue>issues= issueRepository.findBySprintId(sprintId);
        for (Issue issue:issues){
            if (issue.getIssueStatus().name().equals(IssueStatus.DONE)){
                issue.setSprintId(null);
//                issue.setBackLogPosition(0);
                issueRepository.save(issue);
                return sprintRepository.save(sprint);
            }
        }
        return sprintRepository.save(sprint);
    }

    @Override
    public Map<String, Object> getBurnDownData(Long sprintId) {
        Sprint sprint = sprintRepository.findById(sprintId).orElseThrow(() -> new RuntimeException("sprint not found"));
        LocalDateTime start = sprint.getStartDate();
        LocalDateTime end = sprint.getEndDate() != null ? sprint.getEndDate() : LocalDateTime.now();

        List<Issue>issues= issueRepository.findBySprintId(sprintId);

        int totalTask= issues.size();

        Map<String, Object> burnDownData = new LinkedHashMap<>();

        LocalDateTime cursor= start;

        while (!cursor.isAfter(end)){
            long completedTasks = issues.stream()
                    .filter(issue -> issue.getIssueStatus().name().equals(IssueStatus.DONE)).count();

            burnDownData.put(cursor.toString(),totalTask-(int) completedTasks);
            cursor = cursor.plusDays(1);
        }
return burnDownData;
    }
    }
