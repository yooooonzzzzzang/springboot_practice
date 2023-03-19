package com.cos.security1.config.auth;
// 시큐리티가 /login 주소 요청이 오면 낚아채서 로그인을 진행시킨다
// 로그인 진행이 완료가 되면 session을 만들어준다 (Security ContextHolder)
// 시큐리티에 들어갈 오브젝트가 정해져있음 => Authentication 타입 객체
// Authentication 안에 User 정보가 있어야함
// User 오브젝트 타입 => UserDetails 타입 객체

import com.cos.security1.model.User;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

// Security Session => Authentication 타입 => UserDetails(PrincipalDetails)
@Data // get 접근 하기 위해
public class PrincipleDetails implements UserDetails, OAuth2User {
    private User user; //콤포지션
    private Map<String, Object> attributes;
    // 일반 로그인
    public PrincipleDetails(User user){
        this.user = user;
    }
    // OAuth 로그인
    public PrincipleDetails(User user, Map<String, Object> attributes){
        this.user = user;
        this.attributes = attributes;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    // 해당 User 의 권한을 리턴하는 곳
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collect = new ArrayList<>();
        collect.add(new GrantedAuthority() {
            @Override
            public String getAuthority() {
                return user.getRole();
            }
        });
        return collect;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
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
        // 우리 사이트 에서 1년동안 회원이 로그인을 안하면 휴먼계정으로 전환
        // 현재시간 - 로그인 시간 => 1년 초과 return false
        return true;
    }

    @Override
    public String getName() {
        return null;
    }
}
