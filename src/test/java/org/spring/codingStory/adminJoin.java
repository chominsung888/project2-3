package org.spring.codingStory;

import ch.qos.logback.classic.pattern.FileOfCallerConverter;
import org.junit.jupiter.api.Test;
import org.spring.codingStory.config.FileConfig;
import org.spring.codingStory.member.dto.MemberDto;
import org.spring.codingStory.member.dto.MemberFileDto;
import org.spring.codingStory.member.entity.MemberEntity;
import org.spring.codingStory.member.entity.MemberFileEntity;
import org.spring.codingStory.member.repository.MemberFileRepository;
import org.spring.codingStory.member.repository.MemberRepository;
import org.spring.codingStory.member.role.Role;
import org.spring.codingStory.member.serviceImpl.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

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

  }

}


