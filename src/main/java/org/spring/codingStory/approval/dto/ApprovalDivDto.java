package org.spring.codingStory.approval.dto;


import lombok.*;
import org.spring.codingStory.approval.entity.ApprovalDivEntity;
import org.spring.codingStory.approval.entity.ApprovalEntity;

import java.util.List;
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class ApprovalDivDto {

    private Long id;

    private String apvDivName;

    private List<ApprovalEntity> approvalEntityList;

    public static ApprovalDivDto toApvDivList(ApprovalDivEntity approvalDivEntity) {
        ApprovalDivDto approvalDivDto=new ApprovalDivDto();

        approvalDivDto.setId(approvalDivEntity.getId());
        approvalDivDto.setApvDivName(approvalDivEntity.getApvDivName());
        approvalDivDto.setApprovalEntityList(approvalDivEntity.getApprovalEntityList());

        return approvalDivDto;
    }
}
