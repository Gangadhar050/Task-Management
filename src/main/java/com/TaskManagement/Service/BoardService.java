package com.TaskManagement.Service;

import com.TaskManagement.Entity.Board;
import com.TaskManagement.Entity.BoardCard;
import com.TaskManagement.Entity.BoardColumn;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public interface BoardService {
    Board createBoard(Board board);

    Optional<Board> findById(Long id);

    List<BoardColumn> getByColumn(Long id);

    List<BoardCard>getCardsForColumn(Long boardId, Long columnId);

    @Transactional
    BoardCard addIssueToBoard(Long boardId, Long columnId, Long issueId);

    @Transactional
    void moveCard(Long boardId, Long cardId, Long columnId, int toPosition, String performedBy);


    @Transactional
    void recordColumn(Long boardId, Long columnId, List<Long> orderCardsIds);

    @Transactional
    void startSprint(Long sprintId);

    @Transactional
    void completeSprint(Long sprintId);


}
