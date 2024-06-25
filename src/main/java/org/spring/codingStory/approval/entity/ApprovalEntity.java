package org.spring.codingStory.approval.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;
import org.spring.codingStory.approval.dto.ApprovalDto;
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
@DynamicUpdate
@Table(name = "apv_tb")
public class ApprovalEntity extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "apv_id")
    private Long id;

    @Column(nullable = false)
    private String apvTitle;

    @Column(nullable = false)
    private String apvContent;

    @Column(nullable = false)
    private int apvAttachFile;

    @Column(nullable = true)
    private String apvFnl;
    //최종 결재 승인자(사람)

    //나만 따로 추가
    @Column(nullable = true)
    private String apvCom;
    //상신 코멘트
    
//    @Column(nullable = false)
//    private String apvDiv;
//    //결재 종류
    @Column(nullable = true)
    private String date;


    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private MemberEntity memberEntity;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "apvDiv_id")
    private ApprovalDivEntity approvalDivEntity;
    // 보고서의 종류(휴무계획서, 일반 결재서)
    
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "apvStatus_id")
    private ApprovalStatusEntity approvalStatusEntity;
    //보고서의 현재 상태(진행중, 완료, 반려)

    @JsonIgnore
    @OneToMany(mappedBy = "approvalEntity"
            , fetch = FetchType.LAZY
            , cascade = CascadeType.REMOVE)
    private List<ApprovalFileEntity> approvalFileEntityList;

    //dto -> entity (파일 없을 때 보고서 작성)
    public static ApprovalEntity toWriteApv(ApprovalDto approvalDto) {

        ApprovalEntity approvalEntity = new ApprovalEntity();

        approvalEntity.setId(approvalDto.getId());
        approvalEntity.setApvTitle(approvalDto.getApvTitle());
        approvalEntity.setApvContent(approvalDto.getApvContent());
        approvalEntity.setApvAttachFile(0);
        approvalEntity.setMemberEntity(approvalDto.getMemberEntity());
        approvalEntity.setApvFnl(approvalDto.getApvFnl());
        approvalEntity.setApprovalDivEntity(approvalDto.getApprovalDivEntity());
        approvalEntity.setApprovalStatusEntity(approvalDto.getApprovalStatusEntity());
        approvalEntity.setApprovalFileEntityList(approvalDto.getApprovalFileEntityList());
        approvalEntity.setDate(approvalDto.getDate());

        return approvalEntity;
    }

    // 파일 있을 때 보고서 작성
    public static ApprovalEntity toWriteApv1(ApprovalDto approvalDto) {
        ApprovalEntity approvalEntity = new ApprovalEntity();

        approvalEntity.setId(approvalDto.getId());
        approvalEntity.setApvTitle(approvalDto.getApvTitle());
        approvalEntity.setApvContent(approvalDto.getApvContent());
        approvalEntity.setApvAttachFile(1);
        approvalEntity.setMemberEntity(approvalDto.getMemberEntity());
        approvalEntity.setApvFnl(approvalDto.getApvFnl());
        approvalEntity.setApprovalDivEntity(approvalDto.getApprovalDivEntity());
        approvalEntity.setApprovalStatusEntity(approvalDto.getApprovalStatusEntity());
        approvalEntity.setApprovalFileEntityList(approvalDto.getApprovalFileEntityList());
        approvalEntity.setDate(approvalDto.getDate());

        return approvalEntity;
    }

    //dto -> entity
    public static ApprovalEntity toApvOkEntity(ApprovalDto approvalDto) {

        ApprovalEntity approvalEntity = new ApprovalEntity();

        approvalEntity.setId(approvalDto.getId());
        approvalEntity.setApvTitle(approvalDto.getApvTitle());
        approvalEntity.setApvContent(approvalDto.getApvContent());
        if (approvalEntity.getApvAttachFile()==0){
        approvalEntity.setApvAttachFile(0);
        }else{
            approvalEntity.setApvAttachFile(1);
        }
        approvalEntity.setMemberEntity(approvalDto.getMemberEntity());
        approvalEntity.setApvFnl(approvalDto.getApvFnl());
        approvalEntity.setApprovalDivEntity(approvalDto.getApprovalDivEntity());
        approvalEntity.setApprovalStatusEntity(approvalDto.getApprovalStatusEntity());
        approvalEntity.setApprovalFileEntityList(approvalDto.getApprovalFileEntityList());
        approvalEntity.setApvCom(approvalDto.getApvCom());
        approvalEntity.setDate(approvalDto.getDate());

        return approvalEntity;
    }

    public static ApprovalEntity toApvUpdateEntity0(ApprovalDto approvalDto) {
        ApprovalEntity approvalEntity =new ApprovalEntity();

        approvalEntity.setId(approvalDto.getId());
        approvalEntity.setApvTitle(approvalDto.getApvTitle());
        approvalEntity.setApvContent(approvalDto.getApvContent());
        approvalEntity.setApvAttachFile(0);
        approvalEntity.setMemberEntity(approvalDto.getMemberEntity());
        approvalEntity.setApvFnl(approvalDto.getApvFnl());
        approvalEntity.setApprovalDivEntity(approvalDto.getApprovalDivEntity());
        approvalEntity.setApprovalStatusEntity(approvalDto.getApprovalStatusEntity());
        approvalEntity.setApprovalFileEntityList(approvalDto.getApprovalFileEntityList());
        approvalEntity.setDate(approvalDto.getDate());
        approvalEntity.setApvCom(approvalDto.getApvCom());

        return approvalEntity;
    }

    public static ApprovalEntity toApvUpdateEntity1(ApprovalDto approvalDto) {
        ApprovalEntity approvalEntity =new ApprovalEntity();
        approvalEntity.setId(approvalDto.getId());
        approvalEntity.setApvTitle(approvalDto.getApvTitle());
        approvalEntity.setApvContent(approvalDto.getApvContent());
        approvalEntity.setApvAttachFile(1);
        approvalEntity.setMemberEntity(approvalDto.getMemberEntity());
        approvalEntity.setApvFnl(approvalDto.getApvFnl());
        approvalEntity.setApprovalDivEntity(approvalDto.getApprovalDivEntity());
        approvalEntity.setApprovalStatusEntity(approvalDto.getApprovalStatusEntity());
        approvalEntity.setApprovalFileEntityList(approvalDto.getApprovalFileEntityList());
        approvalEntity.setDate(approvalDto.getDate());
        approvalEntity.setApvCom(approvalDto.getApvCom());

        return approvalEntity;
    }


}
