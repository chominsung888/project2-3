package org.spring.codingStory;

import org.junit.jupiter.api.Test;
import org.spring.codingStory.approval.entity.ApprovalDivEntity;
import org.spring.codingStory.approval.entity.ApprovalStatusEntity;
import org.spring.codingStory.approval.repository.ApprovalDivRepository;
import org.spring.codingStory.approval.repository.ApprovalStatusRepository;
import org.spring.codingStory.mRank.entity.RankEntity;
import org.spring.codingStory.mRank.repository.MRankRepository;
import org.spring.codingStory.member.dto.MemberDto;
import org.spring.codingStory.member.entity.MemberEntity;
import org.spring.codingStory.member.repository.MemberFileRepository;
import org.spring.codingStory.member.repository.MemberRepository;
import org.spring.codingStory.member.role.Role;
import org.spring.codingStory.member.serviceImpl.service.MemberService;
import org.spring.codingStory.payment.entity.PaymentEntity;
import org.spring.codingStory.payment.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.io.IOException;

@SpringBootTest
@AutoConfigureMockMvc
public class adminJoin {

  @Autowired
  private MemberRepository memberRepository;
  @Autowired
  private MemberService memberService;
  @Autowired
  private PasswordEncoder passwordEncoder;
  @Autowired
  private MemberFileRepository memberFileRepository;
  @Autowired
  private MRankRepository mRankRepository;
  @Autowired
  private ApprovalDivRepository approvalDivRepository;
  @Autowired
  private ApprovalStatusRepository approvalStatusRepository;


  @Autowired
  private PaymentRepository paymentRepository;

  @Test
  void admin2() throws IOException {

    MemberDto memberDto;
    String adminFile = "admin.jpg";

    MemberEntity memberEntity = MemberEntity.builder()
            .userEmail("admin@naver.com")
            .userPw(passwordEncoder.encode("1234"))
            .name("관리자")
            .department("노원점")
            .mRank("사장")
            .address("서울")
            .phoneNumber("0101234")
            .role(Role.ADMIN)
            .memberAttachFile(0)
            .build();

    memberEntity = memberRepository.save(memberEntity);

    PaymentEntity paymentEntity = new PaymentEntity();
    paymentEntity.setMemberEntity(memberEntity);

    // mRank가 "사장"일 때 hourWage를 1000원으로 설정
    if ("사장".equals(memberEntity.getMRank())) {
      paymentEntity.setHourlyWage("1000");
    } else {
      paymentEntity.setHourlyWage("0"); // 기본 값 설정
    }

    paymentRepository.save(paymentEntity);

    RankEntity rankEntity = RankEntity.builder()
            .rankName("사원")
            .build();
    RankEntity rankEntity1 = RankEntity.builder()
            .rankName("팀장")
            .build();
    RankEntity rankEntity2 = RankEntity.builder()
            .rankName("지점장")
            .build();
    RankEntity rankEntity3 = RankEntity.builder()
            .rankName("사장")
            .build();

    mRankRepository.save(rankEntity);
    mRankRepository.save(rankEntity1);
    mRankRepository.save(rankEntity2);
    mRankRepository.save(rankEntity3);

    //보고서 진행 상태
    ApprovalStatusEntity approvalStatusEntity1 = approvalStatusRepository.save(
            ApprovalStatusEntity.builder()
                    .apvStatus("진행중")
                    .build()
    );
    ApprovalStatusEntity approvalStatusEntity2 = approvalStatusRepository.save(
            ApprovalStatusEntity.builder()
                    .apvStatus("승인")
                    .build()
    );
    ApprovalStatusEntity approvalStatusEntity3 = approvalStatusRepository.save(
            ApprovalStatusEntity.builder()
                    .apvStatus("반려")
                    .build()
    );

    //보고서 종류
    ApprovalDivEntity approvalDivEntity1 = approvalDivRepository.save(
            ApprovalDivEntity.builder()
                    .apvDivName("업무 보고서")
                    .build()
    );
    ApprovalDivEntity approvalDivEntity2 = approvalDivRepository.save(
            ApprovalDivEntity.builder()
                    .apvDivName("회의결과 보고서")
                    .build()
    );
    ApprovalDivEntity approvalDivEntity3 = approvalDivRepository.save(
            ApprovalDivEntity.builder()
                    .apvDivName("휴가 보고서")
                    .build()
    );
    ApprovalDivEntity approvalDivEntity4 = approvalDivRepository.save(
            ApprovalDivEntity.builder()
                    .apvDivName("결제 청구서")
                    .build()
    );
  }

  //////////////////////////////////////////////////////////

  @Test
  void admin() throws IOException {

    MemberDto memberDto;
    String adminFile = "admin.jpg";

    MemberEntity memberEntity = memberRepository.save(
            MemberEntity.builder()
                    .userEmail("admin@naver.com")
                    .userPw(passwordEncoder.encode("1234"))
                    .name("관리자")
                    .department("노원점")
                    .mRank("사장")
                    .address("서울")
                    .phoneNumber("0101234")
                    .role(Role.ADMIN)
                    .memberAttachFile(0)
                    .build());


    //보고서 진행 상태
    ApprovalStatusEntity approvalStatusEntity1 = approvalStatusRepository.save(
            ApprovalStatusEntity.builder()
                    .apvStatus("진행중")
                    .build()
    );
    ApprovalStatusEntity approvalStatusEntity2 = approvalStatusRepository.save(
            ApprovalStatusEntity.builder()
                    .apvStatus("승인")
                    .build()
    );
    ApprovalStatusEntity approvalStatusEntity3 = approvalStatusRepository.save(
            ApprovalStatusEntity.builder()
                    .apvStatus("반려")
                    .build()
    );

    //보고서 종류
    ApprovalDivEntity approvalDivEntity1 = approvalDivRepository.save(
            ApprovalDivEntity.builder()
                    .apvDivName("업무 보고서")
                    .build()
    );
    ApprovalDivEntity approvalDivEntity2 = approvalDivRepository.save(
            ApprovalDivEntity.builder()
                    .apvDivName("회의결과 보고서")
                    .build()
    );
    ApprovalDivEntity approvalDivEntity3 = approvalDivRepository.save(
            ApprovalDivEntity.builder()
                    .apvDivName("휴가 보고서")
                    .build()
    );
    ApprovalDivEntity approvalDivEntity4 = approvalDivRepository.save(
            ApprovalDivEntity.builder()
                    .apvDivName("결제 청구서")
                    .build()
    );


    RankEntity rankEntity = RankEntity.builder()
            .rankName("사원")
            .build();
    RankEntity rankEntity1 = RankEntity.builder()
            .rankName("팀장")
            .build();
    RankEntity rankEntity2 = RankEntity.builder()
            .rankName("지점장")
            .build();
    RankEntity rankEntity3 = RankEntity.builder()
            .rankName("사장")
            .build();

    mRankRepository.save(rankEntity);
    mRankRepository.save(rankEntity1);
    mRankRepository.save(rankEntity2);
    mRankRepository.save(rankEntity3);


  }


  @Test
  void test1() {

    for (int i = 1; i <= 30; i++) {
      MemberEntity memberEntity = memberRepository.save(
              MemberEntity.builder()
                      .userEmail("memb" + i + "@naver.com")
                      .userPw(passwordEncoder.encode("1234"))
                      .name("사원" + i)
                      .department("자동차관")
                      .mRank("사원")
                      .address("서울")
                      .phoneNumber("0101234")
                      .role(Role.MEMBER)
                      .memberAttachFile(0)
                      .build());


    }


  }


}

