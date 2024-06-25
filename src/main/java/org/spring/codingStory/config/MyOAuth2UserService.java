package org.spring.codingStory.config;

import org.spring.codingStory.member.entity.MemberEntity;
import org.spring.codingStory.member.repository.MemberRepository;
import org.spring.codingStory.member.role.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
public class MyOAuth2UserService extends DefaultOAuth2UserService {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);

        ClientRegistration clientRegistration = userRequest.getClientRegistration();
        String registrationId = clientRegistration.getRegistrationId();

        return oAuth2UserSuccess(oAuth2User, registrationId);
    }

    private OAuth2User oAuth2UserSuccess(OAuth2User oAuth2User, String registrationId) {

        String email = "";
        String userPw = "";
        String name = "";
        String address = "";

        if (registrationId.equals("google")) {
            email = oAuth2User.getAttribute("email");
            name = oAuth2User.getAttribute("name");
        } else if (registrationId.equals("kakao")) {
            Map<String, Object> response = (Map<String, Object>) oAuth2User.getAttributes().get("kakao_account");
            Map<String, Object> profile = (Map<String, Object>) response.get("profile");

            System.out.println(profile + "<- profile");

            email = (String) response.get("email");
            name = (String) profile.get("nickname");

            // 임시 이메일
            email = name + "@kakao.test";
        } else if (registrationId.equals("naver")) {
            Map<String, Object> response = (Map<String, Object>) oAuth2User.getAttributes().get("response");
//            Map<String, Object> profile = (Map<String, Object>) response.get("profile");

            email = (String) response.get("email");
            name = (String) response.get("name");
        }

        Optional<MemberEntity> optionalMemberEntity = memberRepository.findByUserEmail(email);

        if (optionalMemberEntity.isPresent()) {
            return new MyUserDetails(optionalMemberEntity.get());
        }

        userPw = passwordEncoder.encode("fwegfwsegew");

        MemberEntity memberEntity = MemberEntity.builder()
                .userEmail(email)
                .userPw(userPw)
                .name(name)
                .address(address)
                .role(Role.GEUST)
                .build();

        MemberEntity member = memberRepository.save(memberEntity);

        return new MyUserDetails(member, oAuth2User.getAttributes());

    }
}
