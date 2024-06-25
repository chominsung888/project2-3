package org.spring.codingStory.board.employee.controller;

import lombok.RequiredArgsConstructor;
import org.spring.codingStory.board.employee.dto.EmployeeDto;
import org.spring.codingStory.board.employee.dto.EmployeeReplyDto;
import org.spring.codingStory.board.employee.serviceImpl.EmployeeReplyServiceImpl;
import org.spring.codingStory.board.employee.serviceImpl.EmployeeServiceImpl;
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
public class EmpController {

    private final EmployeeServiceImpl employeeService;
    private final EmployeeReplyServiceImpl employeeReplyService;

    @GetMapping("/empWrite")
    public String employeeWrite(@AuthenticationPrincipal MyUserDetails myUserDetails,
                             EmployeeDto empDto, Model model) {

        model.addAttribute("memberId", myUserDetails.getMemberEntity().getId());
        model.addAttribute("empDto", empDto);
        model.addAttribute("memberName", myUserDetails.getMemberEntity().getName());

        return "board/empWrite";
    }

    @PostMapping("/empWrite")
    public String empWriteOK(EmployeeDto employeeDto,
                               @AuthenticationPrincipal MyUserDetails myUserDetails,
                               Model model) throws IOException {
        employeeService.empInsertFile(employeeDto);
        return "redirect:/board/empList";
        //글 작성후에 employeeList 페이지로 이동
    }

    @GetMapping("/empList")
    public String empList(@PageableDefault(page = 0, size = 3, sort = "id", direction = Sort.Direction.DESC) Pageable pageable,
                          Model model,
                          @RequestParam(name = "subject1", required = false) String subject1,
                          @RequestParam(name = "subject2", required = false) String subject2,
                          @RequestParam(name = "search", required = false) String search,
                          @AuthenticationPrincipal MyUserDetails myUserDetails) {

        Page<EmployeeDto> empList = employeeService.empList(pageable, subject1, subject2, search);

        int totalPage = empList.getTotalPages();
        int newPage = empList.getNumber();
        Long totalElements = empList.getTotalElements();
        int size = empList.getSize();

        int blockNum = 3;
        int startPage = (int) ((Math.floor(newPage / blockNum) * blockNum) + 1 <= totalPage
                ? (Math.floor(newPage / blockNum) * blockNum) + 1 : totalPage);
        int endPage = (startPage + blockNum) - 1 < totalPage ? (startPage + blockNum) - 1 : totalPage;

        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        model.addAttribute("empList", empList);
        model.addAttribute("myUserDetails", myUserDetails);

        return "board/empList";
    }

    @GetMapping("/empDetail/{Id}")
    public String empDetail(@PathVariable("Id") Long Id,
                             @AuthenticationPrincipal MyUserDetails myUserDetails,
                             Model model) {

        employeeService.updateEmpHit(Id);
        EmployeeDto employeeDto = employeeService.detail(Id);

        List<EmployeeReplyDto> employeeReplyDtoList = employeeReplyService.employeeReplyList(employeeDto.getId());

        model.addAttribute("emp", employeeDto);
        model.addAttribute("employeeReplyDtoList", employeeReplyDtoList);
        model.addAttribute("myUserDetails", myUserDetails);

        return "board/empDetail";
    }
    @PostMapping("/empUpdate")
    public String empUpdate(EmployeeDto employeeDto) throws IOException {
        employeeService.empUpdateOk(employeeDto);
        return "redirect:/board/empDetail/" + employeeDto.getId();
    }
    @GetMapping("/empUpdate/{Id}")
    public String empUpdate(@PathVariable("Id") Long Id,
                             @AuthenticationPrincipal MyUserDetails myUserDetails,
                             Model model) {

        EmployeeDto employeeDto = employeeService.detail(Id);

        model.addAttribute("emp", employeeDto);
        model.addAttribute("myUserDetails", myUserDetails);

        return "board/empUpdate";
    }

    @GetMapping("/empDelete/{id}")
    public String empDelete(@PathVariable("id") Long id) {
        employeeService.empDelete(id);
        return "redirect:/board/empList";
    }



}
