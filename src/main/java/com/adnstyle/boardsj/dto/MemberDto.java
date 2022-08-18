package com.adnstyle.boardsj.dto;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;


@Data
public class MemberDto implements UserDetails {
    private int seq;
    private String id;
    private String pw;
    private String name;
    private String birth;
    private String email;
    private LocalDateTime signupTime;
    private int grade;
    private String role;

    /*
     * 계정이 가지고 있는 권한 목록을 리턴
     * */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority(this.role));
    }

    /*
    * 계정의 패스워드를 리턴
    * */
    @Override
    public String getPassword() {
        return this.pw;
    }

    /*
    * 계정의 UserName을 반환(자신이 원하는 목록으로 return)
    * */
    @Override
    public String getUsername() {
        return this.id;
    }

    /*
     * 계정이 만료되지 않았는지 리턴(true: 만료안됨)
     * */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /*
     * 계정이 잠겨있는지를 리턴 (true: 잠기지 않음)
     * */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /*
     * 비밀번호가 만료되지 않았는지를 리턴(true: 만료안됨)
     * */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /*
     * 계정이 활성화(사용가능)한지 리턴(true: 활성화)
     * */
    @Override
    public boolean isEnabled() {
        return true;
    }
}
