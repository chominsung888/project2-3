package org.spring.codingStory.board.freeBoard.controller;

import lombok.RequiredArgsConstructor;
import org.spring.codingStory.board.freeBoard.dto.FreeDto;
import org.spring.codingStory.board.freeBoard.dto.FreeReplyDto;
import org.spring.codingStory.board.freeBoard.serviceImpl.FreeReplyServiceImpl;
import org.spring.codingStory.board.freeBoard.serviceImpl.FreeServiceImpl;
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
public class FreeController {

    private final FreeServiceImpl freeService;
    private final FreeReplyServiceImpl freeReplyService;

    @GetMapping("/freeWrite")
    public String freeWrite(@AuthenticationPrincipal MyUserDetails myUserDetails,
                                 FreeDto freeDto, Model model) {

        model.addAttribute("memberId", myUserDetails.getMemberEntity().getId());
        model.addAttribute("freeDto", freeDto);
        model.addAttribute("memberName", myUserDetails.getMemberEntity().getName());

        return "board/freeWrite";
    }


    @PostMapping("/freeWrite")
    public String freeWriteOK(FreeDto freeDto,
                               @AuthenticationPrincipal MyUserDetails myUserDetails,
                               Model model) throws IOException {

        freeService.freeInsertFile(freeDto);

        return "redirect:/board/freeList";
        //글 작성후에 employeeList 페이지로 이동
    }

    @GetMapping("/freeList")
    public String freeList(@PageableDefault(page = 0, size = 3, sort = "id", direction = Sort.Direction.DESC) Pageable pageable,
                           Model model,
                           @RequestParam(name = "subject1", required = false) String subject1,
                           @RequestParam(name = "subject2", required = false) String subject2,
                           @RequestParam(name = "search", required = false) String search,
                           @AuthenticationPrincipal MyUserDetails myUserDetails ) {


        Page<FreeDto> freeList = freeService.freeList(pageable, subject1, subject2, search);


        int totalPage = freeList.getTotalPages();//전체page
        int newPage = freeList.getNumber();//현재page
        Long totalElements = freeList.getTotalElements();//전체 레코드 갯수
        int size = freeList.getSize();//페이지당 보이는 갯수

        int blockNum = 3; //브라우저에 보이는 페이지 갯수

        int startPage = (int) ((Math.floor(newPage / blockNum) * blockNum) + 1 <= totalPage
                ? (Math.floor(newPage / blockNum) * blockNum) + 1 : totalPage);
        int endPage = (startPage + blockNum) - 1 < totalPage ? (startPage + blockNum) - 1 : totalPage;

        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        model.addAttribute("freeList", freeList);
        model.addAttribute("myUserDetails", myUserDetails);

        return "board/freeList";
    }

    @GetMapping("/freeDetail/{Id}")
    public String freeDetail(@PathVariable("Id") Long Id,
                            @AuthenticationPrincipal MyUserDetails myUserDetails,
                            Model model) {

        freeService.updateFreeHit(Id);
        FreeDto freeDto = freeService.detail(Id);

        List<FreeReplyDto> freeReplyDtoList = freeReplyService.freeReplyList(freeDto.getId());

        model.addAttribute("free", freeDto);
        model.addAttribute("freeReplyDtoList", freeReplyDtoList);
        model.addAttribute("myUserDetails", myUserDetails);

        return "board/freeDetail";
    }

    @PostMapping("/freeUpdate")
    public String freeUpdate(FreeDto freeDto) throws IOException {
        freeService.freeUpdateOk(freeDto);
        return "redirect:/board/freeDetail/" + freeDto.getId();
    }

    @GetMapping("/freeUpdate/{Id}")
    public String freeUpdate(@PathVariable("Id") Long Id,
                            @AuthenticationPrincipal MyUserDetails myUserDetails,
                            Model model) {

        FreeDto freeDto = freeService.detail(Id);


        model.addAttribute("free", freeDto);
        model.addAttribute("myUserDetails", myUserDetails);

        return "board/freeUpdate";
    }


    @GetMapping("/freeDelete/{id}")
    public String freeDelete(@PathVariable("id") Long id) {
        freeService.freeDelete(id);
        return "redirect:/board/freeList";
    }



}
