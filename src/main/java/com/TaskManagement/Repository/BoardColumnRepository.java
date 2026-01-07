package com.TaskManagement.Repository;

import com.TaskManagement.Entity.BoardColumn;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BoardColumnRepository extends JpaRepository<BoardColumn, Long> {
    List<BoardColumn> findByBoardIdOrderByPosition(Long boardId);

}
