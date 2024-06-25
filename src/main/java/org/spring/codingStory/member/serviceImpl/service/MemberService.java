package org.spring.codingStory.member.serviceImpl.service;

import org.spring.codingStory.member.dto.MemberDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.io.IOException;
import java.util.List;

public interface MemberService {
  void memberJoin(MemberDto memberDto) throws IOException;


  List<MemberDto> memberList();

  MemberDto memberDetail(Long id);

  int memberDelete(Long id);

//  void memberUpdate(MemberDto memberDto) throws IOException;

  Page<MemberDto> memberPagingList(Pageable pageable, String department);

  void memberNameUpdate(MemberDto memberDto);

  void memberPhoneNumberUpdate(MemberDto memberDto);

  void memberAddressUpdate(MemberDto memberDto);

  int findCheck(MemberDto memberDto);

  void findPasswordFin(MemberDto memberDto);

  int memberAppOk(MemberDto memberDto);

  Page<MemberDto> memberAppList(Pageable pageable);

  void memberPasswordUpdate(MemberDto memberDto);

  void profileUpdate(MemberDto memberDto) throws IOException;

  int memberJoin2(MemberDto memberDto) throws IOException;

  void memberMDUdate(MemberDto memberDto);

  void memberMRankUpdate(MemberDto memberDto);

  void memberDepartUpdate(MemberDto memberDto);

  MemberDto memberTest(Long id);

  List<MemberDto> findByDepartment(String dptName);
}