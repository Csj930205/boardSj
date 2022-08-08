package com.adnstyle.boardsj;

import com.adnstyle.boardsj.dto.BoardDto;
import com.adnstyle.boardsj.dto.MemberDto;
import com.adnstyle.boardsj.service.BoardService;
import com.adnstyle.boardsj.service.MemberService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.List;

@SpringBootTest
class BoardSjApplicationTests {
    @Autowired
    MemberService ms;

    @Autowired
    BoardService bs;

    /*
    * 회원리스트 조회 테스트
    * */
    @Test
    void contextLoads() {
        List<MemberDto> listMember =ms.listMember();
        for(MemberDto memdto : listMember){
            System.out.println(memdto);
        }
    }

    /*
    * 회원 단독 조회 테스트
    * */
    @Test
    void oneMemberTest(){
        MemberDto mdt=ms.getSeq(0);
        System.out.println(mdt);
    }

    /*
     * 로그인정보 테스트
     * */
    @Test
    void loginTest(){
        MemberDto memDto= ms.loginMember("admin","1234");
        System.out.println(memDto);
    }

    /*
    * 회원등록 조회
    * */
    /*@Test
    void signupTest(){
        MemberDto memberDto=new MemberDto();
        memberDto.setId("tjdwns135");
        memberDto.setPw("90931234");
        memberDto.setName("chlasdi1");
        memberDto.setBirth(LocalDate.parse("1993-02-05"));
        int result=ms.signupMember(memberDto);
        System.out.println(result);
    }*/

    /*
    * 회원삭제 테스트
    * */
 /*   @Test
    void deleteMemberTest(){
        int result = ms.deleteMember("tjdwns135");
        System.out.println(result);
    }*/

    /*
    * 회원정보 수정 테스트
    * */
/*    @Test
    void updateMemberTest(){
        MemberDto memberDto=new MemberDto();
        memberDto.setId("tjdwns132");
        memberDto.setPw("1234");
        memberDto.setName("성준");
        memberDto.setBirth(LocalDate.parse("1992-02-05"));
        int result = ms.updateMember(memberDto);
        System.out.println(result);
    }*/

    /*
    * 게시글 전체조회
    * */
    @Test
    void boardListTest(){
        List<BoardDto> boardDtoList=bs.listBoard();
        for(BoardDto bdt : boardDtoList){
            System.out.println(bdt);
        }
    }

    /*
    * 게시글 등록 테스트
    * */
    @Test
    void boardInsertReplayTest(){
        BoardDto boardDto =new BoardDto();
        boardDto.setTitle("Test");
        boardDto.setContents("Test");
        int result = bs.insertBoard(boardDto);
        System.out.println(result);
    }
    /*
    * 게시글 삭제 테스트
    * */
    @Test
    void deleteBoardTest(){
        int result = bs.deleteBoard(2);
        System.out.println(result);
    }

    /*
    * 답글 작성 상세조회
    * */
    @Test
    void replayGetSeqTest(){
        BoardDto replay=bs.replayGetSeq(1);
        System.out.println(replay);
    }

    /*
    * 답글 작성
    * */
    @Test
    void insertBoardReplay(){
        BoardDto insertreplay = bs.replayGetSeq(3);
        insertreplay.setTitle("3-1");
        insertreplay.setContents("3-1");
        bs.insertBoardReplay(insertreplay);
    }

}
