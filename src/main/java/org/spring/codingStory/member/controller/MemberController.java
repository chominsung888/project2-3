package org.spring.codingStory.member.controller;

import lombok.RequiredArgsConstructor;
import org.spring.codingStory.config.MyUserDetails;
import org.spring.codingStory.department.dto.DepartmentDto;
import org.spring.codingStory.department.serviceimpl.service.DepartmentService;
import org.spring.codingStory.mRank.dto.RankDto;
import org.spring.codingStory.mRank.serviceImpl.service.MRankService;
import org.spring.codingStory.member.dto.MemberDto;
import org.spring.codingStory.member.repository.MemberRepository;
import org.spring.codingStory.member.serviceImpl.service.MemberService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@Controller
@RequestMapping("/member")
public class MemberController {

  private final MemberRepository memberRepository;
  private final MemberService memberService;
  private final DepartmentService departmentService;
  private final MRankService mRankService;


  @GetMapping("/join")
  public String join(){

    return "member/join";
  }
  @PostMapping("/join")
  public String joinGo(@Valid MemberDto memberDto, BindingResult bindingResult,Model model
  ) throws IOException {


  if(bindingResult.hasErrors()){
      return "member/join";
  }
  memberService.memberJoin(memberDto);

    return "redirect:/login";
  }




  @GetMapping("/myDetail/{id}")
  public String memberDetail(@PathVariable("id")Long id,Model model,MemberDto memberDto){

    MemberDto member=memberService.memberDetail(id);




    model.addAttribute("member",member);


    return "member/myDetail";
  }

//  @PostMapping("/update")
//  public String memberUpdate(MemberDto memberDto) throws IOException {
//
//      memberService.memberUpdate(memberDto);
//
//    return "redirect:/member/detail/"+memberDto.getId();
//  }

  @ResponseBody
  @PostMapping("/memberDelete/{id}")
  public ResponseEntity<?> memberDelete(@PathVariable("id")Long id){

    int result=memberService.memberDelete(id);

    return ResponseEntity.status(HttpStatus.OK).body(result);
  }

  @GetMapping("/memberList")
  public String shopList(@PageableDefault(page=0,size = 8, sort = "id",direction = Sort.Direction.DESC) Pageable pageable,
                         @RequestParam(name = "department", required = false) String department, Model model, @AuthenticationPrincipal MyUserDetails myUserDetails) {


    Page<MemberDto> memberDto = memberService.memberPagingList(pageable,department);

    int totalPages = memberDto.getTotalPages();// 전체 페이지
    int newPage = memberDto.getNumber(); // 현재 페이지
    long  totalElements = memberDto.getNumberOfElements(); // 전체 레코드 갯수

    int size = memberDto.getSize(); // 페이지당 보이는 갯수

    int blockNum = 5; // 브라우저에 보이는 페이지 번호

    int startPage = (int) ((Math.floor(newPage / blockNum) * blockNum) + 1 <= totalPages
            ? (Math.floor(newPage / blockNum)* blockNum ) + 1
            : totalPages
    );

    int endPage = (startPage + blockNum) - 1 < totalPages ? (startPage + blockNum) -1 : totalPages;

    model.addAttribute("startPage",startPage);

    model.addAttribute("endPage",endPage);

    model.addAttribute("memberDto",memberDto);
    model.addAttribute("myUserDetails",myUserDetails);


    return "member/memberList";

  }

  @ResponseBody
  @PostMapping("/nameUpdate")
  public String memberNameUpdate(MemberDto memberDto){

    memberService.memberNameUpdate(memberDto);

    return "member/detail/"+memberDto.getId();
  }

  @ResponseBody
  @PostMapping("/phoneNumberUpdate")
  public String memberPhoneNumberUpdate(MemberDto memberDto){

    memberService.memberPhoneNumberUpdate(memberDto);

    return "member/detail/"+memberDto.getId();
  }

  @ResponseBody
  @PostMapping("/passwordUpdate")
  public String memberPasswordUpdate(MemberDto memberDto){

    memberService.memberPasswordUpdate(memberDto);

    return "member/detail/"+memberDto.getId();
  }




  @ResponseBody
  @PostMapping("/addressUpdate")
  public String memberAddressUpdate(MemberDto memberDto){

    memberService.memberAddressUpdate(memberDto);

    return "member/detail/"+memberDto.getId();
  }

  @GetMapping("/findCheck")
  public String findCheck(){

    return "member/findCheck";
  }

  @ResponseBody
  @PostMapping("/findCheck")
  public ResponseEntity<?> findCheckGo(MemberDto memberDto){

    int result=memberService.findCheck(memberDto);

    return ResponseEntity.status(HttpStatus.OK).body(result);
  }

  @GetMapping("/findPasswordOk")
  public String findPasswordOk(MemberDto memberDto, Model model){

    model.addAttribute("userEmail", memberDto.getUserEmail());


    return "member/findPasswordOk";
  }

  @PostMapping("/findPasswordOk")
  public String findPasswordOkFin(MemberDto memberDto){

    memberService.findPasswordFin(memberDto);

    return "login";
  }

  @PostMapping("/memberAppOk")
  public ResponseEntity<?> memberAppOk(MemberDto memberDto){

    int result=memberService.memberAppOk(memberDto);

    return ResponseEntity.status(HttpStatus.OK).body(result);
  }

  @GetMapping("/memberInfo/{id}")
  public String memberInfo(@PathVariable("id")Long id,Model model, MemberDto memberDto){

    MemberDto member=memberService.memberDetail(id);
    List<DepartmentDto> depart =departmentService.findDepart();
    List<RankDto> rank=mRankService.findRank();

    model.addAttribute("member",member);
    model.addAttribute("depart",depart);
    model.addAttribute("rank",rank);

    return "member/memberInfo";
  }

  @GetMapping("/memberAppList")
  public String memberAppList(@PageableDefault(page=0,size = 1, sort = "id",direction = Sort.Direction.DESC) Pageable pageable,
                              Model model, @AuthenticationPrincipal MyUserDetails myUserDetails){

    Page<MemberDto> memberDto = memberService.memberAppList(pageable);
    List<DepartmentDto> depart =departmentService.findDepart();
    List<RankDto> rank=mRankService.findRank();

    int totalPages = memberDto.getTotalPages();// 전체 페이지
    int newPage = memberDto.getNumber(); // 현재 페이지
    long  totalElements = memberDto.getNumberOfElements(); // 전체 레코드 갯수

    int size = memberDto.getSize(); // 페이지당 보이는 갯수

    int blockNum = 5; // 브라우저에 보이는 페이지 번호

    int startPage = (int) ((Math.floor(newPage / blockNum) * blockNum) + 1 <= totalPages
            ? (Math.floor(newPage / blockNum)* blockNum ) + 1
            : totalPages
    );

    int endPage = (startPage + blockNum) - 1 < totalPages ? (startPage + blockNum) -1 : totalPages;

    model.addAttribute("startPage",startPage);
    model.addAttribute("endPage",endPage);
    model.addAttribute("memberDto",memberDto);
    model.addAttribute("myUserDetails",myUserDetails);
    model.addAttribute("total",totalPages);
    model.addAttribute("depart",depart);
    model.addAttribute("rank",rank);



    return "member/memberAppList";
  }

  @PostMapping("/profileUpdate")
  public String profileUpdate(MemberDto memberDto) throws IOException {

  memberService.profileUpdate(memberDto);

    return "redirect:/member/myDetail/"+memberDto.getId();
 }


  @GetMapping("/test/{id}")
  public String test(MemberDto memberDto,Model model,@PathVariable("id")Long id){


    MemberDto member=memberService.memberTest(id);
    model.addAttribute("member",member);


    return "member/test";
  }


  @PostMapping("/MDUpdate")
  public String memberMDUpdate(MemberDto memberDto){

    memberService.memberMDUdate(memberDto);


    return "redirect:/member/memberInfo/"+memberDto.getId();
  }

  @PostMapping("/MRankUpdate")
  public String memberMRankUpdate(MemberDto memberDto){


    memberService.memberMRankUpdate(memberDto);
    return "member/memberInfo/"+memberDto.getId();
  }

  @PostMapping("/departUpdate")
  public String memberDepartUpdate(MemberDto memberDto){


    memberService.memberDepartUpdate(memberDto);

    return "member/memberInfo/"+memberDto.getId();

  }


}
