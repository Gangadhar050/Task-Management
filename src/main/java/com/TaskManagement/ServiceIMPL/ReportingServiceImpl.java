package com.TaskManagement.ServiceIMPL;

import com.TaskManagement.Entity.Issue;
import com.TaskManagement.Entity.Sprint;
import com.TaskManagement.Enum.IssueStatus;
import com.TaskManagement.Enum.SprintState;
import com.TaskManagement.Repository.IssueRepository;
import com.TaskManagement.Repository.SprintRepository;
import com.TaskManagement.Service.ReportingService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReportingServiceImpl implements ReportingService {

    @Autowired
    private IssueRepository issueRepository;

    @Autowired
    private SprintRepository sprintRepository;

    @Override
    public Map<String, Object> burnDown(Long sprintId) {
        Sprint sprint = sprintRepository.findById(sprintId).orElseThrow(() -> new RuntimeException("Sprint not found"));
        List<Issue> issues = issueRepository.findBySprintId(sprintId);

       int total = issues.size();

       Map<String,Object> chart = new HashMap<>();

        LocalDateTime startDate = sprint.getStartDate();
        LocalDateTime endDate = sprint.getEndDate()!=null ? sprint.getEndDate():LocalDateTime.now();

        for(LocalDateTime d=startDate;!d.isAfter(endDate);d = d.plusDays(1)){
            int done = (int) issues.stream().filter(i->"DONE".equals(i.getIssueStatus().name())).count();
            chart.put(d.toString(),total - done);
        }

        Map<String,Object>response = new HashMap<>();
        response.put("SprintId",sprintId);
        response.put("burnDown",chart);
        return response;

    }

    @Override
    public Map<String, Object> velocity(Long projectId) {
        List<Sprint> completed = sprintRepository.findByProjectId(projectId).stream().filter(i-> i.getState()== SprintState.COMPLETED)
                .collect(Collectors.toList());

        Map<String,Integer> velocity = new LinkedHashMap<>();

        for(Sprint s:completed){
            int done = (int)issueRepository.findBySprintId(s.getId()).stream().filter(i -> i.getIssueStatus() == IssueStatus.DONE).count();

            velocity.put(s.getSprintName(),done);
        }
        Map<String,Object>response = new HashMap<>();

        response.put("ProjetId",projectId);
        response.put("Velocity",velocity);

        return response;
    }
}
