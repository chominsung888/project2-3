package org.spring.codingStory.board.freeBoard.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.spring.codingStory.board.freeBoard.dto.FreeDto;
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
@Table(name = "freeBoard")
public class FreeEntity extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "free_id")
    private Long id;

    @Column(nullable = false)
    public String category;

    @Column(nullable = false)
    private String freeTitle;

    @Column(nullable = false)
    private String freeContent;

    @Column(nullable = false)
    private String freeWriter;

    @Column(columnDefinition = "integer default 0", nullable = false)
    private int freeHit;

    @Column(nullable = false)
    private int freeAttachFile;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private MemberEntity memberEntity;

    @JsonIgnore
    @OneToMany(mappedBy = "freeEntity"
            , fetch = FetchType.LAZY
            , cascade = CascadeType.REMOVE)
    private List<FreeFileEntity> freeFileEntityList;

    @JsonIgnore // ajax시 순환참조 방지
    @OneToMany(mappedBy = "freeEntity"
            , fetch = FetchType.LAZY
            , cascade = CascadeType.REMOVE)
     private List<FreeReplyEntity> freeReplyEntityList;


    public static FreeEntity toInsertFreeEntity(FreeDto freeDto) {
        FreeEntity freeEntity=new FreeEntity();
        freeEntity.setId(freeDto.getId());
        freeEntity.setFreeContent(freeDto.getFreeContent());
        freeEntity.setFreeTitle(freeDto.getFreeTitle());
        freeEntity.setCategory(freeDto.getCategory());
        freeEntity.setFreeWriter(freeDto.getFreeWriter());
        freeEntity.setFreeHit(0);
        freeEntity.setFreeAttachFile(0);
        freeEntity.setMemberEntity(freeDto.getMemberEntity());


        return freeEntity;
    }


    public static FreeEntity toInsertFileFreeEntity(FreeDto freeDto) {
        FreeEntity freeEntity=new FreeEntity();
        freeEntity.setId(freeDto.getId());
        freeEntity.setFreeContent(freeDto.getFreeContent());
        freeEntity.setFreeTitle(freeDto.getFreeTitle());
        freeEntity.setCategory(freeDto.getCategory());
        freeEntity.setFreeWriter(freeDto.getFreeWriter());
        freeEntity.setFreeHit(0);
        freeEntity.setFreeAttachFile(1);
        freeEntity.setMemberEntity(freeDto.getMemberEntity());


        return freeEntity;
    }

    public static FreeEntity toUpdateFreeEntity(FreeDto freeDto) {
        FreeEntity freeEntity=new FreeEntity();
        freeEntity.setId(freeDto.getId());
        freeEntity.setFreeContent(freeDto.getFreeContent());
        freeEntity.setFreeTitle(freeDto.getFreeTitle());
        freeEntity.setCategory(freeDto.getCategory());
        freeEntity.setFreeWriter(freeDto.getFreeWriter());
        freeEntity.setFreeAttachFile(0);
        freeEntity.setMemberEntity(freeDto.getMemberEntity());


        return freeEntity;
    }
    public static FreeEntity toUpdateFileFreeEntity(FreeDto freeDto) {
        FreeEntity freeEntity=new FreeEntity();
        freeEntity.setId(freeDto.getId());
        freeEntity.setFreeContent(freeDto.getFreeContent());
        freeEntity.setFreeTitle(freeDto.getFreeTitle());
        freeEntity.setCategory(freeDto.getCategory());
        freeEntity.setFreeWriter(freeDto.getFreeWriter());
        freeEntity.setFreeAttachFile(1);
        freeEntity.setMemberEntity(freeDto.getMemberEntity());


        return freeEntity;
    }
}
