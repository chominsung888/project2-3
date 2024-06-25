package org.spring.codingStory.approval.dto;

import lombok.*;
import org.spring.codingStory.approval.entity.ApprovalDivEntity;
import org.spring.codingStory.approval.entity.ApprovalEntity;
import org.spring.codingStory.approval.entity.ApprovalFileEntity;
import org.spring.codingStory.approval.entity.ApprovalStatusEntity;
import org.spring.codingStory.member.entity.MemberEntity;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class ApprovalDto {

    private Long id;

    private String apvTitle;

    private String apvContent;

    private int apvAttachFile;

    //dto 만 따로 추가
    private MultipartFile apvFile;

    private String apvFnl;

    private String apvStatus;

    private Long apvStatusId;

    //dto만 따로 추가
    private Long memberId;

    //dto만 따로 추가
    private String apvDiv;

    //DTO 추가
    private String apvOldFileName;

    private MemberEntity memberEntity;
    
    //나만 따로 추가
    private String apvCom;
    //상신 코멘트

    private String date;

    private ApprovalDivEntity approvalDivEntity;

    private ApprovalStatusEntity approvalStatusEntity;

    private List<ApprovalFileEntity> approvalFileEntityList;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    public static ApprovalDto toApvDtoList(ApprovalEntity approvalEntity) {
        ApprovalDto approvalDto = new ApprovalDto();

    approvalDto.setId(approvalEntity.getId());
    approvalDto.setApvTitle(approvalEntity.getApvTitle());
    approvalDto.setApvContent(approvalEntity.getApvContent());
    approvalDto.setApprovalStatusEntity(approvalEntity.getApprovalStatusEntity());
    approvalDto.setApvStatusId(approvalEntity.getApprovalStatusEntity().getId()); // apvStatusId 추가
    approvalDto.setApvAttachFile(approvalDto.getApvAttachFile());
    approvalDto.setMemberEntity(approvalEntity.getMemberEntity());
    approvalDto.setApprovalDivEntity(approvalEntity.getApprovalDivEntity());
    approvalDto.setApprovalFileEntityList(approvalEntity.getApprovalFileEntityList());
    approvalDto.setCreateTime(approvalEntity.getCreateTime());
    approvalDto.setUpdateTime(approvalEntity.getUpdateTime());
    approvalDto.setDate(approvalEntity.getDate());

    return approvalDto;

    }

    public static ApprovalDto toApvDtoDetail(ApprovalEntity approvalEntity) {
        ApprovalDto approvalDto = new ApprovalDto();
        ApprovalFileEntity approvalFileEntity = new ApprovalFileEntity();
        List<ApprovalStatusEntity> approvalStatusEntityList = new ArrayList<>();

        approvalDto.setId(approvalEntity.getId());
        approvalDto.setApvTitle(approvalEntity.getApvTitle());
        approvalDto.setApvFnl(approvalEntity.getApvFnl());
        approvalDto.setApvContent(approvalEntity.getApvContent());
        approvalDto.setApprovalStatusEntity(approvalEntity.getApprovalStatusEntity());
        approvalDto.setApvAttachFile(approvalEntity.getApvAttachFile());
        approvalDto.setApvCom(approvalEntity.getApvCom());
        approvalDto.setMemberEntity(approvalEntity.getMemberEntity());
        approvalDto.setMemberId(approvalEntity.getMemberEntity().getId());
        approvalDto.setApprovalDivEntity(approvalEntity.getApprovalDivEntity());
        if (approvalDto.getApvAttachFile() == 0) {
            approvalDto.setApvAttachFile(approvalEntity.getApvAttachFile());
        } else {
            approvalDto.setApvAttachFile(approvalEntity.getApvAttachFile());
            approvalDto.setApvOldFileName(approvalEntity.getApprovalFileEntityList().get(0).getApvOldFileName());
        }
        approvalDto.setCreateTime(approvalEntity.getCreateTime());
        approvalDto.setUpdateTime(approvalEntity.getUpdateTime());
        approvalDto.setDate(approvalEntity.getDate());

        return approvalDto;
    }

}
