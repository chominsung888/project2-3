package org.spring.codingStory.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.spring.codingStory.member.exception.GuestLoginException;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;

@Component
public class CustomAuthenticationFailHandler extends SimpleUrlAuthenticationFailureHandler {

    private ObjectMapper objectMapper = new ObjectMapper();


    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {

        // 인증이 실패 시 상태
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        // 기본적으로 전달하는 예외 메세지를 작성
        String errorMessage = "invalid UserName or Password";

        if (exception instanceof BadCredentialsException) {
            errorMessage = "아이디 또는 비밀번호가 맞지 않습니다. 다시 입력하세요";
        } else if (exception instanceof InternalAuthenticationServiceException) {
            errorMessage = "오류발생 관리자문의";
        } else if (exception instanceof UsernameNotFoundException) {
            errorMessage = "아이디가 존재하지 않습니다.";
        } else if (exception instanceof AuthenticationCredentialsNotFoundException) {
            errorMessage = "인증 요청이 거부되었습니다. 관리자에게 문의하세요.";
        } else if (exception instanceof GuestLoginException) {
            errorMessage = "가입 대기중";
        }

        errorMessage = URLEncoder.encode(errorMessage, "UTF-8");
        // member/login 페이지로 error, exception
        setDefaultFailureUrl("/login?error=true&exception=" + errorMessage);
        super.onAuthenticationFailure(request, response, exception);
    }

}
