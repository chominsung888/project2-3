package org.spring.codingStory.board.freeBoard.dto;

import lombok.*;
import org.spring.codingStory.board.freeBoard.entity.FreeEntity;
import org.spring.codingStory.board.freeBoard.entity.FreeReplyEntity;

import java.time.LocalDateTime;


@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class FreeReplyDto {
    private Long id;

    private String freeReplyWriter;

    private String freeReplyContent;

    private FreeEntity freeEntity;


    private Long freeId;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;


    public static FreeReplyDto toSelectReplyDto(FreeReplyEntity freeReplyEntity) {
        FreeReplyDto freeReplyDto=new FreeReplyDto();
        freeReplyDto.setId(freeReplyEntity.getId());
        freeReplyDto.setFreeReplyContent(freeReplyEntity.getFreeReplyContent());
        freeReplyDto.setFreeEntity(freeReplyEntity.getFreeEntity());
        freeReplyDto.setFreeReplyWriter(freeReplyEntity.getFreeReplyWriter());
        freeReplyDto.setCreateTime(freeReplyEntity.getCreateTime());
        freeReplyDto.setUpdateTime(freeReplyEntity.getUpdateTime());

        return freeReplyDto;
    }
}
