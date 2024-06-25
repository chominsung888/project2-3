package org.spring.codingStory.board.notice.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.spring.codingStory.board.notice.dto.NoticeDto;
import org.spring.codingStory.contraint.BaseTimeEntity;
import org.spring.codingStory.member.entity.MemberEntity;

import javax.persistence.*;
import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Entity
@Table(name = "notice")
public class NoticeEntity extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notice_id")
    private Long id;

    @Column(nullable = false)
    public String category;

    @Column(nullable = false)
    private String noticeTitle;

    @Column(nullable = false)
    private String noticeContent;

    @Column(columnDefinition = "integer default 0", nullable = false)
    private int noticeHit;

    @Column(nullable = false)
    private String noticeWriter;

    @Column(nullable = false)
    private int noticeAttachFile;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private MemberEntity memberEntity;

    @JsonIgnore
    @OneToMany(mappedBy = "noticeEntity"
            , fetch = FetchType.LAZY
            , cascade = CascadeType.REMOVE)
    private List<NoticeFileEntity> noticeFileEntityList;

    @JsonIgnore // ajax시 순환참조 방지
    @OneToMany(mappedBy = "noticeEntity"
            , fetch = FetchType.LAZY
            , cascade = CascadeType.REMOVE)
    private List<NoticeReplyEntity> noticeReplyEntityList;

    public static NoticeEntity toInsertNoticeEntity(NoticeDto noticeDto) {
        NoticeEntity noticeEntity=new NoticeEntity();
        noticeEntity.setId(noticeDto.getId());
        noticeEntity.setNoticeContent(noticeDto.getNoticeContent());
        noticeEntity.setNoticeTitle(noticeDto.getNoticeTitle());
        noticeEntity.setCategory(noticeDto.getCategory());
        noticeEntity.setNoticeWriter(noticeDto.getNoticeWriter());
        noticeEntity.setNoticeHit(0);
        noticeEntity.setNoticeAttachFile(0);
        noticeEntity.setMemberEntity(noticeDto.getMemberEntity());

        return noticeEntity;

    }

    public static NoticeEntity toInsertFileNoticeEntity(NoticeDto noticeDto) {

        NoticeEntity noticeEntity=new NoticeEntity();
        noticeEntity.setId(noticeDto.getId());
        noticeEntity.setNoticeContent(noticeDto.getNoticeContent());
        noticeEntity.setNoticeTitle(noticeDto.getNoticeTitle());
        noticeEntity.setCategory(noticeDto.getCategory());
        noticeEntity.setNoticeWriter(noticeDto.getNoticeWriter());
        noticeEntity.setNoticeHit(0);
        noticeEntity.setNoticeAttachFile(1);
        noticeEntity.setMemberEntity(noticeDto.getMemberEntity());

        return noticeEntity;

    }

    public static NoticeEntity toUpdateNoticeEntity(NoticeDto noticeDto) {
        NoticeEntity noticeEntity=new NoticeEntity();
        noticeEntity.setId(noticeDto.getId());
        noticeEntity.setNoticeContent(noticeDto.getNoticeContent());
        noticeEntity.setNoticeTitle(noticeDto.getNoticeTitle());
        noticeEntity.setCategory(noticeDto.getCategory());
        noticeEntity.setNoticeWriter(noticeDto.getNoticeWriter());
        noticeEntity.setNoticeAttachFile(0);
        noticeEntity.setMemberEntity(noticeDto.getMemberEntity());

        return noticeEntity;

    }
    public static NoticeEntity toUpdateFileNoticeEntity(NoticeDto noticeDto) {
        NoticeEntity noticeEntity=new NoticeEntity();
        noticeEntity.setId(noticeDto.getId());
        noticeEntity.setNoticeContent(noticeDto.getNoticeContent());
        noticeEntity.setNoticeTitle(noticeDto.getNoticeTitle());
        noticeEntity.setCategory(noticeDto.getCategory());
        noticeEntity.setNoticeWriter(noticeDto.getNoticeWriter());
        noticeEntity.setNoticeAttachFile(1);
        noticeEntity.setMemberEntity(noticeDto.getMemberEntity());

        return noticeEntity;
    }


}
