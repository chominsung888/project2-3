package org.spring.codingStory.approval.controller;

import lombok.RequiredArgsConstructor;
import org.spring.codingStory.approval.dto.ApprovalDivDto;
import org.spring.codingStory.approval.dto.ApprovalDto;
import org.spring.codingStory.approval.dto.ApprovalStatusDto;
import org.spring.codingStory.approval.entity.ApprovalEntity;
import org.spring.codingStory.approval.repository.ApprovalStatusRepository;
import org.spring.codingStory.approval.serviceImpl.ApprovalDivServiceImpl;
import org.spring.codingStory.approval.serviceImpl.ApprovalServiceImpl;
import org.spring.codingStory.approval.serviceImpl.ApprovalStatusServiceImpl;
import org.spring.codingStory.config.MyUserDetails;
import org.spring.codingStory.department.dto.DepartmentDto;
import org.spring.codingStory.department.serviceimpl.service.DepartmentService;
import org.spring.codingStory.mRank.serviceImpl.MRankServiceImpl;
import org.spring.codingStory.member.dto.MemberDto;
import org.spring.codingStory.member.entity.MemberEntity;
import org.spring.codingStory.member.serviceImpl.MemberServiceImpl;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;


@Controller
@RequiredArgsConstructor
@RequestMapping("/apv")
public class ApprovalController {

    private final MemberServiceImpl memberService;
    private final ApprovalServiceImpl approvalService;
    private final ApprovalDivServiceImpl approvalDivService;
    private final ApprovalStatusServiceImpl approvalStatusService;
    private final ApprovalStatusRepository approvalStatusRepository;
    private final DepartmentService departmentService;
    private final MRankServiceImpl mRankService;

    @GetMapping("/write")
    public String write(@AuthenticationPrincipal MyUserDetails myUserDetails, Model model, ApprovalDto approvalDto) {
        ApprovalEntity approvalEntity = new ApprovalEntity();

        List<MemberDto> memberDtoList = memberService.memberList();
        List<ApprovalDivDto> approvalDivDtoList = approvalDivService.apvDivList();
        List<ApprovalStatusDto> approvalStatusDtoList = approvalStatusService.apvStatusList();
        List<DepartmentDto> departmentDtoList = departmentService.findDepart();

        model.addAttribute("myUserDetails", myUserDetails);
        model.addAttribute("memberId", myUserDetails.getMemberEntity().getId());
        model.addAttribute("memberName", myUserDetails.getMemberEntity().getName());
        model.addAttribute("mRank", myUserDetails.getMemberEntity().getMRank());
        model.addAttribute("department", myUserDetails.getMemberEntity().getDepartment());
        model.addAttribute("approvalDto", approvalDto.getApvAttachFile());
        model.addAttribute("approvalStatusDtoList", approvalStatusDtoList);
        model.addAttribute("memberDtoList", memberDtoList);
        model.addAttribute("approvalDivDtoList", approvalDivDtoList);
        model.addAttribute("departmentDtoList", departmentDtoList);

        return "apv/write";
    }

    @PostMapping("/write")
    public String write(ApprovalDto approvalDto) throws IOException {

        approvalService.apvWrite(approvalDto);

        return "redirect:/apv/list";
    }


    //내가 결재자인 보고서
    @GetMapping("/list")
    public String apvList1(Model model,
                           @AuthenticationPrincipal MyUserDetails myUserDetails,
                           @RequestParam(name = "subject", required = false) String subject,
                           @RequestParam(name = "search", required = false) String search,
                           @PageableDefault(page = 0, size = 3, sort = "id", direction = Sort.Direction.DESC)
                           Pageable pageable) {
        model.addAttribute("myUserDetails", myUserDetails);
        String name = myUserDetails.getName();
        Long memberId=myUserDetails.getMemberEntity().getId();
        Page<ApprovalDto> approvalDtoPage = approvalService.apvList(pageable, subject, search, name);

        Long apvCount=approvalService.apvCount(name);
        Long apvWaitCount = approvalService.apvWaitCount(name,1L);
        Long apvDenyCount = approvalService.apvDenyCount(name,3L);
        Long apvMyCount = approvalService.apvMyCount(memberId);
        Long apvMyDenyCount = approvalService.apvMyDenyCount(memberId,3L);

        int totalPages = approvalDtoPage.getTotalPages();
        int newPage = approvalDtoPage.getNumber();
        int blockNum = 10;
        int startPage = (int) (
            (Math.floor(newPage / blockNum) * blockNum) + 1 <=
                totalPages ? (Math.floor(newPage / blockNum) * blockNum) + 1 : totalPages);
        int endPage = (startPage + blockNum) - 1 < totalPages ? (startPage + blockNum) - 1 : totalPages;

        model.addAttribute("startPage", startPage);
        model.addAttribute("newPage", newPage);
        model.addAttribute("endPage", endPage);
        model.addAttribute("approvalDtoPage", approvalDtoPage);
        model.addAttribute("apvCount", apvCount);
        model.addAttribute("apvWaitCount", apvWaitCount);
        model.addAttribute("apvDenyCount", apvDenyCount);
        model.addAttribute("apvMyCount", apvMyCount);
        model.addAttribute("apvMyDenyCount", apvMyDenyCount);

        return "/apv/list";
    }



    //진행(대기)중인 보고서
    @GetMapping("/waitList")
    public String apvWaitList(Model model,
                           @AuthenticationPrincipal MyUserDetails myUserDetails,
                           @RequestParam(name = "subject", required = false) String subject,
                           @RequestParam(name = "search", required = false) String search,
                           @PageableDefault(page = 0, size = 3, sort = "id", direction = Sort.Direction.DESC)
                           Pageable pageable) {
        model.addAttribute("myUserDetails", myUserDetails);
        String name = myUserDetails.getName();
        Long approvalStatusEntity_Id = 1L;
        Long memberId=myUserDetails.getMemberEntity().getId();

        Page<ApprovalDto> approvalDtoPage = approvalService.
                apvWaitList(pageable, subject, approvalStatusEntity_Id, search, name);
        Long apvCount=approvalService.apvCount(name);
        Long apvWaitCount = approvalService.apvWaitCount(name,1L);
        Long apvDenyCount = approvalService.apvDenyCount(name,3L);
        Long apvMyCount = approvalService.apvMyCount(memberId);
        Long apvMyDenyCount = approvalService.apvMyDenyCount(memberId,3L);

        int totalPages = approvalDtoPage.getTotalPages();
        int newPage = approvalDtoPage.getNumber();
        int blockNum = 10;
        int startPage = (int) (
            (Math.floor(newPage / blockNum) * blockNum) + 1 <=
                totalPages ? (Math.floor(newPage / blockNum) * blockNum) + 1 : totalPages);
        int endPage = (startPage + blockNum) - 1 < totalPages ? (startPage + blockNum) - 1 : totalPages;

        model.addAttribute("startPage", startPage);
        model.addAttribute("newPage", newPage);
        model.addAttribute("endPage", endPage);
        model.addAttribute("approvalDtoPage", approvalDtoPage);
        model.addAttribute("apvCount", apvCount);
        model.addAttribute("apvWaitCount", apvWaitCount);
        model.addAttribute("apvDenyCount", apvDenyCount);
        model.addAttribute("apvMyCount", apvMyCount);
        model.addAttribute("apvMyDenyCount", apvMyDenyCount);

        return "/apv/waitList";
    }

    // 반려한 보고서
    @GetMapping("/denyList")
    public String apvDenyList(Model model,
                              @AuthenticationPrincipal MyUserDetails myUserDetails,
                              @RequestParam(name = "subject", required = false) String subject,
                              @RequestParam(name = "search", required = false) String search,
                              @PageableDefault(page = 0, size = 3, sort = "id", direction = Sort.Direction.DESC)
                              Pageable pageable) {
        model.addAttribute("myUserDetails", myUserDetails);
        String name = myUserDetails.getName();
        Long memberId = myUserDetails.getMemberEntity().getId();
        Long approvalStatusEntity_Id = 3L;

        Page<ApprovalDto> approvalDtoPage = approvalService.apvDenyList(pageable, subject, approvalStatusEntity_Id, search, name);
        Long apvCount=approvalService.apvCount(name);
        Long apvWaitCount = approvalService.apvWaitCount(name,1L);
        Long apvDenyCount = approvalService.apvDenyCount(name,3L);
        Long apvMyCount = approvalService.apvMyCount(memberId);
        Long apvMyDenyCount = approvalService.apvMyDenyCount(memberId,3L);

        int totalPages = approvalDtoPage.getTotalPages();
        int newPage = approvalDtoPage.getNumber();
        int blockNum = 10;
        int startPage = (int) (
            (Math.floor(newPage / blockNum) * blockNum) + 1 <=
                totalPages ? (Math.floor(newPage / blockNum) * blockNum) + 1 : totalPages);
        int endPage = (startPage + blockNum) - 1 < totalPages ? (startPage + blockNum) - 1 : totalPages;

        model.addAttribute("startPage", startPage);
        model.addAttribute("newPage", newPage);
        model.addAttribute("endPage", endPage);
        model.addAttribute("approvalDtoPage", approvalDtoPage);
        model.addAttribute("apvCount", apvCount);
        model.addAttribute("apvWaitCount", apvWaitCount);
        model.addAttribute("apvDenyCount", apvDenyCount);
        model.addAttribute("apvMyCount", apvMyCount);
        model.addAttribute("apvMyDenyCount", apvMyDenyCount);

        return "/apv/denyList";
    }


    ///내가 쓴  보고서
    @GetMapping("/myApvList")
    public String myApvList(Model model,
                            @AuthenticationPrincipal MyUserDetails myUserDetails,
                            @RequestParam(name = "subject", required = false) String subject,
                            @RequestParam(name = "search", required = false) String search,
                            @PageableDefault(page = 0, size = 3, sort = "id", direction = Sort.Direction.DESC)
                            Pageable pageable) {
        model.addAttribute("myUserDetails", myUserDetails);
        Long memberId = myUserDetails.getMemberEntity().getId();
        String name = myUserDetails.getName();
        Page<ApprovalDto> approvalDtoPage = approvalService.myApvList(pageable, subject, search, memberId);
        Long apvCount=approvalService.apvCount(name);
        Long apvWaitCount = approvalService.apvWaitCount(name,1L);
        Long apvDenyCount = approvalService.apvDenyCount(name,3L);
        Long apvMyCount = approvalService.apvMyCount(memberId);
        Long apvMyDenyCount = approvalService.apvMyDenyCount(memberId,3L);

        int totalPages = approvalDtoPage.getTotalPages();
        int newPage = approvalDtoPage.getNumber();
        int blockNum = 10;
        int startPage = (int) (
            (Math.floor(newPage / blockNum) * blockNum) + 1 <=
                totalPages ? (Math.floor(newPage / blockNum) * blockNum) + 1 : totalPages);
        int endPage = (startPage + blockNum) - 1 < totalPages ? (startPage + blockNum) - 1 : totalPages;

        model.addAttribute("startPage", startPage);
        model.addAttribute("newPage", newPage);
        model.addAttribute("endPage", endPage);
        model.addAttribute("approvalDtoPage", approvalDtoPage);
        model.addAttribute("apvCount", apvCount);
        model.addAttribute("apvWaitCount", apvWaitCount);
        model.addAttribute("apvDenyCount", apvDenyCount);
        model.addAttribute("apvMyCount", apvMyCount);
        model.addAttribute("apvMyDenyCount", apvMyDenyCount);

        return "/apv/myApvList";
    }

    //내가 작성한 보고서 중 반려당한 것을 보여줌
    @GetMapping("/myApvDenyList")
    public String myApvDenyList(Model model,
                            @AuthenticationPrincipal MyUserDetails myUserDetails,
                            @RequestParam(name = "subject", required = false) String subject,
                            @RequestParam(name = "search", required = false) String search,
                            @PageableDefault(page = 0, size = 3, sort = "id", direction = Sort.Direction.DESC)
                            Pageable pageable) {
        model.addAttribute("myUserDetails", myUserDetails);
        Long memberId = myUserDetails.getMemberEntity().getId();
        String name = myUserDetails.getName();
        Long approvalStatusEntity_Id = 3L;

        Page<ApprovalDto> approvalDtoPage = approvalService
                .myApvDenyList(pageable, subject, search, memberId, approvalStatusEntity_Id);
        Long apvCount=approvalService.apvCount(name);
        Long apvWaitCount = approvalService.apvWaitCount(name,1L);
        Long apvDenyCount = approvalService.apvDenyCount(name,3L);
        Long apvMyCount = approvalService.apvMyCount(memberId);
        Long apvMyDenyCount = approvalService.apvMyDenyCount(memberId,3L);

        int totalPages = approvalDtoPage.getTotalPages();
        int newPage = approvalDtoPage.getNumber();
        int blockNum = 10;
        int startPage = (int) (
            (Math.floor(newPage / blockNum) * blockNum) + 1 <=
                totalPages ? (Math.floor(newPage / blockNum) * blockNum) + 1 : totalPages);
        int endPage = (startPage + blockNum) - 1 < totalPages ? (startPage + blockNum) - 1 : totalPages;

        model.addAttribute("startPage", startPage);
        model.addAttribute("newPage", newPage);
        model.addAttribute("endPage", endPage);
        model.addAttribute("approvalDtoPage", approvalDtoPage);
        model.addAttribute("apvCount", apvCount);
        model.addAttribute("apvWaitCount", apvWaitCount);
        model.addAttribute("apvDenyCount", apvDenyCount);
        model.addAttribute("apvMyCount", apvMyCount);
        model.addAttribute("apvMyDenyCount", apvMyDenyCount);

        return "/apv/myApvDenyList";
    }


    @GetMapping("waitCount")
    public Long waitCount(@AuthenticationPrincipal MyUserDetails myUserDetails,
                           Model model){
        String name = myUserDetails.getName();
        Long approvalStatusEntity_Id = 1L;
        Long waitCount=  approvalService.apvWaitCount(name,approvalStatusEntity_Id);
    model.addAttribute("waitCount",waitCount);
        return waitCount;
    }


    @GetMapping("/detail/{id}")
    public String detail(Model model, @PathVariable("id") Long id,
                         @AuthenticationPrincipal MyUserDetails myUserDetails) {
        ApprovalEntity approvalEntity = new ApprovalEntity();
        List<ApprovalStatusDto> approvalStatusDtoList = approvalStatusService.apvStatusList();
        ApprovalDto approvalDto = approvalService.apvDetail(id);
        List<MemberDto> memberDto= memberService.memberList();
        String apvFnlDept= approvalService.getDepartmentByApvFnlName(id);
        String apvFnlRank= approvalService.getRankByApvFnlName(id);

        model.addAttribute("apvFnlName",approvalDto.getApvFnl()); //결재자의 이름
        model.addAttribute("apvFnlDept",apvFnlDept); //결재자의 부서
        model.addAttribute("apvFnlRank",apvFnlRank); //결재자의 직급
        model.addAttribute("memberDto",memberDto);
        model.addAttribute("mRank", approvalDto.getMemberEntity().getMRank());
        model.addAttribute("department", approvalDto.getMemberEntity().getDepartment());
        model.addAttribute("memberName",approvalDto.getMemberEntity().getName());
        model.addAttribute("myUserDetails", myUserDetails);
        model.addAttribute("apvEntity", approvalEntity);
        model.addAttribute("approvalDto", approvalDto);// entity -> dto 로 변환한 것
        model.addAttribute("apvFile", approvalDto.getApvFile());// entity -> dto 로 변환한 것
        model.addAttribute("approvalStatusDtoList", approvalStatusDtoList);
        return "apv/detail";
    }


    @GetMapping("/apvDelete/{id}")
    public String delete(@PathVariable("id") Long id) {

        approvalService.apvDeleteById(id);
        return "redirect:/apv/list";
    }

    //수정
    @GetMapping("/apvUpdate/{id}")
    public String update(@PathVariable("id") Long id, Model model,
                         @AuthenticationPrincipal MyUserDetails myUserDetails) {
        ApprovalDto approvalDto = approvalService.apvDetail(id);
        Long apvName = approvalDto.getMemberEntity().getId();
        List<MemberDto> memberDtoList = memberService.memberList();
        List<ApprovalDivDto> approvalDivDtoList = approvalDivService.apvDivList();
        List<ApprovalStatusDto> approvalStatusDtoList = approvalStatusService.apvStatusList();

        model.addAttribute("myUserDetails", myUserDetails);
        model.addAttribute("mRank", approvalDto.getMemberEntity().getMRank());
        model.addAttribute("memberId", myUserDetails.getMemberEntity().getId());
        model.addAttribute("memberName", myUserDetails.getMemberEntity().getName());
        model.addAttribute("memberDtoList", memberDtoList);
        model.addAttribute("approvalDivDtoList", approvalDivDtoList);
        model.addAttribute("approvalStatusDtoList", approvalStatusDtoList);
        model.addAttribute("approvalDto", approvalDto.getApvAttachFile());
        model.addAttribute("myUserDetails", myUserDetails);
        model.addAttribute("apvName", apvName);
        model.addAttribute("approvalDto", approvalDto);// entity -> dto 로 변환한 것

        return "apv/apvUpdate";
    }



    @PostMapping("/apvUpdate")
    public String updateOk(Model model,
                           @ModelAttribute ApprovalDto approvalDto) throws IOException {

        ApprovalEntity approvalEntity = ApprovalEntity.builder()
            .id(approvalDto.getId()).build();
        approvalDto.setId(approvalEntity.getId());

        approvalService.apvUpdate(approvalDto);

        return "redirect:/apv/detail/" + approvalDto.getId();
    }

    //update 시 파일 유지 연습해보기
//    @PostMapping("/apvUpdate")
//    public String updateOk(@RequestParam("postId") Long id,
//                           @RequestParam("title") String apvTitle,
//                           @RequestParam("newFile") MultipartFile newFile,
//                           @RequestParam("existingFile") String existingFile,
//                           @ModelAttribute ApprovalDto approvalDto) throws IOException {
//
//        ApprovalEntity approvalEntity = ApprovalEntity.builder()
//            .id(approvalDto.getId()).build();
//        approvalDto.setId(approvalEntity.getId());
//
//        ApprovalEntity approvalEntity1 = approvalService.findById(id);
//        approvalEntity1.setTitle(apvTitle);
//
//        if (!newFile.isEmpty()) {
//            // 새 파일이 업로드된 경우
//            String newFilePath = approvalFileService.saveFile(newFile);
//            post.setFilePath(newFilePath);
//        } else {
//            // 새 파일이 업로드되지 않은 경우
//            post.setFilePath(existingFile);
//        }
//
//        approvalService.save(post);
//
//        approvalService.apvUpdate(approvalDto);
//
//        return "redirect:/apv/detail/" + approvalDto.getId();
//    }


    @PostMapping("/apvOk")
    public String apvOk(@ModelAttribute ApprovalDto approvalDto, Model model, @AuthenticationPrincipal MyUserDetails myUserDetails) {
        ApprovalEntity approvalEntity = new ApprovalEntity();

        System.out.println("================");
        List<MemberDto> memberDtoList = memberService.memberList();
        List<ApprovalDivDto> approvalDivDtoList = approvalDivService.apvDivList();
        List<ApprovalStatusDto> approvalStatusDtoList = approvalStatusService.apvStatusList();

        model.addAttribute("memberName", myUserDetails.getMemberEntity().getName());
        model.addAttribute("memberDtoList", memberDtoList);
        model.addAttribute("approvalDivDtoList", approvalDivDtoList);
        model.addAttribute("approvalStatusDtoList", approvalStatusDtoList);
        model.addAttribute("approvalAttach", approvalDto.getApvAttachFile());
        model.addAttribute("myUserDetails", myUserDetails);
        model.addAttribute("approvalDto", approvalDto);// entity -> dto 로 변환한 것
        System.out.println("---------------------");
        ApprovalEntity approvalEntity1 = ApprovalEntity.builder()
            .id(approvalDto.getId()).build();
        approvalDto.setId(approvalEntity1.getId());
        approvalService.apvOk(approvalDto);
        return "redirect:/apv/list";
    }


}
