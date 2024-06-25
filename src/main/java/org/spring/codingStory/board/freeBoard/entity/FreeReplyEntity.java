package org.spring.codingStory.board.freeBoard.entity;

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
@Table(name = "free_reply")
public class FreeReplyEntity extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "free_reply_id")
    private Long id;

    @Column(nullable = false, length = 100)
    private String freeReplyWriter;

    @Column(nullable = false, length = 500)
    private String freeReplyContent;

    @JsonIgnore // ajax시 순환0참조 방지
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "free_id")
    private FreeEntity freeEntity;


}
