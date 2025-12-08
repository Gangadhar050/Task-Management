package com.TaskManagement.ServiceIMPL;

import com.TaskManagement.Entity.IssueLink;
import com.TaskManagement.Repository.IssueLinkRepository;
import com.TaskManagement.Service.IssueLinkService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class IssueLinkServiceImpl implements IssueLinkService  {

    @Autowired
    private IssueLinkRepository issueLinkRepository;

    @Override
    public IssueLink createLink(Long issueSourceId, Long issuetargetId, String linkType){

        IssueLink link = new IssueLink();
        link.setSourceIssueId(issueSourceId);
        link.setTargetIssueId(issuetargetId);
        link.setLinkType(linkType);
        return issueLinkRepository.save(link);
    }
    @Override
    public List<IssueLink>getLinkBySource(Long issueSourceId){
        return issueLinkRepository.findBySourceIssueId(issueSourceId);
    }
    @Override
    public List<IssueLink>getLinkByTarget(Long issuetargetId){
        return issueLinkRepository.findByTargetIssueId(issuetargetId);
    }

    @Override
    public void deleteLink(Long id) {
        Optional<IssueLink> existingLink = issueLinkRepository.findById(id);
        if (existingLink.isPresent()) {
            issueLinkRepository.deleteById(id);
        } else {
            throw new RuntimeException("Issue link not found with id: " + id);
        }
    }

}
