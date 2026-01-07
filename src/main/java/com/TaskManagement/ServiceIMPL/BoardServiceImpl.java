package com.TaskManagement.ServiceIMPL;


import com.TaskManagement.Entity.Board;
import com.TaskManagement.Entity.BoardCard;
import com.TaskManagement.Entity.BoardColumn;
import com.TaskManagement.Entity.Issue;
import com.TaskManagement.Repository.BoardCardRepository;
import com.TaskManagement.Repository.BoardColumnRepository;
import com.TaskManagement.Repository.BoardRepository;
import com.TaskManagement.Repository.IssueRepository;
import com.TaskManagement.Service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private BoardCardRepository boardCardRepository;

    @Autowired
    private BoardColumnRepository boardColumnRepository;


    @Autowired
    private IssueRepository issueRepository;
    @Override
    public Board createBoard(Board board){
        return boardRepository.save(board);
    }

    @Override
    public Optional<Board> findById(Long id){
        return boardRepository.findById(id);
    }

    @Override
    public List<BoardColumn>getByColumn(Long id){
        return boardColumnRepository.findByBoardIdOrderByPosition(id);
    }

    @Override
    public List<BoardCard>getCardsForColumn(Long boardId, Long columnId){
        return boardCardRepository.findByBoardIdAndBoardColumn_IdOrderByPosition(boardId, columnId);
    }
    @Transactional
    @Override
    public BoardCard addIssueToBoard(Long boardId, Long columnId, Long issueId){
        Issue issue= issueRepository.findById(issueId)
                .orElseThrow(() -> new RuntimeException("Issue not found with id: " + issueId));

        boardCardRepository.findByIssueId(issueId)
                .ifPresent(boardCardRepository::delete);

        BoardColumn column= boardColumnRepository.findById(columnId)
                .orElseThrow(() -> new RuntimeException("Board column not found with id: " + columnId));

        if (column.getWipLimit() !=null && column.getWipLimit()>0) {

            long count = boardCardRepository.countByBoardIdAndBoardColumnId(boardId, columnId);
            if(count >= column.getWipLimit()){
                throw new RuntimeException("Cannot add issue to column. WIP limit reached for column id: " + column.getBoardName());
            }
        }

        List<BoardCard>existing= boardCardRepository.findByBoardIdAndBoardColumn_IdOrderByPosition(boardId,columnId);
        int position= existing.size()+1;

        BoardCard card= new BoardCard();
        card.setBoardId(boardId);
        card.setBoardColumn(column);;
        card.setIssueId(issueId);
        card.setPosition(position);

        card= boardCardRepository.save(card);

        if(column.getStatusKey()!=null){
            issue.setIssueStatus(Enum.valueOf(com.TaskManagement.Enum.IssueStatus.class,column.getStatusKey()));
            issueRepository.save(issue);

        }
        return card;
    }

    @Transactional
    @Override
    public void moveCard(Long boardId, Long cardId, Long columnId, int toPosition, String performedBy){

        BoardCard card= boardCardRepository.findById(cardId)
                .orElseThrow(() -> new RuntimeException("Board card not found with card id: " + cardId));

        BoardColumn from = card.getBoardColumn();
        BoardColumn to= boardColumnRepository.findById(columnId)
                .orElseThrow(() -> new RuntimeException("Board column not found with id: " + columnId));

        if(to.getWipLimit()!=null && to.getWipLimit()>0){

            long count= boardCardRepository.countByBoardIdAndBoardColumnId(boardId, columnId);
            if(!Objects.equals(from.getId(),to.getId()) && count >= to.getWipLimit()){
                throw new RuntimeException(" WIP limit exceeded for column id: " + to.getBoardName());
            }
        }

        List<BoardCard>fromList= boardCardRepository.findByBoardIdAndBoardColumn_IdOrderByPosition(boardId,columnId);
        for(BoardCard bc : fromList){
            if(bc.getPosition() > card.getPosition()){
                bc.setPosition(bc.getPosition()-1);
                boardCardRepository.save(bc);
            }
        }
        List<BoardCard>toList= boardCardRepository.findByBoardIdAndBoardColumn_IdOrderByPosition(boardId,to.getId());
        for (BoardCard bc : toList){
            if(bc.getPosition() >= toPosition){
                bc.setPosition(bc.getPosition()+1);
                boardCardRepository.save(bc);
            }
        }

        card.setBoardColumn(to);
        card.setPosition(toPosition);
        boardCardRepository.save(card);


        Issue issue = issueRepository.findById(card.getIssueId())
                .orElseThrow(() -> new RuntimeException("Issue not found"));
        if(to.getStatusKey()!= null){
            issue.setIssueStatus(Enum.valueOf(com.TaskManagement.Enum.IssueStatus.class,to.getStatusKey()));

            issueRepository.save(issue);
        }
    }

    @Transactional
    @Override
    public void recordColumn(Long boardId, Long columnId, List<Long> orderCardsIds){
        int pos = 0;
        for(Long colId : orderCardsIds){
           BoardCard bd = boardCardRepository.findById(colId).orElseThrow(()->new RuntimeException("card not found"));
           bd.setPosition(pos++);
           boardCardRepository.save(bd);
        }
    }
    @Transactional
    @Override
    public void startSprint(Long sprintId){

    }

    @Transactional
    @Override
    public void completeSprint(Long sprintId){

    }
}
