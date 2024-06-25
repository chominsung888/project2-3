package org.spring.codingStory.config;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.spring.codingStory.member.entity.MemberEntity;
import org.spring.codingStory.member.role.Role;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
public class MyUserDetails implements UserDetails, OAuth2User {


    private MemberEntity memberEntity;
    private Map<String, Object> attribute;

    // 일반회원
    public MyUserDetails(MemberEntity memberEntity) {
        this.memberEntity = memberEntity;
    }

    public MyUserDetails(MemberEntity memberEntity, Map<String, Object> attribute) {
        this.memberEntity = memberEntity;
        this.attribute = attribute;
    }


    @Override
    public Map<String, Object> getAttributes() {
        return attribute;
    }

    @Override
    public String getName() {
        return memberEntity.getName();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collection = new ArrayList<>();

        collection.add(new GrantedAuthority() {
            @Override
            public String getAuthority() {
                return "ROLE_" + memberEntity.getRole().toString();
            }
        });
        return collection;
    }

    public Role getRole(){

        return memberEntity.getRole();
    }


    public String getDepartment() {
        return memberEntity.getDepartment();
    }

    public String getMRank() {
        return memberEntity.getMRank();
    }

    @Override
    public String getPassword() {
        return memberEntity.getUserPw();
    }

    @Override
    public String getUsername() {
        return memberEntity.getUserEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}