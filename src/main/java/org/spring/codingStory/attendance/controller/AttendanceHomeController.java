package org.spring.codingStory.attendance.controller;

import org.spring.codingStory.config.MyUserDetails;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/attendance")
@Controller
public class AttendanceHomeController {

    @GetMapping({"/",""})
    public String attIndex(@AuthenticationPrincipal MyUserDetails myUserDetails, Model model){
        model.addAttribute("name" ,myUserDetails.getMemberEntity().getName());
        model.addAttribute("memberId",myUserDetails.getMemberEntity().getId());
        return "attendance/attIndex";
    }


}
