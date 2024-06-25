package org.spring.codingStory.config;

import org.spring.codingStory.member.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Autowired
    MemberRepository memberRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        response.setContentType("text/html; charset=utf-8");


        PrintWriter out = response.getWriter();

//        out.println("<script> alert('로그인 성공!" + authentication.getName() +" 님 반갑습니다.');" +
//                        "location.href='/index';" +
//                "</script>");
//        out.close();

        out.println("<script> "+
                "location.href='/index';" +
                "</script>");
        out.close();


        }


}
