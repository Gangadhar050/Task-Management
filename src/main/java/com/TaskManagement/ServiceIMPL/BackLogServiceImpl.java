package com.TaskManagement.ServiceIMPL;

import com.TaskManagement.Entity.Issue;
import com.TaskManagement.Entity.Sprint;
import com.TaskManagement.Enum.SprintState;
import com.TaskManagement.Repository.IssueRepository;
import com.TaskManagement.Repository.SprintRepository;
import com.TaskManagement.Service.BackLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
@Service
@RequiredArgsConstructor
public class BackLogServiceImpl implements BackLogService {

    @Autowired
    private IssueRepository issueRepository;

    @Autowired
    private SprintRepository sprintRepository;

    @Override
    public List<Issue>getByBackLog(Long projectId,Long sprintId){
        if (projectId==null){
            return issueRepository .findByProjectIdAndSprintIdOrderByBacklogPosition(projectId,sprintId);
        }
        return issueRepository.findByProjectIdAndSprintIdOrderByBacklogPosition(projectId,sprintId);
    }

    @Transactional
    @Override
    public void recordBackLog(Long projectId, List<Long> orderedIssueIds){
        int post= 0;
        for (Long issueId:orderedIssueIds){
            Issue issue= issueRepository.findById(issueId).orElseThrow(()-> new RuntimeException("issue not found"));
            issue.setBacklogPosition(post);
            issueRepository.save(issue);
            post++;
        }
    }

    @Transactional
    @Override
    public void addIssueToSprint(Long issueId, Long sprintId) {

        Issue issue = issueRepository.findById(issueId).orElseThrow(() -> new RuntimeException("issue not found"));
        Sprint sprint = sprintRepository.findById(sprintId).orElseThrow(() -> new RuntimeException("sprint not found"));

        SprintState status = sprint.getState();
        if (status != SprintState.PLANNED && status != SprintState.ACTIVE) {
            throw new RuntimeException("can not add issue to sprint in state: " + status);
        }
        issue.setSprintId(sprintId);
        issue.setBacklogPosition(null);
        issueRepository.save(issue);
        return;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Map<String,Object>getBacklogHierarchy(Long projectId){

        List<Issue> backlog= getByBackLog(projectId,null);
        Map<Long,Map<String,Object>> epicMap= new LinkedHashMap<>();

        for (Issue issue: backlog){
           if(issue.getIssueType()!=null && "EPIC".equalsIgnoreCase(issue.getIssueType().name())){

               Map<String,Object>data= new LinkedHashMap<>();
               data.put("epic",issue);
               data.put("Stories",new ArrayList<Issue>());
               data.put("subtask",new HashMap<Long,List<Issue>>());
               epicMap.put(issue.getId(),data);
           }
        }
        for(Issue issue:backlog){
            if(issue.getIssueType() != null && "STORY".equalsIgnoreCase(issue.getIssueType().name())){
                Long epicId = issue.getEpicId();

                if(epicId != null && epicMap.containsKey(epicId)){
                    List<Issue>stories = (List<Issue>) epicMap.get(epicId).get("Stories");
                    stories.add(issue);
                }
            }
        }

        for(Issue issue:backlog){
            if(issue.getIssueType()!= null && "SUBTASK".equalsIgnoreCase(issue.getIssueType().name())){
                Issue parent = issue.getSourceIssue();

                if(parent != null){

                    for(Map<String,Object>epicDate:epicMap.values()) {
                        List<Issue> stories = (List<Issue>) epicDate.get("Stories");
                        for (Issue story : stories) {
                            if (story.getId().equals(parent)) {
                                Map<Long, List<Issue>> subtaskMap = (Map<Long, List<Issue>>) epicDate.get("subtask");
                                List<Issue>subTasks= subtaskMap.get(parent.getId());
                                if(subTasks==null){
                                    subTasks= new ArrayList<>();
                                    subtaskMap.put(parent.getId(),subTasks);
                                }
                                subTasks.add(issue);
                                break;
                            }
                        }
                    }
                }
            }
        }
        return Collections.singletonMap("epic",epicMap);
    }
}
