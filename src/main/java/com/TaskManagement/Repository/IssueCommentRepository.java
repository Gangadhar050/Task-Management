package com.TaskManagement.Repository;

import com.TaskManagement.Entity.IssueComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface IssueCommentRepository extends JpaRepository<IssueComment, Long> {

    List<IssueComment> findAllByIssueIdOrderByCreatedAt(Long issueId);
    
}
