package com.TaskManagement.Repository;

import com.TaskManagement.Entity.BoardCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface BoardCardRepository extends JpaRepository<BoardCard,Long> {
    List<BoardCard> findByBoardIdAndBoardColumn_IdOrderByPosition(
            Long boardId,
            Long boardColumnId
    );    Optional<BoardCard> findByIssueId(Long issueId);
    Long countByBoardIdAndBoardColumnId(Long boardId, Long columnId);}
