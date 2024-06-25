package org.spring.codingStory.member.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.spring.codingStory.approval.entity.ApprovalEntity;
import org.spring.codingStory.board.employee.entity.EmployeeEntity;
import org.spring.codingStory.board.freeBoard.entity.FreeEntity;
import org.spring.codingStory.board.notice.entity.NoticeEntity;
import org.spring.codingStory.contraint.BaseTimeEntity;
import org.spring.codingStory.department.entity.DepartmentEntity;
import org.spring.codingStory.mRank.entity.RankEntity;
import org.spring.codingStory.member.dto.MemberDto;
import org.spring.codingStory.member.role.Role;
import org.spring.codingStory.pay.entity.PayEntity;
import org.spring.codingStory.payment.entity.PaymentEntity;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Entity
@Table(name = "member")
public class MemberEntity extends BaseTimeEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "member_id")
  private Long id;


  @Column(nullable = false, unique = true)
  private String userEmail;

  @Column(nullable = false)
  private String userPw;

  @Column(nullable = false)
  private String name;

  @Column(nullable = true)
  private String department;


  @Column(nullable = true)
  private String mRank;

  @Column(nullable = true) // 기본이 널 허용
  private String address;

  @Column(nullable = true)
  private String phoneNumber;

  @Enumerated(EnumType.STRING)
  private Role role;

  @Column(nullable = true)
  private String memberFileName;

  @Column(nullable = false)
  private int memberAttachFile;



  //  1:N
  @JsonIgnore // ajax시 순환참조 방지
  @OneToMany(mappedBy = "memberEntity"
      , fetch = FetchType.LAZY
      , cascade = CascadeType.REMOVE)
  private List<MemberFileEntity> memberFileEntityList;

  @JsonIgnore // ajax시 순환참조 방지
  @OneToMany(mappedBy = "memberEntity"
      , fetch = FetchType.LAZY
      , cascade = CascadeType.REMOVE)
  private List<PayEntity> payEntityList;

  @JsonIgnore // ajax시 순환참조 방지
  @OneToMany(mappedBy = "memberEntity"
      , fetch = FetchType.LAZY
      , cascade = CascadeType.REMOVE)
  private List<ApprovalEntity> approvalEntityList;

  @JsonIgnore // ajax시 순환참조 방지
  @OneToMany(mappedBy = "memberEntity"
      , fetch = FetchType.LAZY
      , cascade = CascadeType.REMOVE)
  private List<EmployeeEntity> employeeEntityList;

  @JsonIgnore // ajax시 순환참조 방지
  @OneToMany(mappedBy = "memberEntity"
      , fetch = FetchType.LAZY
      , cascade = CascadeType.REMOVE)
  private List<NoticeEntity> noticeEntityList;

  @JsonIgnore // ajax시 순환참조 방지
  @OneToMany(mappedBy = "memberEntity"
      , fetch = FetchType.LAZY
      , cascade = CascadeType.REMOVE)
  private List<FreeEntity> freeEntityList;

  //  N:1
  @JsonIgnore
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "rank_id")
  private RankEntity rankEntity;

  @JsonIgnore
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "department_id")
  private DepartmentEntity departmentEntity;


  public static MemberEntity toJoinMember(MemberDto memberDto, PasswordEncoder passwordEncoder) {
    MemberEntity memberEntity = new MemberEntity();

    memberEntity.setUserEmail(memberDto.getUserEmail());
    memberEntity.setUserPw(passwordEncoder.encode(memberDto.getUserPw()));
    memberEntity.setName(memberDto.getName());
    memberEntity.setDepartment(memberDto.getDepartment());
    memberEntity.setMRank(memberDto.getMRank());
    memberEntity.setAddress(memberDto.getAddress());
    memberEntity.setPhoneNumber(memberDto.getPhoneNumber());
    memberEntity.setRole(Role.GEUST);
    memberEntity.setMemberAttachFile(0);

    return memberEntity;

  }

  public static MemberEntity toJoinFileMember(MemberDto memberDto, PasswordEncoder passwordEncoder) {

    MemberEntity memberEntity = new MemberEntity();

    memberEntity.setUserEmail(memberDto.getUserEmail());
    memberEntity.setUserPw(passwordEncoder.encode(memberDto.getUserPw()));
    memberEntity.setName(memberDto.getName());
    memberEntity.setDepartment(memberDto.getDepartment());
    memberEntity.setMRank(memberDto.getMRank());
    memberEntity.setAddress(memberDto.getAddress());
    memberEntity.setPhoneNumber(memberDto.getPhoneNumber());
    memberEntity.setRole(Role.GEUST);
    memberEntity.setMemberAttachFile(1);

    return memberEntity;
  }

  public static MemberEntity toUpdateFileMember(MemberDto memberDto,PasswordEncoder passwordEncoder) {
    MemberEntity memberEntity = new MemberEntity();

    memberEntity.setId(memberDto.getId());
    memberEntity.setUserPw(passwordEncoder.encode(memberDto.getUserPw()));
    memberEntity.setUserEmail(memberDto.getUserEmail());
    memberEntity.setName(memberDto.getName());
    memberEntity.setDepartment(memberDto.getDepartment());
    memberEntity.setMRank(memberDto.getMRank());
    memberEntity.setAddress(memberDto.getAddress());
    memberEntity.setPhoneNumber(memberDto.getPhoneNumber());
    memberEntity.setRole(memberDto.getRole());
    memberEntity.setMemberAttachFile(1);
    memberEntity.setMemberFileEntityList(memberDto.getMemberFileEntityList());

    return memberEntity;

  }

  public static MemberEntity toUpdateMember(MemberDto memberDto,PasswordEncoder passwordEncoder) {


    MemberEntity memberEntity = new MemberEntity();

    memberEntity.setId(memberDto.getId());
    memberEntity.setUserPw(passwordEncoder.encode(memberDto.getUserPw()));
    memberEntity.setUserEmail(memberDto.getUserEmail());
    memberEntity.setName(memberDto.getName());
    memberEntity.setDepartment(memberDto.getDepartment());
    memberEntity.setMRank(memberDto.getMRank());
    memberEntity.setAddress(memberDto.getAddress());
    memberEntity.setPhoneNumber(memberDto.getPhoneNumber());
    memberEntity.setRole(memberDto.getRole());
    memberEntity.setMemberAttachFile(0);
    memberEntity.setMemberFileEntityList(memberDto.getMemberFileEntityList());

    return memberEntity;

  }

  public static MemberEntity toUpdateProfile(MemberDto memberDto) {

    MemberEntity memberEntity= new MemberEntity();
    memberEntity.setId(memberDto.getId());
    memberEntity.setMemberAttachFile(0);
    memberEntity.setMemberFileEntityList(memberDto.getMemberFileEntityList());

    return memberEntity;
  }

  public static MemberEntity toUpdateProfileYes(MemberDto memberDto) {

    MemberEntity memberEntity= new MemberEntity();
    memberEntity.setId(memberDto.getId());
    memberEntity.setMemberAttachFile(1);
    memberEntity.setMemberFileEntityList(memberDto.getMemberFileEntityList());

    return memberEntity;
  }



  /////////////////////////////////////////////////////////////////

  //  @JsonIgnore // ajax시 순환참조 방지
//  @OneToMany(mappedBy = "memberEntity"
//          , fetch = FetchType.LAZY
//          , cascade = CascadeType.REMOVE)
//  private List<PaymentEntity> paymentEntityList;
  @JsonIgnore
  @OneToOne(mappedBy = "memberEntity", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  private PaymentEntity paymentEntity;

  public static MemberEntity MDUpdate(MemberDto memberDto) {

    MemberEntity memberEntity=new MemberEntity();
    memberEntity.setId(memberDto.getId());
    memberEntity.setMRank(memberDto.getMRank());
    memberEntity.setDepartment(memberDto.getDepartment());

    return memberEntity;
  }

  /////////////////////////////////////////////////////////////////
}