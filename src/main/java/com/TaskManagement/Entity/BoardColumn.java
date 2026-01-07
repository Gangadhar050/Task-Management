package com.TaskManagement.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="board_columns",indexes = {@Index(columnList = "board_id,position")})

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BoardColumn {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch =FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private Board board;

    private String boardName;
    private String statusKey;
    private Integer position;
    private  Integer wipLimit;







}
