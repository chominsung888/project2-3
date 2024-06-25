package org.spring.codingStory.board.notice.controller;

import lombok.RequiredArgsConstructor;
import org.spring.codingStory.board.notice.dto.NoticeDto;
import org.spring.codingStory.board.notice.dto.NoticeReplyDto;
import org.spring.codingStory.board.notice.serviceImpl.NoticeReplyServiceImpl;
import org.spring.codingStory.board.notice.serviceImpl.NoticeServiceImpl;
import org.spring.codingStory.config.MyUserDetails;
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

@RequiredArgsConstructor
@Controller
@RequestMapping(value = "/board")
public class NoticeController {

    private final NoticeServiceImpl noticeService;
    private final NoticeReplyServiceImpl noticeReplyService;

    @GetMapping("/noticeWrite")
    public String noticeWrite(@AuthenticationPrincipal MyUserDetails myUserDetails,
                              NoticeDto noticeDto, Model model) {

        model.addAttribute("memberId", myUserDetails.getMemberEntity().getId());
        model.addAttribute("noticeDto", noticeDto);
        model.addAttribute("memberName", myUserDetails.getMemberEntity().getName());

        return "board/noticeWrite";
    }


    @PostMapping("/noticeWrite")
    public String noticeWriteOK(NoticeDto noticeDto,
                               @AuthenticationPrincipal MyUserDetails myUserDetails,
                               Model model) throws IOException {

        noticeService.noticeInsertFile(noticeDto);

        return "redirect:/board/noticeList";
        //글 작성후에 employeeList 페이지로 이동
    }

    @GetMapping("/noticeList")
    public String noticeList(@PageableDefault(page = 0, size = 3, sort = "id", direction = Sort.Direction.DESC) Pageable pageable,
                           Model model,
                           @RequestParam(name = "subject1", required = false) String subject1,
                           @RequestParam(name = "subject2", required = false) String subject2,
                           @RequestParam(name = "search", required = false) String search,
                           @AuthenticationPrincipal MyUserDetails myUserDetails ) {


        Page<NoticeDto> noticeList = noticeService.noticeList(pageable, subject1, subject2, search);


        int totalPages = noticeList.getTotalPages(); // 전체 페이지
        int newPage = noticeList.getNumber(); // 현재 페이지
        int blockNum = 8;// 브라우저에 보이는 페이지번호

        int startPage = (int) (
                (Math.floor(newPage / blockNum) * blockNum) + 1 <= totalPages ? (Math.floor(newPage / blockNum) * blockNum) + 1 : totalPages
        );

        int endPage = (startPage + blockNum) - 1 < totalPages ? (startPage + blockNum) - 1 : totalPages;

        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        model.addAttribute("noticeList", noticeList);
        model.addAttribute("myUserDetails", myUserDetails);

        return "board/noticeList";
    }

    @GetMapping("/noticeDetail/{Id}")
    public String noticeDetail(@PathVariable("Id") Long Id,
                            @AuthenticationPrincipal MyUserDetails myUserDetails,
                            Model model) {

        noticeService.updateNoticeHit(Id);
        NoticeDto noticeDto = noticeService.detail(Id);

        // 상점 댓글 목록 조회
        List<NoticeReplyDto> noticeReplyDtoList = noticeReplyService.noticeReplyList(noticeDto.getId());

        model.addAttribute("notice", noticeDto);
        model.addAttribute("noticeReplyDtoList", noticeReplyDtoList);
        model.addAttribute("myUserDetails", myUserDetails);

        return "board/noticeDetail";
    }
    @PostMapping("/noticeUpdate")
    public String noticeUpdate(NoticeDto noticeDto) throws IOException {
        noticeService.noticeUpdateOk(noticeDto);
        return "redirect:/board/noticeDetail/" + noticeDto.getId();
    }

    @GetMapping("/noticeUpdate/{Id}")
    public String noticeUpdate(@PathVariable("Id") Long Id,
                             @AuthenticationPrincipal MyUserDetails myUserDetails,
                             Model model) {

        NoticeDto noticeDto = noticeService.detail(Id);


        model.addAttribute("notice", noticeDto);
        model.addAttribute("myUserDetails", myUserDetails);

        return "board/noticeUpdate";
    }


    @GetMapping("/noticeDelete/{id}")
    public String noticeDelete(@PathVariable("id") Long id) {
        noticeService.noticeDelete(id);
        return "redirect:/board/noticeList";
    }
    
}
