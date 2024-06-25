package org.spring.codingStory.board.notice.controller;

import lombok.RequiredArgsConstructor;
import org.spring.codingStory.board.notice.dto.NoticeDto;
import org.spring.codingStory.board.notice.dto.NoticeReplyDto;
import org.spring.codingStory.board.notice.serviceImpl.NoticeReplyServiceImpl;
import org.spring.codingStory.config.MyUserDetails;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor
@Controller
@RequestMapping("/reply")
public class NoticeReplyController {

    private final NoticeReplyServiceImpl noticeReplyService;

    @PostMapping("/noticeReplyWrite")
    public String noticeReplyWrite(@AuthenticationPrincipal MyUserDetails myUserDetails,
                                 Model model, NoticeReplyDto noticeReplyDto, NoticeDto noticeDto) {

        model.addAttribute("memberName", myUserDetails.getMemberEntity().getName());
        noticeReplyService.insertNoticeReply(noticeReplyDto);

        return "redirect:/board/noticeDetail/" + noticeReplyDto.getNoticeId();
    }
    @GetMapping("/noticeReplyDelete/{id}")
    public String noticeReplyDelete(@PathVariable("id") Long id) {


        Long noticeId = noticeReplyService.noticeReplyDeleteById(id);

        return "redirect:/board/noticeDetail/" + noticeId;
    }

}
