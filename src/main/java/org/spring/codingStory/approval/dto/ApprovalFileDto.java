package org.spring.codingStory.approval.dto;

import lombok.*;
import org.spring.codingStory.approval.entity.ApprovalEntity;



@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class ApprovalFileDto {

    private Long id;

    private String apvNewFileName;

    private String apvOldFileName;

    private ApprovalEntity approvalEntity;
}
