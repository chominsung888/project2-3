package org.spring.codingStory.board.notice.dto;

import lombok.*;
import org.spring.codingStory.board.notice.entity.NoticeEntity;
import org.spring.codingStory.board.notice.entity.NoticeFileEntity;
import org.spring.codingStory.board.notice.entity.NoticeReplyEntity;
import org.spring.codingStory.member.entity.MemberEntity;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class NoticeDto {

    private Long id;

    public String category;

    private String noticeTitle;

    private String noticeContent;

    private int noticeHit;

    private int noticeAttachFile;

    private String noticeWriter;

    private MultipartFile boardFile;

    private MemberEntity memberEntity;

    private List<NoticeFileEntity> noticeFileEntityList;

    private List<NoticeReplyEntity> noticeReplyEntityList;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    private Long memberId;

    private String newFileName;

    private String oldFileName;

    public static NoticeDto toNoticeDto(NoticeEntity noticeEntity) {
        NoticeDto boardDto = new NoticeDto();

        boardDto.setId(noticeEntity.getId());
        boardDto.setNoticeContent(noticeEntity.getNoticeContent());
        boardDto.setNoticeTitle(noticeEntity.getNoticeTitle());
        boardDto.setCategory(noticeEntity.getCategory());
        boardDto.setNoticeWriter(noticeEntity.getMemberEntity().getName()); // 작성말고 회원의 이름정보만 받아오기?
        boardDto.setNoticeHit(noticeEntity.getNoticeHit());
        boardDto.setNoticeAttachFile(noticeEntity.getNoticeAttachFile());
        boardDto.setCreateTime(noticeEntity.getCreateTime());
        boardDto.setUpdateTime(noticeEntity.getUpdateTime());
        boardDto.setMemberEntity(noticeEntity.getMemberEntity());
        if(noticeEntity.getNoticeAttachFile()==0) {
            //파일0
            boardDto.setNoticeAttachFile(noticeEntity.getNoticeAttachFile());
        }else{
            boardDto.setNoticeAttachFile(noticeEntity.getNoticeAttachFile());
            //새파일
            boardDto.setNewFileName(noticeEntity.getNoticeFileEntityList().get(0).getNoticeNewFileName());
            //원본파일
            boardDto.setOldFileName(noticeEntity.getNoticeFileEntityList().get(0).getNoticeOldFileName());
        }
        return boardDto;

    }
}
