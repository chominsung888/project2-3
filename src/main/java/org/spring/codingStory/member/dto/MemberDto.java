package org.spring.codingStory.member.dto;

import lombok.*;
import org.spring.codingStory.approval.entity.ApprovalEntity;
import org.spring.codingStory.department.entity.DepartmentEntity;
import org.spring.codingStory.mRank.entity.RankEntity;
import org.spring.codingStory.member.entity.MemberEntity;
import org.spring.codingStory.member.entity.MemberFileEntity;
import org.spring.codingStory.member.role.Role;
import org.spring.codingStory.pay.entity.PayEntity;
import org.spring.codingStory.payment.entity.PaymentEntity;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class MemberDto {

    private Long id;

    @NotBlank(message = "이메일을 입력해주세요")
    private String userEmail;
    @NotBlank
    private String userPw;
    @NotBlank
    private String name;

    private String department;

    private String mRank;

    private String address;

    private String phoneNumber;

    private Role role;

    private String memberFileName;

    private MultipartFile memberFile;

    private String memberNewFileName;

    private String memberOldFileName;

    private int memberAttachFile;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    private DepartmentEntity departmentEntity;

    private RankEntity rankEntity;

    private List<PayEntity> PayEntityList;

    private List<MemberFileEntity> memberFileEntityList;

    private PaymentEntity paymentEntity;

    public static MemberDto toSelectMemberDto(MemberEntity member) {
        MemberDto memberDto=new MemberDto();
        memberDto.setId(member.getId());
        memberDto.setUserEmail(member.getUserEmail());
        memberDto.setName(member.getName());
        memberDto.setDepartment(member.getDepartment());
        memberDto.setMRank(member.getMRank());
        memberDto.setAddress(member.getAddress());
        memberDto.setPhoneNumber(member.getPhoneNumber());
        memberDto.setRole(member.getRole());
        memberDto.setCreateTime(member.getCreateTime());
        memberDto.setUpdateTime(member.getUpdateTime());

        if (member.getMemberAttachFile() == 0) {
            memberDto.setMemberAttachFile(member.getMemberAttachFile());
        } else {
            memberDto.setMemberAttachFile(member.getMemberAttachFile());
            memberDto.setMemberNewFileName(member.getMemberFileEntityList().get(0).getMemberNewFileName());
            memberDto.setMemberOldFileName(member.getMemberFileEntityList().get(0).getMemberOldFileName());
        }

        return memberDto;

    }


    public static MemberDto toSelectMemberTest(MemberEntity memberEntity) {
        MemberDto memberDto=new MemberDto();
        memberDto.setId(memberEntity.getId());
        memberDto.setUserEmail(memberEntity.getUserEmail());
        memberDto.setName(memberEntity.getName());
        memberDto.setDepartment(memberEntity.getDepartment());
        memberDto.setMRank(memberEntity.getMRank());
        memberDto.setAddress(memberEntity.getAddress());
        memberDto.setPhoneNumber(memberEntity.getPhoneNumber());
        memberDto.setRole(memberEntity.getRole());
        memberDto.setCreateTime(memberEntity.getCreateTime());
        memberDto.setUpdateTime(memberEntity.getUpdateTime());
        memberDto.setPayEntityList(memberEntity.getPayEntityList());
        memberDto.setPaymentEntity(memberEntity.getPaymentEntity());

        if (memberEntity.getMemberAttachFile() == 0) {
            memberDto.setMemberAttachFile(memberEntity.getMemberAttachFile());
        } else {
            memberDto.setMemberAttachFile(memberEntity.getMemberAttachFile());
            memberDto.setMemberNewFileName(memberEntity.getMemberFileEntityList().get(0).getMemberNewFileName());
            memberDto.setMemberOldFileName(memberEntity.getMemberFileEntityList().get(0).getMemberOldFileName());
        }

        return memberDto;

    }

    public static MemberDto toSelectMemberDto1(MemberEntity member) {
        MemberDto memberDto=new MemberDto();
        memberDto.setId(member.getId());
        memberDto.setUserEmail(member.getUserEmail());
        memberDto.setName(member.getName());
        memberDto.setDepartment(member.getDepartment());
        memberDto.setMRank(member.getMRank());
        memberDto.setAddress(member.getAddress());
        memberDto.setPhoneNumber(member.getPhoneNumber());
        memberDto.setRole(member.getRole());
        memberDto.setCreateTime(member.getCreateTime());
        memberDto.setUpdateTime(member.getUpdateTime());

        if (member.getMemberAttachFile() == 0) {
            memberDto.setMemberAttachFile(member.getMemberAttachFile());
        } else {
            memberDto.setMemberAttachFile(member.getMemberAttachFile());
            memberDto.setMemberNewFileName(member.getMemberFileEntityList().get(0).getMemberNewFileName());
            memberDto.setMemberOldFileName(member.getMemberFileEntityList().get(0).getMemberOldFileName());
        }

        return memberDto;

    }
}
