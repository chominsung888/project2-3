package org.spring.codingStory.board.freeBoard.dto;

import lombok.*;
import org.spring.codingStory.board.freeBoard.entity.FreeEntity;
import org.spring.codingStory.board.freeBoard.entity.FreeFileEntity;
import org.spring.codingStory.board.freeBoard.entity.FreeReplyEntity;
import org.spring.codingStory.member.entity.MemberEntity;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class FreeDto {

    private Long id;

    public String category;

    private String freeTitle;

    private String freeContent;

    private int freeHit;

    private int freeAttachFile;

    private String freeWriter;

    private MultipartFile boardFile;

    private MemberEntity memberEntity;

    private List<FreeFileEntity> freeFileEntityList;

    private List<FreeReplyEntity> freeReplyEntityList;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    private Long memberId;

    private String newFileName;

    private String oldFileName;

    public static FreeDto toFreeDto(FreeEntity freeEntity) {
        FreeDto boardDto = new FreeDto();

        boardDto.setId(freeEntity.getId());
        boardDto.setFreeContent(freeEntity.getFreeContent());
        boardDto.setFreeTitle(freeEntity.getFreeTitle());
        boardDto.setCategory(freeEntity.getCategory());
        boardDto.setFreeWriter(freeEntity.getMemberEntity().getName()); // 작성말고 회원의 이름정보만 받아오기?
        boardDto.setFreeHit(freeEntity.getFreeHit());
        boardDto.setFreeAttachFile(freeEntity.getFreeAttachFile());
        boardDto.setCreateTime(freeEntity.getCreateTime());
        boardDto.setUpdateTime(freeEntity.getUpdateTime());
        boardDto.setMemberEntity(freeEntity.getMemberEntity());
        if(freeEntity.getFreeAttachFile()==0) {
            //파일0
            boardDto.setFreeAttachFile(freeEntity.getFreeAttachFile());
        }else{
            boardDto.setFreeAttachFile(freeEntity.getFreeAttachFile());
            //새파일
            boardDto.setNewFileName(freeEntity.getFreeFileEntityList().get(0).getFreeNewFileName());
            //원본파일
            boardDto.setOldFileName(freeEntity.getFreeFileEntityList().get(0).getFreeOldFileName());
        }
        return boardDto;

    }



}
