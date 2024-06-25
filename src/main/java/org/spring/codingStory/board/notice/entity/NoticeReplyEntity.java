package org.spring.codingStory.board.notice.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.spring.codingStory.contraint.BaseTimeEntity;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Entity
@Table(name = "notice_reply")
public class NoticeReplyEntity extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notice_reply_id")
    private Long id;

    @Column(nullable = false, length = 100)
    private String noticeReplyWriter;

    @Column(nullable = false, length = 500)
    private String noticeReplyContent;

    @JsonIgnore // ajax시 순환0참조 방지
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "notice_id")
    private NoticeEntity noticeEntity;


}
