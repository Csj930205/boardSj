package com.adnstyle.boardsj.repository;

import com.adnstyle.boardsj.dto.MemberDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MemberRepository {
    /*
    * 회원 조회
    * */
    List<MemberDto> listMember();

    /*
    * 회원 단독 조회
    * */

    MemberDto getSeq(int seq);

    /*
    * 로그인
    * */
    MemberDto loginMember(String id, String pw);

    /*
    * 로그인(시큐리티)
    * */
    MemberDto securityLogin(String id);

    /*
    * 회원 등록
    * */
    int signupMember(MemberDto memberDto);

    /*
    * 회원 삭제
    * */
    int deleteMember(int seq);

    /*
    * 회원정보 변경
    * */
    int updateMember(MemberDto memberDto);
}
