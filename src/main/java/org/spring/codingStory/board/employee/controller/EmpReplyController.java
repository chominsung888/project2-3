package org.spring.codingStory.board.employee.controller;

import lombok.RequiredArgsConstructor;
import org.spring.codingStory.board.employee.dto.EmployeeDto;
import org.spring.codingStory.board.employee.dto.EmployeeReplyDto;
import org.spring.codingStory.board.employee.serviceImpl.EmployeeReplyServiceImpl;
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
public class EmpReplyController {

    private final EmployeeReplyServiceImpl employeeReplyService;

    @PostMapping("/empReplyWrite")
    public String empReplyWrite(@AuthenticationPrincipal MyUserDetails myUserDetails,
                                Model model, EmployeeReplyDto empReplyDto, EmployeeDto empDto) {

        model.addAttribute("memberName", myUserDetails.getMemberEntity().getName());
        employeeReplyService.insertEmployeeReply(empReplyDto);

        return "redirect:/board/empDetail/" + empReplyDto.getEmployeeId();
    }
    @GetMapping("/empReplyDelete/{id}")
    public String empReplyDelete(@PathVariable("id") Long id) {


        Long empId = employeeReplyService.employeeReplyDeleteById(id);

        return "redirect:/board/empDetail/" + empId;
    }
}
