package com.adnstyle.boardsj.service;

import com.adnstyle.boardsj.dto.MemberDto;
import com.adnstyle.boardsj.dto.Role;
import com.adnstyle.boardsj.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;

import javax.servlet.http.HttpSession;
import java.sql.Array;
import java.util.*;

@Service
@RequiredArgsConstructor
public class MemberService implements UserDetailsService{
    private final MemberRepository memberRepository;

    /*
     * 회원 전체조회
     * */
    public List<MemberDto> listMember(){
        return memberRepository.listMember();
    }


    /*
    * 회원 단독 조회
    * */
    public MemberDto getSeq(int seq){
        return memberRepository.getSeq(seq);
    }

    /*
    * 회원 등록
    * */
    public int signupMember(MemberDto memberDto){
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        memberDto.setPw(passwordEncoder.encode(memberDto.getPw()));
//        등급이 0일 시 Role 의 key값인 "ROLE_ADMIN" 1일 시 "USER" 를 부여함
        if(memberDto.getGrade()==0){
            memberDto.setRole(Role.ADMIN.getKey());
        }else{
            memberDto.setRole(Role.USER.getKey());
        }
        return memberRepository.signupMember(memberDto);
    }

    /*
    * 회원 삭제
    * */
    public int deleteMember(int seq){
        return memberRepository.deleteMember(seq);
    }

    /*
    * 회원정보 수정
    * */
    public int updateMember(MemberDto memberDto){
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        memberDto.setPw(passwordEncoder.encode(memberDto.getPw()));
        return memberRepository.updateMember(memberDto);
    }

    /*
    * 로그인(시큐리티)
    * */
    @Override
    public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {
        MemberDto memberDto=memberRepository.securityLogin(id);
        if(memberDto == null){
            throw new UsernameNotFoundException("User not authorized");
        }

//        로그인 정보가 일치 할 시 해당 권한을 반환함
        memberDto.getAuthorities();
        return memberDto;
    }
}
