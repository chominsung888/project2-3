package org.spring.codingStory.pay.controller;

import lombok.RequiredArgsConstructor;
import org.spring.codingStory.config.MyUserDetails;
import org.spring.codingStory.member.entity.MemberEntity;
import org.spring.codingStory.pay.dto.PayDto;
import org.spring.codingStory.pay.serviceImpl.PayServiceImpl;
import org.spring.codingStory.payment.dto.PaymentDto;
import org.spring.codingStory.payment.serviceImpl.PaymentServiceImpl;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/pay")
@Controller
public class PayController {
    private final PayServiceImpl payServiceImpl;
    private final PaymentServiceImpl paymentServiceImpl;

    //////////////////////////////////////////////////////////

    @GetMapping({"/", ""})
    public String payIndex(@AuthenticationPrincipal MyUserDetails myUserDetails,
                           @RequestParam(name = "subject", required = false) String subject,
                           @RequestParam(name = "search", required = false) String search,
                           Model model) {

        List<PaymentDto> paymentList = new ArrayList<>();
        if (subject == null || search == null) {
            paymentList = paymentServiceImpl.paymentList();
        } else {
            paymentList = paymentServiceImpl.paymentList();
        }

        model.addAttribute("paymentList", paymentList);

        model.addAttribute("name", myUserDetails.getMemberEntity().getName());
        model.addAttribute("memberId", myUserDetails.getMemberEntity().getId());

        model.addAttribute("userEmail",myUserDetails.getMemberEntity().getUserEmail());

        return "pay/payIndex";
    }

    //////////////////////////////////////////////////////////

    @PostMapping("/payUpdate")
    public String payUpdate(@ModelAttribute PayDto payDto,
                            BindingResult bindingResult) throws IOException {
        if (bindingResult.hasErrors()) {
            return "pay/payDetail";
        }
        if (payDto.getId() == null) {
            throw new IllegalArgumentException("The given id must not be null!");
        }

        payServiceImpl.updateOk(payDto);

        return "redirect:/pay/payDetail/" + payDto.getMemberId();
    }

    //////////////////////////////////////////////////////////


    @GetMapping("/payWrite/{memberId}")
    public String write(@PathVariable("memberId") Long memberId, Model model) {
        PayDto payDto = new PayDto();
        payDto.setMemberEntity(new MemberEntity());
        payDto.getMemberEntity().setId(memberId);

        model.addAttribute("payDto", payDto);
        return "pay/payWrite";
    }

    @PostMapping("/payWrite")
    public String writeOk(@Valid PayDto payDto,
                          BindingResult bindingResult) throws IOException {

        if (bindingResult.hasErrors()) {
            return "pay/payWrite";
        }

        payServiceImpl.insertPay(payDto);
        return "redirect:/pay";
    }


    //////////////////////////////////////////////////////////////

    @GetMapping("/payDetail/{memberId}")
    public String payByMemberId(
            @PathVariable("memberId") Long memberId,
            Model model) {

        List<PayDto> payList = payServiceImpl.findByMemberId(memberId);
        if (payList.isEmpty()) {
            return "error/404";
        }

//        model.addAttribute("pay", payList.get(0)); // 첫 번째 Pay 엔티티만 표시
        model.addAttribute("pay", payList.get(payList.size() - 1));
        return "pay/payDetail";
    }

    //////////////////////////////////////////////////////////



}