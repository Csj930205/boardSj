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

import javax.servlet.http.HttpSession;
import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

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
        if(memberDto.getGrade()==0){
            memberDto.setRole(Role.ADMIN.getKey());
        }else {
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

        List<GrantedAuthority> authorities = new ArrayList<>();
        if("ROLE_ADMIN".equals(memberDto.getRole().toString())){
            authorities.add(new SimpleGrantedAuthority(memberDto.getRole()));
        }else{
            authorities.add(new SimpleGrantedAuthority(Role.USER.getKey()));
        }
        return memberDto;
    }
}
