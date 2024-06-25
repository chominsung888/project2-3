package org.spring.codingStory.board.notice.dto;

import lombok.*;
import org.spring.codingStory.board.notice.entity.NoticeEntity;
import org.spring.codingStory.board.notice.entity.NoticeReplyEntity;

import java.time.LocalDateTime;


@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class NoticeReplyDto {
    private Long id;

    private String noticeReplyWriter;

    private String noticeReplyContent;

    private NoticeEntity noticeEntity;

    private Long noticeId;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    public static NoticeReplyDto toSelectReplyDto(NoticeReplyEntity noticeReplyEntity) {
        NoticeReplyDto noticeReplyDto=new NoticeReplyDto();
        noticeReplyDto.setId(noticeReplyEntity.getId());
        noticeReplyDto.setNoticeReplyContent(noticeReplyEntity.getNoticeReplyContent());
        noticeReplyDto.setNoticeEntity(noticeReplyEntity.getNoticeEntity());
        noticeReplyDto.setNoticeReplyWriter(noticeReplyEntity.getNoticeReplyWriter());
        noticeReplyDto.setCreateTime(noticeReplyEntity.getCreateTime());
        noticeReplyDto.setUpdateTime(noticeReplyEntity.getUpdateTime());

        return noticeReplyDto;
    }
}
