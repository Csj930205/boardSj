package com.adnstyle.boardsj.controller;

import com.adnstyle.boardsj.dto.BoardDto;
import com.adnstyle.boardsj.dto.MemberDto;
import com.adnstyle.boardsj.service.BoardService;
import com.adnstyle.boardsj.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
@RequiredArgsConstructor
public class DomainController {
    private final MemberService memberService;
    private final BoardService boardService;
    /*
    * 메인페이지 이동
    * */
    @RequestMapping("/")
    public String domain(){
        return "index";
    }

    /*
    * 회원가입 페이지 이동
    * */
    @RequestMapping("/signup/signup.do")
    public String signup(){
        return "signup/signup";
    }

    /*
    * 회원 단독조회
    * */
    @RequestMapping("/member/modify/{seq}")
        public String getSeq(@PathVariable("seq") int seq, Model model){
            MemberDto modifyDomain=memberService.getSeq(seq);
            model.addAttribute("modifyDomain",modifyDomain);
            return "member/modify";
        }
    /*
    * 로그인 페이지 이동
    * */
    @RequestMapping("/login/login.do")
    public String login(){
        return "login/login";
    }

    /*
    * 로그아웃 및 메인페이지로 이동
    * */
    @RequestMapping("/login/logout.do")
    public String logout(HttpSession session){
        session.invalidate();
        return "redirect:/";
    }

    /*
    * 게시글 리스트로 이동
    * */
    @RequestMapping("/board/list.do")
    public String listBoard(){
        return "board/list";
    }

    /*
    * 게시글 등록 페이지 이동
    * */
    @RequestMapping("/board/insertBoard.do")
    public String insertBoard(){
        return "board/post";
    }

    /*
    * 답글을 위한 게시물 상세조회 페이지 이동
    * */
    @RequestMapping("/board/replay/{seq}")
    public String replayBoard(@PathVariable("seq")int seq, Model model){
        BoardDto replayBoard = boardService.replayGetSeq(seq);
        model.addAttribute("replayBoard",replayBoard);
        return "board/replay";
    }
}
