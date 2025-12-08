package com.TaskManagement.Repository;

import com.TaskManagement.Entity.IssueLink;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IssueLinkRepository extends JpaRepository<IssueLink,Long> {
    List<IssueLink>findBySourceIssueId( Long sourceIssueId);
    List<IssueLink>findByTargetIssueId(Long targetIssueId);
}
