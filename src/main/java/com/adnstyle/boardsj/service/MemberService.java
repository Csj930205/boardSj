package com.adnstyle.boardsj.service;

import com.adnstyle.boardsj.dto.MemberDto;
import com.adnstyle.boardsj.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberService{
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
    * 로그인
    * */
    public MemberDto loginMember(String id, String pw){
        return memberRepository.loginMember(id, pw);
    }

    /*
    * 회원 등록
    * */
    public int signupMember(MemberDto memberDto){
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
        return memberRepository.updateMember(memberDto);
    }
}
