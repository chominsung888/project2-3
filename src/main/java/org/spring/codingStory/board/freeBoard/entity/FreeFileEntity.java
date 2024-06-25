package org.spring.codingStory.board.freeBoard.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.spring.codingStory.board.freeBoard.dto.FreeFileDto;
import org.spring.codingStory.contraint.BaseTimeEntity;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Entity
@Table(name = "freeBoard_file")
public class FreeFileEntity extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "free_file_id")
    private Long id;

    @Column(nullable = false)
    private String freeNewFileName;

    @Column(nullable = false)
    private String freeOldFileName;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "free_id")
    private FreeEntity freeEntity;

    public static FreeFileEntity toInsertFreeFile(FreeFileDto freeFileDto) {
        FreeFileEntity freeFileEntity=new FreeFileEntity();
        freeFileEntity.setFreeNewFileName(freeFileDto.getFreeNewFileName());
        freeFileEntity.setFreeOldFileName(freeFileDto.getFreeOldFileName());
        freeFileEntity.setFreeEntity(freeFileDto.getFreeEntity());

        return freeFileEntity;
    }

}
