package com.TaskManagement.Entity;

import com.TaskManagement.Enum.BoardType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="board")

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Board {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;
    private String boardName;
    private String projectKey;

    @Enumerated(EnumType.STRING)
    private BoardType boardType;
    private LocalDateTime createdAt=LocalDateTime.now();

    @OneToMany(mappedBy = "board",cascade = CascadeType.ALL,orphanRemoval = true)
    @OrderBy("position")
    private List<BoardColumn> columns= new ArrayList<>();
}
