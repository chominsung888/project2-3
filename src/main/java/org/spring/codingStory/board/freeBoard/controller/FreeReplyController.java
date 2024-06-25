package org.spring.codingStory.board.freeBoard.controller;

import lombok.RequiredArgsConstructor;
import org.spring.codingStory.board.freeBoard.dto.FreeDto;
import org.spring.codingStory.board.freeBoard.dto.FreeReplyDto;
import org.spring.codingStory.board.freeBoard.serviceImpl.FreeReplyServiceImpl;
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
public class FreeReplyController {

    private final FreeReplyServiceImpl freeReplyService;

    @PostMapping("/freeReplyWrite")
    public String freeReplyWrite(@AuthenticationPrincipal MyUserDetails myUserDetails,
                                 Model model, FreeReplyDto freeReplyDto, FreeDto freeDto) {

        model.addAttribute("memberName", myUserDetails.getMemberEntity().getName());
        freeReplyService.insertFreeReply(freeReplyDto);

        return "redirect:/board/freeDetail/" + freeReplyDto.getFreeId();
    }
    @GetMapping("/freeReplyDelete/{id}")
    public String freeReplyDelete(@PathVariable("id") Long id) {


        Long FreeId = freeReplyService.freeReplyDeleteById(id);

        return "redirect:/board/freeDetail/" + FreeId;
    }

}
