package com.TaskManagement.ServiceIMPL;

import com.TaskManagement.Client.IssueClient;
import com.TaskManagement.Enum.IssueStatus;
import com.TaskManagement.Service.IntegrationService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class IntegrationServiceImpl implements IntegrationService {

@Autowired
private IssueClient issueClient;


//In this we have three features
// 1.handelCommitMessage,
// 2.handelPullRequest
// 3.processGithubEvent(extractIssueKeyAndUpdate,extractIssueKeyAndMoveInProgress internally connected)
// Jenkin is seperate feature


    @Override
    public void handelCommitMessage(String commitMessage, String author) {
        String regex = "([A-Z]+-//d+)";
        var matcher = java.util.regex.Pattern.compile(regex).matcher(commitMessage);
        if (matcher.find()) {
            String issueKey = matcher.group(1);
            Long issueId = Long.valueOf(issueKey.split("-")[1]);
            // Here you can add logic to update the issue status or add a comment
            // For example:
            // issueService.addComment(issueId, "Commit by " + author + ": " + commitMessage);
            // issueService.updateStatus(issueId, IssueStatus.RESOLVED, author);
            issueClient.status(issueId,"Done",author);
            issueClient.commit(issueId,author,"Automitically close By"+commitMessage);
        }
    }

    @Override
    public void handelPullRequest(String title, String author) {
        String regex = "([A-Z]+-//d+)";
        Matcher matcher = java.util.regex.Pattern.compile(regex).matcher(title);

        if (matcher.find()) {
            String issueKey = matcher.group(1);
            Long issueId = Long.valueOf(issueKey.split("-")[1]);

            issueClient.status(issueId, String.valueOf(IssueStatus.IN_PROGRESS), issueKey);
            issueClient.commit(issueId, author, "pull request opened " + title);
        }
    }

    @Override
    public void processGithubEvent(String event, Map<String, Object> payload) {
        if (event.equals("PUSH")) {
            List<Map<String, Object>> commit = (List<Map<String, Object>>) payload.get("commits");

            Object commitObj = payload.get("commits");
            if (!(commitObj instanceof List)){
                System.out.println("No commits found");
                return;
            }

           List<?> commits=(List<?>) commitObj;

            for (Object obj : commits) {

                if (!(obj instanceof Map)) continue;

                    Map<String, Object> commitMap = (Map<String, Object>) obj;

                    String message = (String) commitMap.get("message");
                    Map<String, Object> authorMap = (Map<String, Object>) commitMap.get("author");
                    String author =authorMap !=null? (String) authorMap.get("name"):"Unknown";
                    extractIssueKeyAndUpdate(message,author);
//                     handelCommitMessage(message, author);
            }
        }
        // You can add more event types and their handlers here
        else if ("Pull Request".equals(event)){
            Object prObj= payload.get("Pull Request");

            if (!(prObj instanceof Map)){
                System.out.println("No Pull Request FOund in Paylod");
            }

            Map<String,Object> prObj1 = (Map<String, Object>)prObj;
            String title=(String) prObj1.get("title");
            Map<String, Object> userMap = (Map<String, Object>) prObj1.getOrDefault("user",Map.class);
            String auther= (String)userMap.getOrDefault("login","Unknown");

        }
    }

    @Override
    public void extractIssueKeyAndUpdate(String commitMsg, String user){

        String regex="([A-Z]+-\\d+)";
        Matcher matcher= java.util.regex.Pattern.compile(regex).matcher(commitMsg);

        if (matcher.find()) {
            Long issueId = Long.parseLong(matcher.group(1).split("-")[1]);

            issueClient.status(issueId, String.valueOf(IssueStatus.DONE),user);
            issueClient.commit(issueId, user, "Automitically closed by commit message: " + commitMsg);

        }
    }

    @Override
    public void extractIssueKeyAndMoveInProgress(String title, String user){
        String regex="([A-Z]+-\\d+)";
        Matcher matcher = Pattern.compile(regex).matcher(title);

        if (matcher.find()) {
            Long issueId = Long.parseLong(matcher.group(1).split("-")[1]);

            issueClient.status(issueId, String.valueOf(IssueStatus.IN_PROGRESS),user);
            issueClient.commit(issueId, user, "Pull request opened: " + title);

        }
    }

   // Jenkin
   @Override
   public void processJenkinEvent(Map<String, Object> body){

        String jobName=(String) body.get("name");
        String result=(String) body.get("result");
        String log= (String) body.get("log");

        Matcher matcher = Pattern.compile("([A-Z]+-\\d+)").matcher(jobName);

        if (matcher.find()){
            Long issueId = Long.parseLong(matcher.group(1).split("_")[1]);

            if (result.equals("FAILURE")){
                issueClient.commit(issueId,"jenkins","Build Failure\n''''\n"+log+"\n''''");
            }
        }

    }

    @Override
    public void processDockerEvent(Map<String, Object> payload){

        String status= (String) payload.get("Status");
        String image= (String) payload.get("from");

        Map<String,Object>actor = (Map<String,Object>)payload.get("Actor");
        Map<String,Object>attributes = actor !=null? (Map<String, Object>)actor.get("Attribute"):null;

        String containerName=attributes !=null? (String) attributes.get("Name"):"";
        String imageName=attributes !=null? (String)attributes.get("image"):image;

        String issueKey =extractIssueKey(containerName+""+imageName);

        if(issueKey == null){
            System.out.println("No IssueKey founf=d in Docker");
            return;
        }
        Long issueid = Long.parseLong(issueKey.split("_")[1]);

        switch (status.toLowerCase()){

            case "start":

                issueClient.updateStatus(issueid,IssueStatus.DEPLOYMENT,"Docker");
                issueClient.addCommit(issueid,"Docker","container creashed | Image:"+imageName);
                break;

            case "die":

                issueClient.updateStatus(issueid,IssueStatus.BLOCK,"Docker");
                issueClient.addCommit(issueid,"Docker","container creashed | Image:"+imageName);
                break;

            case "pull":

                issueClient.addCommit(issueid,"Docker","Image pulled:"+imageName);
                break;

            case "build":
                issueClient.addCommit(issueid,"docker","Docker image build :"+imageName);
                break;

            default:

                issueClient.addCommit(issueid,"Docker","Docker event"+status+"image"+imageName);
        }

    }


    @Override
    public String extractIssueKey(String text){
        if (text == null) return  null;

        Matcher matcher = Pattern.compile("([A-Z]+-\\d+)").matcher(text);
        if (matcher.find()){
            return matcher.group(1);
        }
          return null;

    }
}
