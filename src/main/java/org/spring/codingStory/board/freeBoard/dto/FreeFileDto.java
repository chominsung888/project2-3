package org.spring.codingStory.board.freeBoard.dto;

import lombok.*;
import org.spring.codingStory.board.freeBoard.entity.FreeEntity;

import java.time.LocalDateTime;


@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class FreeFileDto {
    private Long id;

    private String freeNewFileName;

    private String freeOldFileName;

    private FreeEntity freeEntity;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;
}
