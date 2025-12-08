package com.TaskManagement.Service;

import com.TaskManagement.Entity.IssueLink;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface IssueLinkService {
    IssueLink createLink(Long issueSourceId, Long issuetargetId, String linkType);

    List<IssueLink> getLinkBySource(Long issueSourceId);

    List<IssueLink>getLinkByTarget(Long issuetargetId);

    void deleteLink(Long id);
}
