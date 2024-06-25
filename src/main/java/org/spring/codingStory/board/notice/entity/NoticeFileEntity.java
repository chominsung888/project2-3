package org.spring.codingStory.board.notice.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.spring.codingStory.board.notice.dto.NoticeFileDto;
import org.spring.codingStory.contraint.BaseTimeEntity;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Entity
@Table(name = "notice_file")
public class NoticeFileEntity extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notice_file_id")
    private Long id;

    @Column(nullable = false)
    private String noticeNewFileName;

    @Column(nullable = false)
    private String noticeOldFileName;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "notice_id")
    private NoticeEntity noticeEntity;

    public static NoticeFileEntity toInsertNoticeFile(NoticeFileDto noticeFileDto) {
        NoticeFileEntity noticeFileEntity=new NoticeFileEntity();
        noticeFileEntity.setNoticeNewFileName(noticeFileDto.getNoticeNewFileName());
        noticeFileEntity.setNoticeOldFileName(noticeFileDto.getNoticeOldFileName());
        noticeFileEntity.setNoticeEntity(noticeFileDto.getNoticeEntity());

        return noticeFileEntity;
    }

}
