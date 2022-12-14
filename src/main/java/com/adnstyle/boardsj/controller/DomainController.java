package com.adnstyle.boardsj.controller;

import com.adnstyle.boardsj.dto.*;
import com.adnstyle.boardsj.service.BoardService;
import com.adnstyle.boardsj.service.ItemService;
import com.adnstyle.boardsj.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class DomainController {
    private final MemberService memberService;
    private final BoardService boardService;
    private final ItemService itemService;

    /*
     * 메인페이지 이동
     * */
    @RequestMapping("/")
    public String domain() {
        return "index";
    }

    /*
     * 회원가입 페이지 이동
     * */
    @RequestMapping("/signup/signup.do")
    public String signup() {
        return "signup/signup";
    }

    /*
     * 회원 단독조회
     * */
    @RequestMapping("/member/modify/{seq}")
    public String getSeq(@PathVariable("seq") int seq, Model model) {
        MemberDto modifyDomain = memberService.getSeq(seq);
        model.addAttribute("modifyDomain", modifyDomain);
        return "member/modify";
    }

    /*
     * 로그인 페이지 이동
     * */
    @RequestMapping("/auth/login.do")
    public String login() {
        return "auth/login";
    }

    /*
     * 로그인 실패시 이동
     * */
    @RequestMapping("/denied/denied.do")
    public String loginDenied() {
        return "denied/denied";
    }

    /*
     * 로그인 성공시 이동
     * */
    @RequestMapping("/auth/login_access.do")
    public String loginAccess(Authentication authentication, HttpSession session) {
        MemberDto memberLoginInfo = (MemberDto) authentication.getPrincipal(); // userDetail 객체를 가져온다
        session.setAttribute("memberLoginInfo", memberLoginInfo);
        return "auth/login_access";
    }

    /*
     * 게시글 리스트로 이동
     * */
    @RequestMapping("/board/list.do")
    public String listBoard() {
        return "board/list";
    }

    /*
     * 게시글 상세조회
     * */
    @RequestMapping("/board/detailList/{seq}")
    public String detailListBoard(@PathVariable("seq") int seq, Model model) {
        BoardDto detailListBoard = boardService.detailListBoard(seq);
        model.addAttribute("detailListBoard", detailListBoard);
        return "board/detailList";
    }

    /*
     * 게시글 등록 페이지 이동
     * */
    @RequestMapping("/board/insertBoard.do")
    public String insertBoard() {
        return "board/post";
    }

    /*
     * 답글을 위한 게시물 상세조회 페이지 이동
     * */
    @GetMapping("/board/replay/{seq}")
    public String replayBoard(@PathVariable("seq") int seq, Model model) {
        BoardDto replayBoard = boardService.replayGetSeq(seq);
        model.addAttribute("replayBoard", replayBoard);
        return "board/replay";
    }

    /*
     * 상품리스트 페이지 이동
     * */
    @RequestMapping("/item/itemList")
    public String itemList() {
        return "/item/itemList";
    }

    /*
     * 상품등록 페이지 이동
     * */
    @RequestMapping("/item/itemInsert")
    public String itemInsert() {
        return "/item/itemInsert";
    }

    /*
     * 상품상세페이지 이동
     * */
    @RequestMapping("/item/itemmodify/{seq}")
    public String getList(@PathVariable("seq") int seq, Model model) {
        ItemDto modifyList = itemService.getList(seq);
        model.addAttribute("modifyList", modifyList);

        AttachDto fileList = itemService.attachListDetail(seq);
        model.addAttribute("fileList", fileList);
        return "/item/itemmodify";
    }
}
