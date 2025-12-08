package com.TaskManagement.Service;

import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public interface IntegrationService {
    void handelCommitMessage(String commitMessage,String auther);

    void handelPullRequest(String title, String author);

    void processGithubEvent(String event, Map<String, Object> payload);

    void extractIssueKeyAndUpdate(String commitMsg, String user);

    void extractIssueKeyAndMoveInProgress(String title, String user);

    // Jenkin
    void processJenkinEvent(Map<String, Object> body);

    void processDockerEvent(Map<String, Object> payload);

    String extractIssueKey(String text);
}
