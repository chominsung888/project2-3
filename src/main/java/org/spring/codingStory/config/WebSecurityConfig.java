package org.spring.codingStory.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {


    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public OAuth2UserService<OAuth2UserRequest, OAuth2User> myOAuth2Service() {
        return new MyOAuth2UserService();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.csrf().disable();


        http.authorizeRequests()
            .antMatchers("member/login","member/join","/member/findCheck","/member/findPasswordOk").permitAll()
            .antMatchers("/js/**","/css/**", "/images/***").permitAll()
            .antMatchers("/my/mycalendar2/calendar").permitAll()
            .antMatchers("/index").authenticated()
            .antMatchers("/member/memberList","/member/memberAppList","/member/memberInfo/**","/department/**").hasAnyRole("ADMIN")
            .antMatchers().hasAnyRole()
            .anyRequest().permitAll();

        http.formLogin()
            .loginPage("/login")
            .usernameParameter("userEmail")
            .passwordParameter("userPw")
            .loginProcessingUrl("/login")
            .successHandler(customAuthenticationSuccessHandler())
            .failureHandler(authenticationFailureHandler())
//                .defaultSuccessUrl("/index")
//                .failureForwardUrl("/login")
            .and()
            .oauth2Login()
            .loginPage("/login")
            .userInfoEndpoint()
            .userService(myOAuth2Service());

        http.logout()
            .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
            .logoutSuccessUrl("/");

        return http.build();
    }



    @Bean
    public CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler() {
        return new CustomAuthenticationSuccessHandler();
    }

    @Bean
    public AuthenticationFailureHandler authenticationFailureHandler() {
        return new CustomAuthenticationFailHandler();
    }







}
