package com.TaskManagement.ServiceIMPL;

import com.TaskManagement.Client.UserClient;
import com.TaskManagement.DTO.IssueDTO;
import com.TaskManagement.Entity.Issue;
import com.TaskManagement.Entity.IssueComment;
import com.TaskManagement.Entity.Label;
import com.TaskManagement.Entity.Sprint;
import com.TaskManagement.Enum.IssueStatus;
import com.TaskManagement.Enum.Role;
import com.TaskManagement.Repository.IssueCommentRepository;
import com.TaskManagement.Repository.IssueRepository;
import com.TaskManagement.Repository.LabelRepository;
import com.TaskManagement.Repository.SprintRepository;
import com.TaskManagement.Service.IssueService;
import com.TaskManagement.Service.WorkFlowService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class IssueServiceImpl implements IssueService {

    @Autowired
    private IssueRepository issueRepository;

    @Autowired
    private LabelRepository lableRepository;

    @Autowired
    private SprintRepository sprintRepository;

    @Autowired
    private IssueCommentRepository issueCommentRepository;

    @Autowired
    public WorkFlowService workFlowService;

    @Autowired
    private UserClient userClient;

    private String generateKey(Long id) {
        return "PROJ-"+id;
    }


    @Transactional
    @Override
    public IssueDTO createIssue(IssueDTO dto) {

        Issue  issue = new Issue();

        issue.setIssueTitle(dto.getIssueTitle());
        issue.setIssueDescription(dto.getIssueDescription());
        issue.setIssueType(dto.getIssueType());
        issue.setIssuePriority(dto.getIssuePriority());
        issue.setIssueStatus(dto.getIssueStatus());
        issue.setAssignedEmail(dto.getAssignedEmail());
        issue.setReporterEmail(dto.getReporterEmail());
        issue.setDueDate(dto.getDueDate());
        issue.setEpicId(dto.getEpicId());
        issue.setSprintId(dto.getSprintId());

        // 🟢 Step 1: Set a temporary key to avoid null constraint
        issue.setIssueKey("TEMP_KEY");

        if(dto.getLabels() != null && !dto.getLabels().isEmpty()) {
            for(String lableName : dto.getLabels()) {
                Label label= lableRepository.findByLabelName(lableName).orElse(null);
                if(label == null) {
                    label=new Label(lableName);
                    lableRepository.save(label);
                }
                issue.getLabels().add(label);
            }
        }

        // 🟢 Step 3: Save once (temporary key ensures no null)
        Issue savedIssue = issueRepository.save(issue);

        // Step 4: Generate real issue key and update
        savedIssue.setIssueKey(generateKey(savedIssue.getId()));
        Issue updatedIssue = issueRepository.save(savedIssue);

        //Step 5: Return DTO
        return toDTO(updatedIssue);
    }
    @Override
    public IssueDTO getById(Long id){
         Issue issue= issueRepository.findById(id)
                 .orElseThrow(()->new RuntimeException("Issue Not Found with id:"+id));
        return toDTO(issue);

   }

    @Override
    public List<IssueDTO> getByAssignedEmail(String assignedEmail) {
        return issueRepository.findAllByAssignedEmail(assignedEmail)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<IssueDTO> getBySprintId(Long sprintId) {
        return issueRepository.findBySprintId(sprintId)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<IssueDTO> getByEpicId(Long epicId) {
        return issueRepository.findByEpicId(epicId)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public IssueComment addComment(Long issueId, String authorEmail, String body) {
        Issue issue=issueRepository.findById(issueId).orElseThrow(()->new RuntimeException("Issue not found"));

        IssueComment comment=new IssueComment();
        comment.setIssueId(issueId);
//        issue.setIssueStatus(status);
        comment.setAuthorEmail(authorEmail);
        comment.setBody(body);

        issueCommentRepository.save(comment);
        return comment;
    }

    @Transactional
    @Override
    public IssueDTO updateStatus(Long id, IssueStatus toStatus,String assignedEmail) {
        Issue issue = issueRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Issue not found "));

        IssueStatus newStatus = IssueStatus.valueOf(assignedEmail);// OR  IssueStatus newStatus = IssueStatus.valueOf("DONE");
        issue.setIssueStatus(newStatus);
        issue.setUpdatedAt(LocalDateTime.now());
        IssueStatus fromStatus = issue.getIssueStatus();
        Long workFlowId = issue.getWrkFlowId();
        if(workFlowId==null){
           throw new RuntimeException("workFlow not assigned to issue");
        }

        Set<Role> userRole=userClient.getRole(assignedEmail);

        workFlowService.isTransactionAllowed(workFlowId,fromStatus,fromStatus,userRole);
        issue.setIssueStatus(toStatus);

        issueRepository.save(issue);
        addComment(id,assignedEmail,"status changed from"+fromStatus+"->"+toStatus);
        return toDTO(issue);
    }
    @Transactional
    @Override
    public Sprint createSprint(Sprint sprint) {
        return sprintRepository.save(sprint);
    }
    @Transactional
    @Override
    public List<IssueDTO> search(Map<String, String> filter) {
       if(filter.containsKey("assignee")){
           return getByAssignedEmail(filter.get("assignee"));
       }
       if (filter.containsKey("sprint")){
           Long sprintId=Long.valueOf(filter.get("sprint"));
           return issueRepository.findBySprintId(sprintId)
                   .stream()
                   .map(this::toDTO)
                   .collect(Collectors.toList());
       }
       if (filter.containsKey("status")){
           IssueStatus status = IssueStatus.valueOf(filter.get("status").toUpperCase());
           return issueRepository.findByIssueStatus(status)
                   .stream()
                   .map(this::toDTO)
                   .collect(Collectors.toList());
       }
      return issueRepository.findAll()
              .stream()
              .map(this::toDTO)
              .collect(Collectors.toList()) ;
    }


    private IssueDTO toDTO(Issue issue) {
        IssueDTO dto = new IssueDTO();

        dto.setId(issue.getId());
        dto.setIssueTitle(issue.getIssueTitle());
        dto.setIssueKey(issue.getIssueKey());
        dto.setIssueDescription(issue.getIssueDescription());
        dto.setIssueType(issue.getIssueType());
        dto.setIssuePriority(issue.getIssuePriority());
        dto.setIssueStatus(issue.getIssueStatus());
        dto.setAssignedEmail(issue.getAssignedEmail());
        dto.setReporterEmail(issue.getReporterEmail());
        dto.setEpicId(issue.getEpicId());
        dto.setSprintId(issue.getSprintId());
        dto.setCreatedAt(issue.getCreatedAt());
        dto.setDueDate(issue.getDueDate());
        dto.setUpdatedAt(issue.getUpdatedAt());

        dto.setLabels(issue.getLabels()
                .stream()
                .map(Label::getLabelName)
                .collect(Collectors.toSet()));


        return dto;
    }
}
