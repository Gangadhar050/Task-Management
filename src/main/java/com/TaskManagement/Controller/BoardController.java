package com.TaskManagement.Controller;

import com.TaskManagement.Entity.Board;
import com.TaskManagement.Entity.BoardCard;
import com.TaskManagement.Entity.BoardColumn;
import com.TaskManagement.Service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/boards")
@RequiredArgsConstructor
public class BoardController {

    @Autowired
    private BoardService boardService;

    @PostMapping("/create_board")
    public ResponseEntity<Board> createBoard(@RequestBody Board board) {
        return ResponseEntity.ok(boardService.createBoard(board));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Board>> getById(@PathVariable Long id) {
        return ResponseEntity.ok(boardService.findById(id));
    }

    @GetMapping("/{id}/columns")
    public ResponseEntity<List<BoardColumn>> getBycolumns(@PathVariable Long id) {
        return ResponseEntity.ok(boardService.getByColumn(id));
    }

    @PostMapping("/{id}/columns")
    public ResponseEntity<Board> addColumn(@PathVariable Long id, @RequestBody BoardColumn boardColumn) {
        boardColumn.setBoard(boardService.findById(id)
                .orElseThrow(() -> new RuntimeException("Board Not found")));
        return ResponseEntity.ok(boardService.createBoard(boardColumn.getBoard()));
    }

    @PostMapping("/{id}/cards")
    public ResponseEntity<BoardCard> addCards(@PathVariable Long id, @RequestBody Map<String, Object> body) {

        Long columnId = Long.valueOf(String.valueOf(body.get("ColumnId")));
        Long issueId = Long.valueOf(String.valueOf(body.get("issueId")));
        return ResponseEntity.ok(boardService.addIssueToBoard(id, columnId, issueId));
    }

    @PostMapping("/{id}/cards/{cardId}/move")
    public ResponseEntity<String> moveCard(@PathVariable Long id,
                                           @PathVariable Long cardId,
                                           @RequestBody Map<String, Object> body,
                                           @RequestHeader(value = "Ex_user_Email", required = false) String user) {
        Long toColumnId = Long.valueOf(String.valueOf(body.get("toColumnId")));
        int toPosition = Integer.parseInt(String.valueOf(body.get("toPosition")));

        boardService.moveCard(cardId, cardId, toColumnId, toPosition, user);
        return ResponseEntity.ok("Moved");
    }

    @PostMapping("/{id}/columns/{columnId}/records")
    public ResponseEntity<String> recordColumn(@PathVariable Long id, @PathVariable Long columnId, @RequestBody List<Long> orderCardsIds) {
        boardService.recordColumn(id, columnId, orderCardsIds);
        return ResponseEntity.ok("Recorded");
    }

    @PostMapping("/sprints/{sprintId}/start")
    public ResponseEntity<String> startSprint(@PathVariable Long id, @PathVariable Long sprintId) {
        boardService.startSprint(sprintId);
        return ResponseEntity.ok("Sprint got Started");
    }

    @PostMapping("/sprints/{sprintId}/complete")
    public ResponseEntity<String> completeSprint(@PathVariable Long id, @PathVariable Long sprintId) {
        boardService.completeSprint(sprintId);
        return ResponseEntity.ok("Sprint got Completed");
    }
}