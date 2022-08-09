package com.adnstyle.boardsj.controller;

import com.adnstyle.boardsj.dto.MemberDto;
import com.adnstyle.boardsj.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
@RequiredArgsConstructor
@RequestMapping("/auth")
public class LoginController {
    private final MemberService memberService;

    @PostMapping("login.do")
    public String loginMember(String id, String pw, HttpSession session) {
        MemberDto loginDto = memberService.loginMember(id, pw);
        if (loginDto != null) {
            session.setAttribute("loginDto", loginDto);
            return "redirect:/";
        } else {
            return "redirect:auth/login";
        }
    }
}
