package org.spring.codingStory.approval.dto;

import lombok.*;
import org.spring.codingStory.approval.entity.ApprovalEntity;
import org.spring.codingStory.approval.entity.ApprovalStatusEntity;

import java.util.List;
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class ApprovalStatusDto {

    private Long id;

    private String apvStatus;

    private List<ApprovalEntity> approvalEntityList;

    public static ApprovalStatusDto toApvStatusList(ApprovalStatusEntity approvalStatusEntity) {
        ApprovalStatusDto approvalStatusDto=new ApprovalStatusDto();

        approvalStatusDto.setId(approvalStatusEntity.getId());
        approvalStatusDto.setApvStatus(approvalStatusEntity.getApvStatus());
        approvalStatusDto.setApprovalEntityList(approvalStatusEntity.getApprovalEntityList());
        return approvalStatusDto;
   }
}
