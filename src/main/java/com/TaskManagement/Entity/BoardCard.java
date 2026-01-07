package com.TaskManagement.Entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="board_cards" ,indexes = {@Index(columnList = "board_id,column_id,position")})

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class  BoardCard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long issueId;
    private Long boardId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "column_id", nullable = false)
    private BoardColumn boardColumn;

    private  Integer position;

}
